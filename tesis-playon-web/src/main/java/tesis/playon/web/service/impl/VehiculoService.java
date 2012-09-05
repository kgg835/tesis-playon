package tesis.playon.web.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import tesis.playon.web.dao.IVehiculoDao;
import tesis.playon.web.model.Vehiculo;
import tesis.playon.web.service.IVehiculoService;
/**
 * 
 * @author pablo
 *
 */
@Transactional(readOnly = true)
public class VehiculoService implements IVehiculoService {

    IVehiculoDao vehiculoDao;
    
    @Transactional(readOnly = false)
    @Override
    public void save(Vehiculo vehiculo) {
	getVehiculoDao().save(vehiculo);
    }

    @Transactional(readOnly = false)
    @Override
    public void update(Vehiculo vehiculo) {
	getVehiculoDao().update(vehiculo);
    }

    @Transactional(readOnly = false)
    @Override
    public void delete(Vehiculo vehiculo) {
	getVehiculoDao().delete(vehiculo);
    }

    @Override
    public Vehiculo findByPatenteVehiculo(String patenteVehiculo) {
	return getVehiculoDao().findByPatenteVehiculo(patenteVehiculo);
    }

    @Override
    public List<Vehiculo> findAll() {
	return getVehiculoDao().findAll();
    }

    public IVehiculoDao getVehiculoDao() {
        return vehiculoDao;
    }

    public void setVehiculoDao(IVehiculoDao vehiculoDao) {
        this.vehiculoDao = vehiculoDao;
    }

}
