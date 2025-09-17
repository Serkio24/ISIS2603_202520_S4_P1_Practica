package co.edu.uniandes.dse.parcial1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcial1.entities.UbicacionBodegaEntity;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcial1.repositories.UbicacionBodegaRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UbicacionBodegaService {

    @Autowired
    private UbicacionBodegaRepository ubicacionBodegaRepository;

    @Transactional
    public UbicacionBodegaEntity createUbicacionBodega(UbicacionBodegaEntity ubicacionBodegaEntity) throws IllegalOperationException{
        log.info("Inicia el proceso para crear una nueva ubicacion en bodega");
        if(ubicacionBodegaEntity.getNumeroEstante() <= 0) throw new IllegalOperationException("Numero de estante no valido");
        return ubicacionBodegaRepository.save(ubicacionBodegaEntity); 
    }

}
