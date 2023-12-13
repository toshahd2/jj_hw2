import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Test {
    int order() default 0;
}


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface BeforeEach {
}

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface AfterEach {
}


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface Skip {
}
