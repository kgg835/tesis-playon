package tesis.playon.web.dao;

import java.util.List;

import tesis.playon.web.model.Empleado;

/**
 * @author garribere
 * 
 */
public interface IEmpleadoDao {

    void save(Empleado empleado);

    void update(Empleado empleado);

    void delete(Empleado empleado);

    List<Empleado> findAll();

    List<Empleado> findAll(Integer idCargoEmpleado);

    Empleado findByLegajo(Integer legajo);

}