package co.edu.uniandes.dse.parcial1.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class UbicacionBodegaEntity extends BaseEntity{
   
    private Integer numeroEstante;
    private Integer canasta; 
    private Float pesoMax;

    @OneToMany(mappedBy = "ubicacionMercancia")
    private List<MercanciaEntity> ubicacion = new ArrayList<>(); 
    
}