package teste;

import com.mycompany.consultoriodescorp.Funcionario;
import com.mycompany.consultoriodescorp.MarcarConsulta;
import com.mycompany.consultoriodescorp.Paciente;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
public class TesteMarcarConsulta {

    private static EntityManagerFactory emf;
    private static Logger logger = Logger.getGlobal();
    private EntityManager em;
    private EntityTransaction et;

    public TesteMarcarConsulta() {
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
    public void t01_persistirMarcarConsulta() {
        logger.info("Executando t01: persistir Marcar consulta");
        Calendar c = Calendar.getInstance();
        MarcarConsulta marcar = new MarcarConsulta();
        String consulta = "SELECT f FROM Funcionario f WHERE f.id=?1";
        Query query = em.createQuery(consulta); 
        query.setParameter(1,5);
        Funcionario funcionario;
        funcionario=(Funcionario)query.getSingleResult();
        funcionario.getId();
        marcar.adicionarMedico(funcionario);
        
        String consulta1 = "SELECT p FROM Paciente p WHERE p.id=?2";
        Query query1 = em.createQuery(consulta1); 
        query1.setParameter(2,4);
        Paciente paciente;
        paciente=(Paciente)query1.getSingleResult();
        paciente.getId();
        marcar.setPaciente(paciente);
      
        c.set(2018, Calendar.OCTOBER, 01);
        marcar.setDataConsuta(c.getTime());
        marcar.setHora("14:00H");
        
        em.persist(marcar);
        em.flush();
        assertNotNull(marcar.getId());
        logger.log(Level.INFO, "Marcar Consulta {0} incluída com sucesso.", marcar);
    }

    @Test
    public void t02_atualizarMarcarConsulta() {
        logger.info("Executando t02: atualizar Marcar Consulta ");
        MarcarConsulta marcar;
        String consulta = "SELECT mc FROM MarcarConsulta mc WHERE mc.hora=?1";
        Query query = em.createQuery(consulta);
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        query.setParameter(1,"14:30H");
        marcar = (MarcarConsulta) query.getSingleResult();
        marcar.setHora("15:00H");
       
        em.flush();
        query.setParameter(1, "15:00H");
        marcar = (MarcarConsulta) query.getSingleResult();
        
        assertEquals("15:00H", marcar.getHora());

    }
    
    private Date getData(Integer dia, Integer mes, Integer ano) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, ano);
        c.set(Calendar.MONTH, mes);
        c.set(Calendar.DAY_OF_MONTH, dia);
        return c.getTime();
    }
    
    @Test
    public void t03_atualizarMarcarConsultaMerge() {
        logger.info("Executando t03: atualizar MarcarConsulta com Merge");
        MarcarConsulta marcar;
        String consulta = "SELECT mc FROM MarcarConsulta mc WHERE mc.dataConsuta=?1";
        Query query = em.createQuery(consulta);
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        query.setParameter(1 ,getData(25,Calendar.SEPTEMBER,2018));
        marcar = (MarcarConsulta) query.getSingleResult();
        em.clear();
        marcar.setDataConsuta(getData(05,Calendar.OCTOBER,2018));
        em.merge(marcar);
        em.flush();
        
        query.setParameter(1 ,getData(05,Calendar.OCTOBER,2018));
        marcar = (MarcarConsulta) query.getSingleResult();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String dataConsulta = dateFormat.format((Date) marcar.getDataConsuta());
        assertEquals("05-10-2018", dataConsulta);
    }
    
     

    @Test
    public void t05_delete() {
        logger.info("Executando t05: DELETE cosulta marcada");
        MarcarConsulta marcar;
        String consulta = "SELECT mc FROM MarcarConsulta mc WHERE mc.id=?1";
        Query query = em.createQuery(consulta);
        long id = 2;
        query.setParameter(1, id);
        marcar = (MarcarConsulta) query.getSingleResult();
        em.remove(marcar);
        em.flush();
        Map map = new HashMap();
        map.put("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        MarcarConsulta deletado = em.find(MarcarConsulta.class, id, map);
        assertNull(deletado);

    }

}
