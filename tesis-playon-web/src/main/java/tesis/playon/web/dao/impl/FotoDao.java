package tesis.playon.web.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;

import tesis.playon.web.dao.IFotoDao;
import tesis.playon.web.model.Foto;

/**
 * @author Pablo
 * 
 */
public class FotoDao implements IFotoDao {

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
	return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
	this.sessionFactory = sessionFactory;
    }

    public void save(Foto foto) {
	getSessionFactory().getCurrentSession().save(foto);
    }

    public void update(Foto foto) {
	getSessionFactory().getCurrentSession().update(foto);
    }

    public void delete(Foto foto) {
	getSessionFactory().getCurrentSession().delete(foto);
    }

    public Foto findByLinkFoto(String link) {
	List<?> list = getSessionFactory().getCurrentSession().createQuery("from Foto where link=?")
		.setParameter(0, link).list();
	return (Foto) list.get(0);
    }

    public List<Foto> findAll() {
	List<Foto> fotos = new ArrayList<Foto>();
	List<?> list = getSessionFactory().getCurrentSession().createQuery("from Foto").list();
	for (Object object : list) {
	    fotos.add((Foto) object);
	}
	return fotos;
    }
}
