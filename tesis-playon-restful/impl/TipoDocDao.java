package tesis.playon.web.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;

import tesis.playon.web.dao.ITipoDocDao;
import tesis.playon.web.model.TipoDoc;

/**
 * 
 * @author garribere
 * 
 */
public class TipoDocDao implements ITipoDocDao {

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
	return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
	this.sessionFactory = sessionFactory;
    }

    public void save(TipoDoc tipoDoc) {
	session.save(tipoDoc);
    }

    public void update(TipoDoc tipoDoc) {
	session.update(tipoDoc);
    }

    public void delete(TipoDoc tipoDoc) {
	session.delete(tipoDoc);
    }

    public TipoDoc findByNombreTipoDoc(String nombreTipoDoc) {
	List<?> list = session.createQuery("from TipoDoc where nombre=?")
		.setParameter(0, nombreTipoDoc).list();
	return (TipoDoc) list.get(0);
    }

    public List<TipoDoc> findAll() {
	List<TipoDoc> documentos = new ArrayList<TipoDoc>();
	List<?> list = session.createQuery("from TipoDoc order by nombre").list();
	for (Object object : list) {
	    documentos.add((TipoDoc) object);
	}
	return documentos;
    }
}
