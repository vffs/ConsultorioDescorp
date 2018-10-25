
package consultoriodescorp;

import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author valeria
 */
public class ValidadorEscolaridade implements ConstraintValidator<ValidaEscolaridade , String>{
    private List<String>escolaridades;
   
    @Override
    public void initialize(ValidaEscolaridade validaEscolaridade) {
        this.escolaridades = new ArrayList<>();
        this.escolaridades.add("ENSINO FUNDAMENTAL INCOMPLETO");
        this.escolaridades.add("ENSINO FUNDAMENTAL COMPLETO");
        this.escolaridades.add("ENSINO MEDIO INCOMPLETO");
        this.escolaridades.add("ENSINO MEDIO COMPLETO");
        this.escolaridades.add("ENSINO SUPERIOR INCOMPLETO");
        this.escolaridades.add("ENSINO SUPERIOR COMPLETO");
    }

     @Override
    public boolean isValid(String valor, ConstraintValidatorContext context) {
        return valor == null ? false : escolaridades.contains(valor);
    }
    
}
