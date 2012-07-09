package tesis.playon.web.business_object.dao;

import java.util.List;

import tesis.playon.web.model.Tarifa;
import tesis.playon.web.model.Playa;

public interface ITarifaDao {

    void save(Tarifa tarifa);

    void update(Tarifa tarifa);

    void delete(Tarifa tarifa);

    Tarifa findByPlayaID(Playa playa);

    List<Tarifa> findAll();
}
