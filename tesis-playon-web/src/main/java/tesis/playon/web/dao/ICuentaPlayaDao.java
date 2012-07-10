package tesis.playon.web.dao;

import tesis.playon.web.model.CuentaPlaya;

public interface ICuentaPlayaDao {

    void save(CuentaPlaya cuentaPlaya);

    void update(CuentaPlaya cuentaPlaya);

    void delete(CuentaPlaya cuentaPlaya);

    CuentaPlaya findByNroCuenta(String nroCuenta);

}