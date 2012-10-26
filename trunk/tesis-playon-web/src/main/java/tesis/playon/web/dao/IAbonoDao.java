package tesis.playon.web.dao;

import java.util.List;

import tesis.playon.web.model.Abono;
import tesis.playon.web.model.Playa;

/**
 * Interfaz DAO de Abono.
 * 
 * @author alejandro
 * 
 */
public interface IAbonoDao {

    void save(Abono abono);

    void update(Abono abono);

    void delete(Abono abono);

    // no se que findBy... poner.

    List<Abono> findAll();

    List<Abono> findByPlaya(Playa playa);

}
