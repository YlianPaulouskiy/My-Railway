package by.itacademy.railway.annotation;

import by.itacademy.railway.DatabaseApplication;
import by.itacademy.railway.configuration.PostgresSqlExtension;
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

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ActiveProfiles("test")
@SpringBootTest(classes = DatabaseApplication.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@ExtendWith(PostgresSqlExtension.class)
@Sql({"classpath:sql/schema.sql",
        "classpath:sql/data.sql"})
@Transactional
public @interface IT {
}

