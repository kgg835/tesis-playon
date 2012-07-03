package tesis.playon.web.business_object.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import tesis.playon.util.CustomHibernateDaoSupport;
import tesis.playon.web.business_object.dao.IProvinciaDao;
import tesis.playon.web.model.Provincia;

@Repository("provinciaDao")
public class ProvinciaDaoImpl extends CustomHibernateDaoSupport implements IProvinciaDao {

    public void save(Provincia provincia) {
	getHibernateTemplate().save(provincia);
    }

    public void update(Provincia cliente) {
	getHibernateTemplate().update(cliente);
    }

    public void delete(Provincia cliente) {
	getHibernateTemplate().delete(cliente);
    }

    public Provincia findByNombreProvincia(String nombreProvincia) {
	List<?> list = getHibernateTemplate().find("from Provincia where nombre=?", nombreProvincia);
	return (Provincia) list.get(0);
    }
}
