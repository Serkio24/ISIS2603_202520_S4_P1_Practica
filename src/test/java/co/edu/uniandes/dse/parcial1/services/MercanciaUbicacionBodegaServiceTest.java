package co.edu.uniandes.dse.parcial1.services;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import co.edu.uniandes.dse.parcial1.entities.MercanciaEntity;
import co.edu.uniandes.dse.parcial1.entities.UbicacionBodegaEntity;
import co.edu.uniandes.dse.parcial1.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import org.junit.jupiter.api.Test;
import java.util.List;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import({MercanciaService.class, UbicacionBodegaService.class})
public class MercanciaUbicacionBodegaServiceTest {

    @Autowired
    private MercanciaUbicacionBodegaService mercanciaUbicacionBodegaService;

    @Autowired
    private MercanciaService mercanciaService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<MercanciaEntity> mercanciaList = new ArrayList<>();
    private UbicacionBodegaEntity ubicacion;

    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from MercanciaEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from UbicacionBodegaEntity").executeUpdate();
    }

    private void insertData() {
        ubicacion = factory.manufacturePojo(UbicacionBodegaEntity.class);
        ubicacion.setNumeroEstante(10);
        entityManager.persist(ubicacion);


        for(int i =0; i< 3; i++ ){
            MercanciaEntity mercancia = factory.manufacturePojo(MercanciaEntity.class);

            mercancia.setFechaRecepcion(LocalDateTime.now().minusDays(3));
            entityManager.persist(mercancia);
            mercanciaList.add(mercancia);
        }
    }

    @Test
    void testAddMercancia() throws EntityNotFoundException, IllegalOperationException{
        MercanciaEntity mercanciaEntity = mercanciaUbicacionBodegaService.AddMercancia(mercanciaList.get(0).getId(), ubicacion.getId());

    }
    
}
