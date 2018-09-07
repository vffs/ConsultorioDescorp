package teste;

import com.mycompany.consultoriodescorp.Paciente;
import com.mycompany.consultoriodescorp.TipoPlanoSaude;
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
public class TestePacienteCrud {

    private static EntityManagerFactory emf;
    private static Logger logger = Logger.getGlobal();
    private EntityManager em;
    private EntityTransaction et;

    public TestePacienteCrud() {
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
    public void t01_persistirPaciente() {
        logger.info("Executando t01: persistir paciente");

        Paciente paciente = new Paciente();

        paciente.setNome("Maria Joana");
        paciente.setLogin("mariajoana");
        paciente.setEmail("mariaj@gmail.com");
        paciente.setSenha("mariaj123");
        paciente.setSexo("F");
        paciente.setPlano(TipoPlanoSaude.HAPVIDA);

        em.persist(paciente);
        em.flush();
        assertNotNull(paciente.getId());
        logger.log(Level.INFO, "Funcionario {0} incluída com sucesso.", paciente);
    }

    @Test
    public void t02_atualizarpaciente() {
        logger.info("Executando t02: atualizar paciente");
        Paciente paciente;
        String consulta = "SELECT p FROM Paciente p WHERE  p.senha=?1 ";
        Query query = em.createQuery(consulta);
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        query.setParameter(1, "joao123");
        paciente = (Paciente) query.getSingleResult();
        paciente.setSenha("MacacosMeMordam");
        em.flush();
        query.setParameter(1, "MacacosMeMordam");
        paciente = (Paciente) query.getSingleResult();
        
        assertEquals("MacacosMeMordam", paciente.getSenha());

    }

    @Test
    public void t03_atualizarPacienteMerge() {
        logger.info("Executando t03: atualizar paciente com Merge");
        Paciente paciente;
        String consulta = "SELECT p FROM Paciente p WHERE p.login=?1";
        Query query = em.createQuery(consulta);
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        query.setParameter(1, "mariajoaquina");
        paciente = (Paciente) query.getSingleResult();
        assertNotNull(paciente);
        em.clear();
        paciente.setLogin("mariaj");
        em.merge(paciente);
        em.flush();
        query.setParameter(1, "mariaj");
        paciente = (Paciente) query.getSingleResult();
        assertEquals("mariaj", paciente.getLogin());
    }

    @Test
    public void t04_persistirPaciente() {
        logger.info("Executando t01: persistir Paciente");

        Paciente paciente = new Paciente();
        paciente.setNome("Joao Pedro");
        paciente.setLogin("Joao Pedro");
        paciente.setEmail("joaopedro@gmail.com");
        paciente.setSenha("joao123");
        paciente.setSexo("M");
        paciente.setPlano(TipoPlanoSaude.AMIL);
        em.persist(paciente);
        em.flush();
        assertNotNull(paciente.getId());
        logger.log(Level.INFO, "Paciente {0} incluída com sucesso.", paciente);
    }

    @Test
    public void t05_delete() {
        logger.info("Executando t05: DELETE paciente");
        Paciente paciente;
        String consulta = "SELECT p FROM Paciente p WHERE p.id=?1";
        Query query = em.createQuery(consulta);
        long id = 1;
        query.setParameter(1, id);
        paciente = (Paciente) query.getSingleResult();
        em.remove(paciente);
        em.flush();
        Map map = new HashMap();
        map.put("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        Paciente deletado = em.find(Paciente.class, id, map);
        assertNull(deletado);

    }

}
