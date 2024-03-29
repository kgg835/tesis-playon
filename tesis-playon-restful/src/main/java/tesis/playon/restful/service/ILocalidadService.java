package tesis.playon.restful.service;

import java.util.List;
import java.util.Set;

import tesis.playon.restful.domain.Barrio;
import tesis.playon.restful.domain.Localidad;

public interface ILocalidadService {

    void save(Localidad localidad);

    void update(Localidad localidad);

    void delete(Localidad localidad);

    Localidad findByNombreLocalidad(String nombreLocalidad);

    List<Localidad> findAll();
    
    Set<Barrio> findBarrio(Localidad localidad);
}
