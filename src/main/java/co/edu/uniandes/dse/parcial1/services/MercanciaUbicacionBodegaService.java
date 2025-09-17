package co.edu.uniandes.dse.parcial1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import co.edu.uniandes.dse.parcial1.entities.MercanciaEntity;
import co.edu.uniandes.dse.parcial1.entities.UbicacionBodegaEntity;
import co.edu.uniandes.dse.parcial1.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcial1.repositories.MercanciaRepository;
import co.edu.uniandes.dse.parcial1.repositories.UbicacionBodegaRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MercanciaUbicacionBodegaService {

    @Autowired
    private MercanciaRepository mercanciaRepository;

    @Autowired
    private UbicacionBodegaRepository ubicacionBodegaRepository;

    @Transactional
    public MercanciaEntity AddMercancia(Long mercanciaId, Long ubicacionId) throws EntityNotFoundException{
        log.info("Inicia el proceso de agregar una mercancia a una ubicacion");
        Optional<MercanciaEntity> mercanciaEntity = mercanciaRepository.findById(mercanciaId);
        Optional<UbicacionBodegaEntity> ubicacionEntity = ubicacionBodegaRepository.findById(ubicacionId);

        if(mercanciaEntity.isEmpty()) throw new EntityNotFoundException("No se encontro la mercancia de id: "+ mercanciaId);
        if(ubicacionEntity.isEmpty()) throw new EntityNotFoundException("No se encontro la ubicacion en bodega de id: " + ubicacionId);

        ubicacionEntity.get().getUbicacion().add(mercanciaEntity.get());
        log.info("Finaliza el proceso de agregar una mercancia a una ubicacion");
        return mercanciaEntity.get();
    }

}
