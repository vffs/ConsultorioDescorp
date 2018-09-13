package teste;

import com.mycompany.consultoriodescorp.Funcionario;
import com.mycompany.consultoriodescorp.Receita;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

/**
 *
 * @author MASC
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SuppressWarnings("JPQLValidation")
public class TesteReceita {

    private static EntityManagerFactory emf;
    private static Logger logger = Logger.getGlobal();
    private EntityManager em;
    private EntityTransaction et;

    public TesteReceita() {
    }

    @BeforeClass
    public static void setUpClass() {
        //logger.setLevel(Level.INFO);
        logger.setLevel(Level.SEVERE);
        emf = Persistence.createEntityManagerFactory("consultorio");
        DbUnitUtil.inserirDados();
    }

    @AfterClass
    public static void tearDownClass() {
        emf.close();
    }

    @Before
    public void setUp() {
        em = emf.createEntityManager();
        beginTransaction();
    }

    @After
    public void tearDown() {
        commitTransaction();
        em.close();
    }

    private void beginTransaction() {
        et = em.getTransaction();
        et.begin();
    }

    private void commitTransaction() {
        try {
            et.commit();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            et.rollback();
            fail(ex.getMessage());
        }
    }

    @Test
    public void t01_persistiReceita() {
        logger.info("Executando t01: persistir receita");
        
        Receita receita = new Receita();
        String consulta = "SELECT f FROM Funcionario f WHERE f.id=?1";
        Query query = em.createQuery(consulta); 
        query.setParameter(1,9);
        Funcionario funcionario;
        funcionario=(Funcionario)query.getSingleResult();
        funcionario.getId();
        receita.setMedico(funcionario);
        receita.setNomeRemedio("Busonid");
        receita.setTratamento("Desintupir vias respiratorias");
        receita.setDuracao("15 dias");
        
        em.persist(receita);
        em.flush();
        assertNotNull(receita.getId());
        logger.log(Level.INFO, "Exame {0} incluído com sucesso.", receita);
    }

    @Test
    public void t02_atualizarReceita() {
        logger.info("Executando t02: atualizar Receita ");
        Receita receita;
        String consulta = "SELECT r FROM Receita r WHERE r.tratamento=?1";
        Query query = em.createQuery(consulta);
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        query.setParameter(1,"Queimadura");
        receita = (Receita) query.getSingleResult();
        receita.setTratamento("Queimadura e assadura");
       
        em.flush();
        query.setParameter(1, "Queimadura e assadura");
        receita = (Receita) query.getSingleResult();
        
        assertEquals("Queimadura e assadura", receita.getTratamento());

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
        receita.setNomeRemedio("Melxin");
        em.merge(receita);
        em.flush();
        
        query.setParameter(1 ,"Melxin");
        receita = (Receita) query.getSingleResult();
        
        assertEquals("Melxin", receita.getNomeRemedio());
    }
    
    @Test
    public void t05_delete() {
        logger.info("Executando t05: DELETE exame");
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

    }

}
