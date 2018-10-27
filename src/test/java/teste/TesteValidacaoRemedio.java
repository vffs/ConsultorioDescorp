package teste;

import consultoriodescorp.Remedio;
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
public class TesteValidacaoRemedio extends TesteBase {

    @Test(expected = ConstraintViolationException.class)
    public void criarRemedioInvalido() {
        Remedio r = new Remedio();
        try {
            r.setNomeRemedio("Vitamina C");
            r.setDuracao(null);/* duração do tratamento nula*/
            r.setTratamento("");/*tratamento vazio*/
            em.persist(r);
        } catch (ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

            for (ConstraintViolation violation : constraintViolations) {
                assertThat(violation.getRootBeanClass() + "." + violation.getPropertyPath() + ": " + violation.getMessage(),
                        CoreMatchers.anyOf(
                            startsWith("class consultoriodescorp.Remedio.tratamento: Não pode estar em branco"),
                            startsWith("class consultoriodescorp.Remedio.duracao: Não pode estar em branco")
                        )
                );
            }
            assertEquals(2, constraintViolations.size());
            assertNull(r.getId());
            throw ex;

        }

    }

    @Test(expected = ConstraintViolationException.class)
    public void atualizaRemedioInvalido() {
        Remedio r;
        String consulta = "SELECT r FROM Remedio r WHERE r.id=?1";
        TypedQuery<Remedio> query = em.createQuery(consulta, Remedio.class);
        query.setParameter(1, 3);
        r = query.getSingleResult();
        r.setNomeRemedio("");/* nome vazio*/
 
        try { 
            em.flush();
        } catch (ConstraintViolationException ex) {
            ConstraintViolation violation = ex.getConstraintViolations().iterator().next();
            assertEquals("Não pode estar em branco", violation.getMessage());
            assertEquals(1, ex.getConstraintViolations().size());
            throw ex;
        }
    }
}
