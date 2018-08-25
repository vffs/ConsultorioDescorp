package teste;

import com.mycompany.consultoriodescorp.Funcionario;
import com.mycompany.consultoriodescorp.Paciente;
import com.mycompany.consultoriodescorp.TipoFuncionario;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        emf = Persistence.createEntityManagerFactory("ConsultorioDescorp");
//        DbUnitUtil.inserirDados();
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
        Paciente fun=em.find(Paciente.class, new Long(1));
        Paciente paciente=new Paciente();
        
        paciente.setNome("Maria Joana");
        paciente.setLogin("mariajoana");
        paciente.setEmail("mariaj@gmail.com");
        paciente.setSenha("mariaj123");
        paciente.setSexo("F");
        paciente.setPlano("Unimed");
              
        em.persist(paciente);
        em.flush();
        assertNotNull(paciente.getId());
        logger.log(Level.INFO, "Funcionario {0} incluída com sucesso.", paciente);
    }
  
@Test
    public void t02_atualizarpaciente() {
        logger.info("Executando t02: atualizar paciente");
        Paciente paciente;
        String consulta="SELECT p FROM Paciente p WHERE p.email=?1";
        Query query = em.createQuery(consulta);
        query.setParameter(1,"mariaj@gmail.com");
        paciente=(Paciente)query.getSingleResult();
        paciente.setEmail("mariajoana@gmail.com");
        em.flush();
        assertEquals("mariajoana@gmail.com",paciente.getEmail());
        
    }
    
     @Test
    public void t03_atualizarPacienteMerge() {
        logger.info("Executando t03: atualizar paciente com Merge");
        Paciente paciente;
        String consulta="SELECT p FROM Paciente p WHERE p.login=?1";
        Query query = em.createQuery(consulta);
        query.setParameter(1, "mariajoana");
        paciente=(Paciente)query.getSingleResult();
        assertNotNull(paciente);
        em.clear();
        paciente.setLogin("mariaj");
        em.merge(paciente);
        em.flush();
        assertEquals("mariaj",paciente.getLogin());
    }
    @Test
    public void t04_persistirPaciente() {
        logger.info("Executando t01: persistir Paciente");
        Paciente pac=em.find(Paciente.class, new Long(2));
        Paciente paciente=new Paciente();
        paciente.setNome("Joao Augusto");
        paciente.setLogin("JoaoAugusto");
        paciente.setEmail("joaoaugusto@gmail.com");
        paciente.setSenha("joao123");
        paciente.setSexo("M");
        paciente.setPlano("Amil");
        em.persist(paciente);
        em.flush();
        assertNotNull(paciente.getId());
        logger.log(Level.INFO, "Paciente {0} incluída com sucesso.",paciente);
    }

    @Test
    public void t05_delete() {
        logger.info("Executando t31: DELETE paciente");
        Paciente paciente;
        String consulta="SELECT p FROM Paciente p WHERE p.id=?1";
        Query query = em.createQuery(consulta);
        long id=1;
        query.setParameter(1, id);
            paciente=(Paciente)query.getSingleResult();
            em.remove(paciente);
            em.flush();
        Paciente deletado=em.find(Paciente.class, id);
        assertNull(deletado);
        
    }

    
}
