package co.edu.uniandes.dse.parcial1.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import co.edu.uniandes.dse.parcial1.entities.MercanciaEntity;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import uk.co.jemos.podam.api.PodamFactory;

@DataJpaTest
@Transactional
@Import(MercanciaService.class)
public class MercanciaServiceTest {
    
    @Autowired
    private MercanciaService mercanciaService;

    @Autowired
    private EntityManager entityManager;

    private PodamFactory podamFactory = new PodamFactoryImpl();

    @BeforeEach
    void setUp(){
        clearData();
    }

    private void clearData(){
        entityManager.createQuery("Delete from MercanciaEntity").executeUpdate();
    }
    
    @Test
    void testCreateMercancia() throws IllegalOperationException{
        MercanciaEntity newMercancia = podamFactory.manufacturePojo(MercanciaEntity.class);
        newMercancia.setFechaRecepcion(LocalDateTime.now().minusDays(3));

        MercanciaEntity result = mercanciaService.createMercancia(newMercancia);
        assertNotNull(result);
        
        MercanciaEntity entity = entityManager.find(MercanciaEntity.class, result.getId());
        assertEquals(newMercancia.getCantidad(), entity.getCantidad());
        assertEquals(newMercancia.getCodigoBarras(), entity.getCodigoBarras());
        assertEquals(newMercancia.getNombre(), entity.getNombre());
        assertEquals(newMercancia.getId(), entity.getId());
    }

    @Test
    void testCreateMercanciaNombreInvalido() throws IllegalOperationException{
        MercanciaEntity newMercancia = podamFactory.manufacturePojo(MercanciaEntity.class);
        newMercancia.setFechaRecepcion(LocalDateTime.now().minusDays(3));
        newMercancia.setNombre(null);

        assertThrows(IllegalOperationException.class,() -> {
            mercanciaService.createMercancia(newMercancia);
        });
    }
}
