package co.edu.uniandes.dse.parcial1.services;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcial1.entities.MercanciaEntity;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcial1.repositories.MercanciaRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MercanciaService {
    
    @Autowired
    private MercanciaRepository mercanciaRepository;

    @Transactional
    public MercanciaEntity createMercancia(MercanciaEntity mercanciaEntity) throws IllegalOperationException{
        log.info("Inicia el proceso de crear una mercancia");
        if(mercanciaEntity.getCodigoBarras() == null) throw new IllegalOperationException("Codigo de barras no valido");
        if(mercanciaEntity.getNombre() == null || mercanciaEntity.getNombre().isBlank()) throw new IllegalOperationException("El nombre no puede estar vacio");
        if(Duration.between(mercanciaEntity.getFechaRecepcion() ,LocalDateTime.now()).isNegative()) throw new IllegalOperationException("La fecha de la recepcion no es valida");
        log.info("Finaliza el proceso de creacion de una mercancia");
        return mercanciaRepository.save(mercanciaEntity);
        }
    }
