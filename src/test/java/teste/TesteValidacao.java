package teste;

import consultoriodescorp.Funcionario;
import consultoriodescorp.Paciente;
import consultoriodescorp.TipoFuncionario;
import consultoriodescorp.TipoPlanoSaude;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author valeria
 */
public class TesteValidacao extends TesteBase {

    @Test
    public void t01_criarPacienteValido1() {
        Paciente p = new Paciente();

        p.setNome("Jose Augusto");
        p.setEmail("joseaugusto@hotmail.com");
        p.setLogin("joseaugusto");
        p.setSenha("JoseA123");
        p.setSexo("M");
        p.setPlano(TipoPlanoSaude.SEMPLANO);
      /*  Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        validator.validate(p);*/
        em.persist(p);
        em.flush();
        assertNotNull(p.getId());
    }

    @Test
    public void t02_CriarPacienteInvalido() {
        Paciente p = new Paciente();
        //try{
        p.setNome("Carolina Maria");
        p.setEmail("carolina#teste");/* e-mail invalido*/
        p.setLogin(" Carolina Maria ");/* login com espaço*/
        p.setSexo("F");
        p.setPlano(TipoPlanoSaude.UNIMED);
        p.setSenha("CarolinaMaria123");
        em.persist(p);
        em.flush();
        assertTrue(false);
        /*}catch(ConstraintViolationException ex){
            Logger.getGlobal().info(ex.getMessage());
            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

            if (logger.isLoggable(Level.INFO)) {
                for (ConstraintViolation violation : constraintViolations) {
                    Logger.getGlobal().log(Level.INFO, "{0}.{1}: {2}", new Object[]{violation.getRootBeanClass(), violation.getPropertyPath(), violation.getMessage()});
                }
            }

            assertEquals(2, constraintViolations.size());
            assertNull(p.getId());
        }*/
    }
    
    @Test
    public void T03_criarFuncionarioValido(){
        Funcionario f = new Funcionario();
        
        f.setNome("Clarisse Lima");
        f.setEmail("clarissel@yahoo.com.br");
        f.setLogin("clarisse.lima");
        f.setSexo("F");
        f.setSenha("Clarisselima123");
        f.setTipo(TipoFuncionario.MEDICO);
        f.setEscolaridade("ENSINO MEDIO COMPLETO");
        f.setEspecialidade("FISIOTERAPEUTA");
        
        em.persist(f);
        em.flush();
        assertNotNull(f.getId());
    }
}
