package by.itacademy.railway.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LogAspect {

    @Pointcut("execution(public * by.itacademy.railway.service.*Service.findAll(..))")
    public void anyServiceFindAllMethod() {
    }

    @Pointcut("execution(public * by.itacademy.railway.service.*Service.findAllByUserId(..))")
    public void anyServiceFindAllByUserIdMethod() {
    }

    @Pointcut("execution(public * by.itacademy.railway.service.*Service.findById(..))")
    public void anyServiceFindByIdMethod() {
    }

    @Pointcut("execution(public * by.itacademy.railway.service.*Service.create(..))")
    public void anyServiceCreateMethod() {
    }

    @Pointcut("execution(public * by.itacademy.railway.service.*Service.remove(..))")
    public void anyServiceRemoveMethod() {
    }

    @Pointcut("execution(public * by.itacademy.railway.service.*Service.findByWagonId(..))")
    public void anyServiceFindByWagonIdMethod() {
    }

    @Pointcut("execution(public * by.itacademy.railway.service.*Service.findByTrainId(..))")
    public void anyServiceFindByTrainIdMethod() {
    }

    @Pointcut("execution(public * by.itacademy.railway.service.*Service.findByRoute(..))")
    public void anyServiceFindByRouteMethod() {
    }

    @Pointcut("execution(public * by.itacademy.railway.service.*Service.findByEmail(..))")
    public void anyServiceFindByEmailMethod() {
    }

    @Pointcut("execution(public * by.itacademy.railway.service.*Service.findAvatar(..))")
    public void anyServiceFindAvatarMethod() {
    }

    @Pointcut("execution(public * by.itacademy.railway.service.*Service.updateRole(..))")
    public void anyServiceUpdateRoleMethod() {
    }

    @Pointcut("execution(public * by.itacademy.railway.service.*Service.changePassword(..))")
    public void anyServiceChangePasswordMethod() {
    }

    @Pointcut("execution(public * by.itacademy.railway.service.*Service.findByTrainIdAndStation(..))")
    public void anyServiceFindByTrainAndStationMethod() {
    }

    @Pointcut("execution(public * by.itacademy.railway.service.*Service.update(..))")
    public void anyServiceUpdateMethod() {
    }


    @Before(value = "anyServiceFindAllMethod()")
    public void checkFindAllMethod(JoinPoint joinPoint) {
        log.info("Invoke findAll() method in {} with params: {}", joinPoint.getTarget(), joinPoint.getArgs());
    }

    @Before(value = "anyServiceFindAllByUserIdMethod()")
    public void checkFindAllByUserIdMethod(JoinPoint joinPoint) {
        log.info("Invoke findAllByUserId() method in {} with params: {}", joinPoint.getTarget(), joinPoint.getArgs());
    }

    @Before(value = "anyServiceFindByIdMethod()")
    public void checkFindByIdMethod(JoinPoint joinPoint) {
        log.info("Invoke findById() method in {} with params: {}", joinPoint.getTarget(), joinPoint.getArgs());
    }

    @Before(value = "anyServiceCreateMethod()")
    public void checkCreateMethod(JoinPoint joinPoint) {
        log.info("Invoke create() method in {} with params: {}", joinPoint.getTarget(), joinPoint.getArgs());
    }

    @Before(value = "anyServiceRemoveMethod()")
    public void checkRemoveMethod(JoinPoint joinPoint) {
        log.info("Invoke remove() method in {} with params: {}", joinPoint.getTarget(), joinPoint.getArgs());
    }

    @Before(value = "anyServiceUpdateMethod()")
    public void checkUpdateMethod(JoinPoint joinPoint) {
        log.info("Invoke update() method in {} with params: {}", joinPoint.getTarget(), joinPoint.getArgs());
    }

    @Before(value = "anyServiceFindByTrainAndStationMethod()")
    public void checkFindByTrainIdAndStationMethod(JoinPoint joinPoint) {
        log.info("Invoke findByTrainIdAndStation() method in {} with params: {}", joinPoint.getTarget(), joinPoint.getArgs());
    }

    @Before(value = "anyServiceFindByWagonIdMethod()")
    public void checkFindByWagonIdMethod(JoinPoint joinPoint) {
        log.info("Invoke findByWagonId() method in {} with params: {}", joinPoint.getTarget(), joinPoint.getArgs());
    }

    @Before(value = "anyServiceFindByTrainIdMethod()")
    public void checkFindByTrainIdMethod(JoinPoint joinPoint) {
        log.info("Invoke findByTrainId() method in {} with params: {}", joinPoint.getTarget(), joinPoint.getArgs());
    }

    @Before(value = "anyServiceFindByRouteMethod()")
    public void checkFindByRouteMethod(JoinPoint joinPoint) {
        log.info("Invoke findByRoute() method in {} with params: {}", joinPoint.getTarget(), joinPoint.getArgs());
    }

    @Before(value = "anyServiceFindByEmailMethod()")
    public void checkFindByEmailMethod(JoinPoint joinPoint) {
        log.info("Invoke findByEmail() method in {} with params: {}", joinPoint.getTarget(), joinPoint.getArgs());
    }

    @Before(value = "anyServiceFindAvatarMethod()")
    public void checkAvatarMethod(JoinPoint joinPoint) {
        log.info("Invoke findAvatar() method in {} with params: {}", joinPoint.getTarget(), joinPoint.getArgs());
    }

    @Before(value = "anyServiceUpdateRoleMethod()")
    public void checkUpdateRoleMethod(JoinPoint joinPoint) {
        log.info("Invoke updateRole() method in {} with params: {}", joinPoint.getTarget(), joinPoint.getArgs());
    }

    @Before(value = "anyServiceChangePasswordMethod()")
    public void checkChangePasswordMethod(JoinPoint joinPoint) {
        log.info("Invoke changePassword() method in {} with params: {}", joinPoint.getTarget(), joinPoint.getArgs());
    }

    @AfterReturning(value = "anyServiceFindAllMethod() && target(service)", returning = "result", argNames = "result,service")
    public void addLoggingAfterReturningFindAllMethod(Object result, Object service) {
        log.info("After findAll() method in class {}, return {}", service, result);
    }

    @AfterReturning(value = "anyServiceFindAllByUserIdMethod() && target(service)", returning = "result", argNames = "result,service")
    public void addLoggingAfterReturningFindAllByUserIdMethod(Object result, Object service) {
        log.info("After findAllByUserId() method in class {}, return {}", service, result);
    }

    @AfterReturning(value = "anyServiceFindByIdMethod() && target(service)", returning = "result", argNames = "result,service")
    public void addLoggingAfterReturningFindByIdMethod(Object result, Object service) {
        log.info("After findById() method in class {}, return {}", service, result);
    }

    @AfterReturning(value = "anyServiceCreateMethod() && target(service)", returning = "result", argNames = "result,service")
    public void addLoggingAfterReturningCreateMethod(Object result, Object service) {
        log.info("After create() method in class {}, return {}", service, result);
    }

    @AfterReturning(value = "anyServiceUpdateMethod() && target(service)", returning = "result", argNames = "result,service")
    public void addLoggingAfterReturningUpdateMethod(Object result, Object service) {
        log.info("After update() method in class {}, with result {}", service, result);
    }

    @AfterReturning(value = "anyServiceFindByTrainAndStationMethod() && target(service)", returning = "result", argNames = "result,service")
    public void addLoggingAfterReturningFindByTrainAndStationMethod(Object result, Object service) {
        log.info("After finByTrainAndStation() method in class {}, with result {}", service, result);
    }

    @AfterReturning(value = "anyServiceFindByWagonIdMethod() && target(service)", returning = "result", argNames = "result,service")
    public void addLoggingAfterReturningFindByWagonIdMethod(Object result, Object service) {
        log.info("After findByWagonId() method in class {}, with result {}", service, result);
    }

    @AfterReturning(value = "anyServiceFindByRouteMethod() && target(service)", returning = "result", argNames = "result,service")
    public void addLoggingAfterReturningFindByRouteMethod(Object result, Object service) {
        log.info("After findByRoute() method in class {}, with result {}", service, result);
    }

    @AfterReturning(value = "anyServiceFindByEmailMethod() && target(service)", returning = "result", argNames = "result,service")
    public void addLoggingAfterReturningFindByEmailMethod(Object result, Object service) {
        log.info("After findByEmail() method in class {}, with result {}", service, result);
    }

    @AfterReturning(value = "anyServiceFindAvatarMethod() && target(service)", returning = "result", argNames = "result,service")
    public void addLoggingAfterReturningFindAvatarMethod(Object result, Object service) {
        log.info("After findAvatar() method in class {}, with result {}", service, result);
    }


}
