package tesis.playon.web.managed.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.springframework.dao.DataAccessException;

import tesis.playon.web.model.Pais;
import tesis.playon.web.model.Provincia;
import tesis.playon.web.service.IProvinciaService;

@ManagedBean(name = "provinciaMB")
@RequestScoped
public class ProvinciaManagedBean implements Serializable {

    private static final long serialVersionUID = -1085389423375986168L;

    private static final String PROVINCIA_PAIS = "provincialist";

    private static final String ERROR = "error";

    @ManagedProperty(value = "#{ProvinciaService}")
    IProvinciaService provinciaService;

    List<Provincia> provinciaList;

    private String nombre;

    private Pais pais;

    public String addPais() {
	try {
	    Provincia provincia = new Provincia();
	    provincia.setNombre(getNombre());
	    provincia.setPais(getPais());
	    getProvinciaService().save(provincia);
	    return PROVINCIA_PAIS;
	} catch (DataAccessException e) {
	    e.printStackTrace();
	}
	return ERROR;
    }

    public void deleteProvincia(Provincia provincia) {
	getProvinciaService().delete(provincia);
    }

    public void updateProvincia(Provincia provincia) {
	getProvinciaService().update(provincia);
    }

    public void reset() {
	this.setNombre("");
    }

    public IProvinciaService getProvinciaService() {
	return provinciaService;
    }

    public void setProvinciaService(IProvinciaService provinciaService) {
	this.provinciaService = provinciaService;
    }

    public List<Provincia> getProvinciaList() {
	provinciaList = new ArrayList<Provincia>();
	provinciaList.addAll(getProvinciaService().findAll());
	return provinciaList;
    }

    public void setProvinciaList(List<Provincia> provinciaList) {
	this.provinciaList = provinciaList;
    }

    public String getNombre() {
	return nombre;
    }

    public void setNombre(String nombre) {
	this.nombre = nombre;
    }

    public Pais getPais() {
	return pais;
    }

    public void setPais(Pais pais) {
	this.pais = pais;
    }

}