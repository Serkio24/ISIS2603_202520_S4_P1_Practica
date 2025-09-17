package co.edu.uniandes.dse.parcial1.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import co.edu.uniandes.dse.parcial1.entities.UbicacionBodegaEntity;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(UbicacionBodegaService.class)
public class UbicacionBodegaServiceTest {

    @Autowired
    private UbicacionBodegaService ubicacionBodegaService;

    @Autowired
    private EntityManager entityManager;

    private PodamFactory podamFactory = new PodamFactoryImpl();

    @BeforeEach
    void setUp(){
        clearData();
    }

    private void clearData(){
        entityManager.createQuery("Delete from UbicacionBodegaEntity").executeUpdate();
    }

    @Test
    void testCreateUbicacionBodega() throws IllegalOperationException{
        UbicacionBodegaEntity newUbicacion = podamFactory.manufacturePojo(UbicacionBodegaEntity.class);
        newUbicacion.setNumeroEstante(100);

        UbicacionBodegaEntity result = ubicacionBodegaService.createUbicacionBodega(newUbicacion);
        assertNotNull(result);
        UbicacionBodegaEntity entity = entityManager.find(UbicacionBodegaEntity.class, result.getId());
        assertEquals(newUbicacion.getId(), entity.getId());
        assertEquals(newUbicacion.getCanasta(), entity.getCanasta());
        assertEquals(newUbicacion.getNumeroEstante(), entity.getNumeroEstante());
        assertEquals(newUbicacion.getPesoMax(), entity.getPesoMax());
    }

    @Test
    void testCreateUbicacionBodegaEstanteNoValido() throws IllegalOperationException{
        UbicacionBodegaEntity newUbicacion = podamFactory.manufacturePojo(UbicacionBodegaEntity.class);
        newUbicacion.setNumeroEstante(0);

        assertThrows(IllegalOperationException.class,() -> {
            ubicacionBodegaService.createUbicacionBodega(newUbicacion);
        });
    }


    
}