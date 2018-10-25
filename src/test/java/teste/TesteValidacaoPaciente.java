package teste;

import consultoriodescorp.Paciente;
import consultoriodescorp.TipoPlanoSaude;
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
public class TesteValidacaoPaciente extends TesteBase {

    @Test(expected = ConstraintViolationException.class)
    public void criarPacienteInvalido() {
        Paciente p = new Paciente();
        try {
            p.setNome("Carolina Maria");
            p.setEmail("carolina#teste");/* e-mail invalido*/
            p.setLogin(null);/* login nulo*/
            p.setSexo("F");
            p.setPlano(TipoPlanoSaude.UNIMED);
            p.setSenha("CarolinaMaria123");
            em.persist(p);
        } catch (ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

            for (ConstraintViolation violation : constraintViolations) {
                assertThat(violation.getRootBeanClass() + "." + violation.getPropertyPath() + ": " + violation.getMessage(),
                        CoreMatchers.anyOf(
                                startsWith("class consultoriodescorp.Paciente.email: Não é um endereço de e-mail"),
                                startsWith("class consultoriodescorp.Paciente.login: Não pode estar em branco")
                        )
                );
            }
            assertEquals(2, constraintViolations.size());
            assertNull(p.getId());
            throw ex;

        }

    }

    @Test(expected = ConstraintViolationException.class)
    public void atualizarPacienteinvalido() {
        Paciente p;
        String consulta = "SELECT p FROM Paciente p WHERE p.id=?1";
        TypedQuery<Paciente> query = em.createQuery(consulta, Paciente.class);
        query.setParameter(1, 2);
        p = query.getSingleResult();
        p.setSenha("Og@to");

        try {
            em.flush();
        } catch (ConstraintViolationException ex) {
            ConstraintViolation violation = ex.getConstraintViolations().iterator().next();
            assertEquals("tamanho deve estar entre 6 e 20", violation.getMessage());
            assertEquals(1, ex.getConstraintViolations().size());
            throw ex;
        }
    }
}
