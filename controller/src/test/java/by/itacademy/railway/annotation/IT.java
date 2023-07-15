package by.itacademy.railway.annotation;

import by.itacademy.railway.ControllerApplication;
import by.itacademy.railway.DatabaseApplication;
import by.itacademy.railway.config.PostgresSqlExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Transactional
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ActiveProfiles("test")
@ExtendWith(PostgresSqlExtension.class)
@Sql({"classpath:sql/schema.sql",
        "classpath:sql/data.sql"})
@SpringBootTest(classes = ControllerApplication.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public @interface IT {
}

