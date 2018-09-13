package teste;

import com.mycompany.consultoriodescorp.Exame;
import com.mycompany.consultoriodescorp.Funcionario;
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
public class TesteExame {

    private static EntityManagerFactory emf;
    private static Logger logger = Logger.getGlobal();
    private EntityManager em;
    private EntityTransaction et;

    public TesteExame() {
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
    public void t01_persistiExame() {
        logger.info("Executando t01: persistir exame");
        
        Exame exame = new Exame();
        String consulta = "SELECT f FROM Funcionario f WHERE f.id=?1";
        Query query = em.createQuery(consulta); 
        query.setParameter(1,5);
        Funcionario funcionario;
        funcionario=(Funcionario)query.getSingleResult();
        funcionario.getId();
        exame.setMedico(funcionario);  
        exame.setNome("Ecocardiograma"); 
        
        em.persist(exame);
        em.flush();
        assertNotNull(exame.getId());
        logger.log(Level.INFO, "Exame {0} incluído com sucesso.", exame);
    }

    @Test
    public void t02_atualizarExame() {
        logger.info("Executando t02: atualizar Exame ");
        Exame exame;
        String consulta = "SELECT e FROM Exame e WHERE e.nome=?1";
        Query query = em.createQuery(consulta);
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        query.setParameter(1,"Ultrasom do torax");
        exame = (Exame) query.getSingleResult();
        exame.setNome("Tomografia do torax");
       
        em.flush();
        query.setParameter(1, "Tomografia do torax");
        exame = (Exame) query.getSingleResult();
        
        assertEquals("Tomografia do torax", exame.getNome());

    }
   
    
    @Test
    public void t03_atualizarExameMerge() {
        logger.info("Executando t03: atualizar exame com Merge");
        Exame exame;
        String consulta = "SELECT e FROM Exame e WHERE e.nome=?1";
        Query query = em.createQuery(consulta);
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        query.setParameter(1 ,"Esteira");
        exame = (Exame) query.getSingleResult();
        em.clear();
        exame.setNome("Ultrasom do torax");
        em.merge(exame);
        em.flush();
        
        query.setParameter(1 ,"Ultrasom do torax");
        exame = (Exame) query.getSingleResult();
        
        assertEquals("Ultrasom do torax", exame.getNome());
    }
    
    @Test
    public void t05_delete() {
        logger.info("Executando t05: DELETE exame");
        Exame exame;
        String consulta = "SELECT e FROM  Exame e WHERE e.id=?1";
        Query query = em.createQuery(consulta);
        long id = 4;
        query.setParameter(1, id);
        exame = (Exame) query.getSingleResult();
        em.remove(exame);
        em.flush();
        Map map = new HashMap();
        map.put("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        Exame deletado = em.find(Exame.class, id, map);
        assertNull(deletado);

    }

}
