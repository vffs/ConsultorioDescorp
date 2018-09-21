package teste;

import com.mycompany.consultoriodescorp.Funcionario;
import com.mycompany.consultoriodescorp.Receita;
import com.mycompany.consultoriodescorp.Remedio;
import java.util.Calendar;
import java.util.logging.Level;
import javax.persistence.Query;
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
public class TesteReceita extends TesteBase {

    @Test
    public void t01_persistiReceita() {
        logger.info("Executando t01: persistir receita");
        
        Receita receita = new Receita();
        Calendar c = Calendar.getInstance();
        String consulta = "SELECT f FROM Funcionario f WHERE f.id=?1";
        Query query = em.createQuery(consulta); 
        query.setParameter(1,9);
        Funcionario funcionario;
        funcionario=(Funcionario)query.getSingleResult();
        funcionario.getId();
        receita.setMedico(funcionario);
        
        String consulta1 = "SELECT r FROM Remedio r WHERE r.id=?2";
        Query query1 = em.createQuery(consulta1);
        query1.setParameter(2,5);
        Remedio remedio;
        remedio=(Remedio)query1.getSingleResult();
        remedio.getId();
        receita.adicionarRemedio(remedio);
        
        c.set(2018, Calendar.SEPTEMBER, 28);
        receita.setDataReceita(c.getTime());
        
        em.persist(receita);
        em.flush();
        assertNotNull(receita.getId());
        logger.log(Level.INFO, "Receita {0} incluído com sucesso.", receita);
    }
/*
    @Test
    public void t02_atualizarReceita() {
        logger.info("Executando t02: atualizar Receita ");
        Receita receita;
        String consulta = "SELECT r FROM Receita r WHERE r.tratamento=?1";
        Query query = em.createQuery(consulta);
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        query.setParameter(1,"Queimadura");
        receita = (Receita) query.getSingleResult();
       // receita.setTratamento("Queimadura e assadura");
       
        em.flush();
        query.setParameter(1, "Queimadura e assadura");
        receita = (Receita) query.getSingleResult();
        
     //   assertEquals("Queimadura e assadura", receita.getTratamento());

    } 
    
    @Test
    public void t03_atualizarReceitaMerge() {
        logger.info("Executando t03: atualizar Receita com Merge");
        Receita receita;
        String consulta = "SELECT r FROM Receita r WHERE r.nomeRemedio=?1";
        Query query = em.createQuery(consulta);
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        query.setParameter(1 ,"Aerolim");
        receita = (Receita) query.getSingleResult();
        em.clear();
       // receita.setNomeRemedio("Melxin");
        em.merge(receita);
        em.flush();
        
        query.setParameter(1 ,"Melxin");
        receita = (Receita) query.getSingleResult();
        
       //s assertEquals("Melxin", receita.getNomeRemedio());
    }
    
    @Test
    public void t05_delete() {
        logger.info("Executando t05: DELETE Receita");
        Receita receita;
        String consulta = "SELECT r FROM  Receita r WHERE r.id=?1";
        Query query = em.createQuery(consulta);
        long id = 2;
        query.setParameter(1, id);
        receita = (Receita) query.getSingleResult();
        em.remove(receita);
        em.flush();
        Map map = new HashMap();
        map.put("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        Receita deletado = em.find(Receita.class, id, map);
        assertNull(deletado);

    }*/

}
