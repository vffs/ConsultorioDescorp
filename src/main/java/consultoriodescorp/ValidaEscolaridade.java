
package consultoriodescorp;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 *
 * @author valeria
 */
@Target( {ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidadorEscolaridade.class)
@Documented
public @interface ValidaEscolaridade {
    
     String message() default "{consultoriodescorp.Funcionario.escolaridade}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
