package teste;

import consultoriodescorp.Funcionario;
import consultoriodescorp.TipoFuncionario;
import java.util.Set;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.hamcrest.CoreMatchers;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 *
 * @author valeria
 */
public class TesteValidacaoFuncionario extends TesteBase {

    @Test(expected = ConstraintViolationException.class)
    public void criarFuncionarioInvalido() {
        Funcionario f = new Funcionario();
        try {
            f.setNome("Mauricio Pedro");
            f.setEmail("mauriciop@hotmail.com");
            f.setLogin("mauriciopedro");
            f.setSexo("M");
            f.setSenha("Mauricio123");
            f.setTipo(TipoFuncionario.MEDICO);
            f.setEscolaridade("ESC");/*Escolaridade invalida*/
            f.setEspecialidade("CARDIOLOGISTA");
            em.persist(f);
        } catch (ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

            for (ConstraintViolation violation : constraintViolations) {
                assertThat(violation.getRootBeanClass() + "." + violation.getPropertyPath() + ": " + violation.getMessage(),
                        CoreMatchers.anyOf(
                                startsWith("class consultoriodescorp.Funcionario.escolaridade: Escolaridade invalida.")
                                
                        )
                );
            }
            assertEquals(1, constraintViolations.size());
            assertNull(f.getId());
            throw ex;

        }

    }

    @Test(expected = ConstraintViolationException.class)
    public void atualizarFuncionarioInvalido() {
        Funcionario f;
        String consulta = "SELECT f FROM Funcionario f WHERE f.id=?1";
        TypedQuery<Funcionario> query = em.createQuery(consulta, Funcionario.class);
        query.setParameter(1, 6);
        f = query.getSingleResult();
        f.setEscolaridade("SUPERIOR INCOMPLETO");

        try {
            em.flush();
        } catch (ConstraintViolationException ex) {
            ConstraintViolation violation = ex.getConstraintViolations().iterator().next();
            assertEquals("Escolaridade invalida. ", violation.getMessage());
            assertEquals(1, ex.getConstraintViolations().size());
            throw ex;
        }
    }
}
