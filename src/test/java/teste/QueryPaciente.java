package teste;

import com.mycompany.consultoriodescorp.Paciente;
import com.mycompany.consultoriodescorp.TipoPlanoSaude;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

/**
 *
 * @author Valéria
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SuppressWarnings("JPQLValidation")
public class QueryPaciente extends TesteBase{

    @Test
    public void t01_listarPacientes() {
        logger.info("Executando t01: listar pacientes");

        List<Paciente>pacientes;
        Query query = em.createNamedQuery("Listar.Paciente", Paciente.class);
        pacientes = query.getResultList();
        
        assertEquals(5 , pacientes.size());
    }

    @Test
    public void t02_listarPacientesPorPlano() {
        logger.info("Executando t02: listar pacientes por plano HAPVIDA");
        List<Paciente> pacientes;
        TipoPlanoSaude tipo = null;
        TypedQuery<Paciente> query = em.createNamedQuery("Listar.Paciente.Planos", Paciente.class);
        query.setParameter(1 , tipo.HAPVIDA);
        pacientes = query.getResultList();
        
        for(Paciente paciente : pacientes){
           paciente.getPlano();
        }
        assertEquals(2 , pacientes.size());
    }

    @Test
    public void t03_SelecionarDiferentesPlanosDePaciente() {
        logger.info("Executando t03: Selecionar Diferente Planos de Paciente");
       List<String> pacientes; 
       TypedQuery<String> query = em.createQuery("SELECT DISTINCT(p.plano) FROM Paciente p ORDER BY p.plano", String.class);
       pacientes = query.getResultList();
       assertEquals(3 , pacientes.size());
          
       
    }  

}
