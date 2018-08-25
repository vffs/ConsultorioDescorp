package teste;

import com.mycompany.consultoriodescorp.Funcionario;
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
public class TesteFuncionarioCrud {

    private static EntityManagerFactory emf;
    private static Logger logger = Logger.getGlobal();
    private EntityManager em;
    private EntityTransaction et;

    public TesteFuncionarioCrud() {
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
    public void t01_persistirFuncionario() {
        logger.info("Executando t01: persistir funcionario");
        Funcionario fun=em.find(Funcionario.class, new Long(1));
        Funcionario funcionario=new Funcionario();
        
        funcionario.setNome("Joaquina Baquita");
        funcionario.setLogin("joaquinabaquita");
        funcionario.setEmail("joaquinabaq@gmail.com");
        funcionario.setSenha("joaquina123");
        funcionario.setSexo("F");
        funcionario.setTipo(TipoFuncionario.ATENDENTE);
              
        em.persist(funcionario);
        em.flush();
        assertNotNull(funcionario.getId());
        logger.log(Level.INFO, "Funcionario {0} incluída com sucesso.", funcionario);
    }
  
@Test
    public void t02_atualizarFuncionario() {
        logger.info("Executando t02: atualizar funcionario");
        Funcionario funcionario;
        String consulta="SELECT f FROM Funcionario f WHERE f.email=?1";
        Query query = em.createQuery(consulta);
        query.setParameter(1,"joaquinabaq@gmail.com");
        funcionario=(Funcionario)query.getSingleResult();
        funcionario.setEmail("joaquinabaquita@gmail.com");
        em.flush();
        assertEquals("joaquinabaquita@gmail.com",funcionario.getEmail());
        
    }
    
     @Test
    public void t03_atualizarFuncionarioMerge() {
        logger.info("Executando t03: atualizar funcionario com Merge");
        Funcionario funcionario;
        String consulta="SELECT f FROM Funcionario f WHERE f.login=?1";
        Query query = em.createQuery(consulta);
        query.setParameter(1, "joaquinabaquita");
        funcionario=(Funcionario)query.getSingleResult();
        assertNotNull(funcionario);
        em.clear();
        funcionario.setLogin("joaquinabaq");
        em.merge(funcionario);
        em.flush();
        assertEquals("joaquinabaq",funcionario.getLogin());
    }
    @Test
    public void t04_persistirFuncionario() {
        logger.info("Executando t01: persistir funcionario");
        Funcionario fun=em.find(Funcionario.class, new Long(2));
        Funcionario funcionario= new Funcionario();
        funcionario.setNome("Maria Izabel");
        funcionario.setLogin("mariaiza");
        funcionario.setEmail("mariaiza@gmail.com");
        funcionario.setSenha("mariaiza123");
        funcionario.setSexo("F");
        funcionario.setTipo(TipoFuncionario.MEDICO);
        em.persist(funcionario);
        em.flush();
        assertNotNull(funcionario.getId());
        logger.log(Level.INFO, "Funcionario {0} incluída com sucesso.", funcionario);
    }

    @Test
    public void t05_delete() {
        logger.info("Executando t31: DELETE funcionario");
        Funcionario funcionario;
        String consulta="SELECT f FROM Funcionario f WHERE f.id=?1";
        Query query = em.createQuery(consulta);
        long id=1;
        query.setParameter(1, id);
            funcionario=(Funcionario)query.getSingleResult();
            em.remove(funcionario);
            em.flush();
        Funcionario deletado=em.find(Funcionario.class, id);
        assertNull(deletado);
        
    }

    
}
