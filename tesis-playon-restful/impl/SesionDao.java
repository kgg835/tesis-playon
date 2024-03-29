package tesis.playon.web.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;

import tesis.playon.web.dao.ISesionDao;
import tesis.playon.web.model.Sesion;

public class SesionDao implements ISesionDao {

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
	return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
	this.sessionFactory = sessionFactory;
    }

    public void save(Sesion sesion) {
	session.save(sesion);
    }

    public void update(Sesion sesion) {
	session.update(sesion);
    }

    public void delete(Sesion sesion) {
	session.delete(sesion);
    }

    public Sesion findByIDSesionSesion(String idSesion) {
	List<?> list = session.createQuery("from Sesion where sesionId=?")
		.setParameter(0, idSesion).list();
	return (Sesion) list.get(0);
    }

    public List<Sesion> findAll() {
	List<Sesion> sesiones = new ArrayList<Sesion>();
	List<?> list = session.createQuery("from Sesion").list();
	for (Object object : list) {
	    sesiones.add((Sesion) object);
	}
	return sesiones;
    }

}
