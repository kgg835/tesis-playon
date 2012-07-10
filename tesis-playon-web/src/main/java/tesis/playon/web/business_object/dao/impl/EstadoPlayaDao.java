package tesis.playon.web.business_object.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import tesis.playon.util.CustomHibernateDaoSupport;
import tesis.playon.web.business_object.dao.IEstadoPlayaDao;
import tesis.playon.web.model.EstadoPlaya;

@Repository("estadoPlayaDao")
public class EstadoPlayaDao extends CustomHibernateDaoSupport implements IEstadoPlayaDao {

    public void save(EstadoPlaya estadoPlaya) {
	getHibernateTemplate().save(estadoPlaya);
    }

    public void update(EstadoPlaya estadoPlaya) {
	getHibernateTemplate().update(estadoPlaya);
    }

    public void delete(EstadoPlaya estadoPlaya) {
	getHibernateTemplate().delete(estadoPlaya);
    }

    public EstadoPlaya findByNombreEstadoPlaya(String nombreEstado) {
	List<?> list = getHibernateTemplate().find("from EstadoPlaya where nombre=?", nombreEstado);
	return (EstadoPlaya) list.get(0);
    }

    public List<EstadoPlaya> findAll() {
	List<EstadoPlaya> estadoPlaya = new ArrayList<EstadoPlaya>();
	List<?> list = getHibernateTemplate().find("from EstadoPlaya");
	for (Object object : list) {
	    estadoPlaya.add((EstadoPlaya) object);
	}
	return estadoPlaya;
    }

}
