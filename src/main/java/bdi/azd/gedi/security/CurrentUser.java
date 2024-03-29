package bdi.azd.gedi.security;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@Documented
@Retention(RUNTIME)
@Target({TYPE, PARAMETER})
@AuthenticationPrincipal(expression="user")
public @interface CurrentUser {

}
