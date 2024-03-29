package tesis.playon.web.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;

import tesis.playon.web.dao.IPromocionDao;
import tesis.playon.web.model.Promocion;

/**
 * @author Pablo
 * 
 */
public class PromocionDao implements IPromocionDao {

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
	return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
	this.sessionFactory = sessionFactory;
    }

    public void save(Promocion promocion) {
	session.save(promocion);
    }

    public void update(Promocion promocion) {
	session.update(promocion);
    }

    public void delete(Promocion promocion) {
	session.delete(promocion);
    }

    public Promocion findByNombrePromocion(String nombrePromocion) {
	List<?> list = session.createQuery("from Promocion where nombre=?")
		.setParameter(0, nombrePromocion).list();
	return (Promocion) list.get(0);
    }

    public List<Promocion> findAll() {
	List<Promocion> promociones = new ArrayList<Promocion>();
	List<?> list = session.createQuery("from Promocion").list();
	for (Object object : list) {
	    promociones.add((Promocion) object);
	}
	return promociones;
    }
}
