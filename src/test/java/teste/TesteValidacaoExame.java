package teste;

import consultoriodescorp.Exame;
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
public class TesteValidacaoExame extends TesteBase {

    @Test(expected = ConstraintViolationException.class)
    public void criarExameInvalido() {
        Exame e = new Exame();
        try {
            e.setNome("");/*nome do exame vazio*/
            em.persist(e);
        } catch (ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

            for (ConstraintViolation violation : constraintViolations) {
                assertThat(violation.getRootBeanClass() + "." + violation.getPropertyPath() + ": " + violation.getMessage(),
                        CoreMatchers.anyOf(
                            startsWith("class consultoriodescorp.Exame.nome: Não pode estar em branco")
                        )
                );
            }
            assertEquals(1, constraintViolations.size());
            assertNull(e.getId());
            throw ex;

        }

    }

    @Test(expected = ConstraintViolationException.class)
    public void atualizarExameinvalido() {
        Exame e;
        String consulta = "SELECT e FROM Exame e WHERE e.id=?1";
        TypedQuery<Exame> query = em.createQuery(consulta, Exame.class);
        query.setParameter(1, 4);
        e = query.getSingleResult();
        e.setNome(null);/* nome nulo*/
 
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
