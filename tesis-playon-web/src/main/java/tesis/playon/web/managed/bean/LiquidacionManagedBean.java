/**
 * 
 */
package tesis.playon.web.managed.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import tesis.playon.web.datamodel.PlayaDataModel;
import tesis.playon.web.model.Barrio;
import tesis.playon.web.model.CuentaPlaya;
import tesis.playon.web.model.Estadia;
import tesis.playon.web.model.EstadoPlaya;
import tesis.playon.web.model.Liquidacion;
import tesis.playon.web.model.Mail;
import tesis.playon.web.model.Playa;
import tesis.playon.web.model.TransaccionPlaya;
import tesis.playon.web.service.ICuentaPlayaService;
import tesis.playon.web.service.IEstadiaService;
import tesis.playon.web.service.IEstadoPlayaService;
import tesis.playon.web.service.ILiquidacionService;
import tesis.playon.web.service.IPlayaService;
import tesis.playon.web.service.ITransaccionPlayaService;
import tesis.playon.web.util.NotificadorUtil;

/**
 * @author Alejandro
 * 
 */
@ManagedBean(name = "liquidacionMB")
@ViewScoped
public class LiquidacionManagedBean implements Serializable {

    private static final long serialVersionUID = 3053005214323025897L;

    private static final String ERROR = "error";

    private NotificadorUtil notificador;

    private Mail mail;

    private String asunto;

    private String mensaje;

    @ManagedProperty(value = "#{PlayaService}")
    IPlayaService playaService;

    @ManagedProperty(value = "#{EstadoPlayaService}")
    IEstadoPlayaService estadoPlayaService;

    @ManagedProperty(value = "#{TransaccionPlayaService}")
    ITransaccionPlayaService transaccionPlayaService;

    @ManagedProperty(value = "#{CuentaPlayaService}")
    ICuentaPlayaService cuentaPlayaService;

    @ManagedProperty(value = "#{LiquidacionService}")
    ILiquidacionService liquidacionService;
    
    @ManagedProperty(value = "#{EstadiaService}")
    IEstadiaService estadiaService;

    Playa playaSeleccionada;

    Playa[] playasSeleccionadas;

    private PlayaDataModel playasModel;

    private String cuit;

    private Integer disponibilidad;

    private String direccionBusqueda;

    private String domicilio;

    private String nombreComercial;

    private String razonSocial;

    private Barrio barrio;

    private String telefono;

    private String emailPlaya;

    List<Playa> playasAprobadasList;

    List<TransaccionPlaya> transaccionesALiquidar;

    private List<Playa> filteredPlayas;

    private List<Playa> playasALiquidar;

    public IPlayaService getPlayaService() {
	return playaService;
    }

    public void setPlayaService(IPlayaService playaService) {
	this.playaService = playaService;
    }

    public IEstadoPlayaService getEstadoPlayaService() {
	return estadoPlayaService;
    }

    public void setEstadoPlayaService(IEstadoPlayaService estadoPlayaService) {
	this.estadoPlayaService = estadoPlayaService;
    }

    public ITransaccionPlayaService getTransaccionPlayaService() {
	return transaccionPlayaService;
    }

    public void setTransaccionPlayaService(ITransaccionPlayaService transaccionPlayaService) {
	this.transaccionPlayaService = transaccionPlayaService;
    }

    public ICuentaPlayaService getCuentaPlayaService() {
	return cuentaPlayaService;
    }

    public void setCuentaPlayaService(ICuentaPlayaService cuentaPlayaService) {
	this.cuentaPlayaService = cuentaPlayaService;
    }

    public ILiquidacionService getLiquidacionService() {
	return liquidacionService;
    }

    public void setLiquidacionService(ILiquidacionService liquidacionService) {
	this.liquidacionService = liquidacionService;
    }

    public IEstadiaService getEstadiaService() {
        return estadiaService;
    }

    public void setEstadiaService(IEstadiaService estadiaService) {
        this.estadiaService = estadiaService;
    }

    private int cantTransacciones(CuentaPlaya cuentaPlaya) {
	int contTransacciones = 0;
	for (TransaccionPlaya transaccion : getTransaccionesALiquidar()) {
	    if (transaccion.getCuentaPlaya().equals(cuentaPlaya)) {
		contTransacciones++;
	    }
	}
	return contTransacciones;
    }

    private int importeTotal(CuentaPlaya cuentaPlaya) {
	int importe = 0;
	for (TransaccionPlaya transaccion : getTransaccionesALiquidar()) {
	    if (transaccion.getCuentaPlaya().equals(cuentaPlaya)) {
		importe += transaccion.getImporte();
	    }
	}
	return importe;
    }

    public int cantTransacciones(int idPlaya) {
	Playa playa = this.getPlayaService().findById(idPlaya);
	CuentaPlaya cuentaPlaya = this.getCuentaPlayaService().findByPlaya(playa);
	return this.cantTransacciones(cuentaPlaya);
    }

    public int importeTotal(int idPlaya) {
	Playa playa = this.getPlayaService().findById(idPlaya);
	CuentaPlaya cuentaPlaya = this.getCuentaPlayaService().findByPlaya(playa);
	return this.importeTotal(cuentaPlaya);
    }

    public List<Playa> getPlayasAprobadasList() {
	playasAprobadasList = new ArrayList<Playa>();
	EstadoPlaya estado = new EstadoPlaya();
	estado = getEstadoPlayaService().findByNombreEstadoPlaya("Aprobada");
	playasAprobadasList = getPlayaService().findByEstado(estado);
	return playasAprobadasList;
    }

    public String liquidar() {
	for (Playa playa : this.getPlayasAprobadasList()) {
	    if (cantTransacciones(playa.getId()) <= 0)
		continue;
	    // Generamos la Liquidación con los datos
	    Liquidacion liquidacion = new Liquidacion();
	    Estadia estadiaPlaya = getEstadiaService().findByPlaya(playa);
	    liquidacion.setEstadia(estadiaPlaya);
	    liquidacion.setFecha(Calendar.getInstance().getTime());
	    liquidacion.setFechaDesde(null); //TODO Cambiar esto cuando filtre por fecha desde
	    liquidacion.setFechaHasta(null); //TODO Cambiar esto cuando filtre por fecha hasta
	    liquidacion.setImporteTotal(0);
	    getLiquidacionService().save(liquidacion);
	    
	    // Agregamos el ID de la liquidacion generada a 
	    // cada una de las transaciones liquidadas para esa playa.
	    for (TransaccionPlaya transaccion : this.getTransaccionesALiquidar()) {
		if (transaccion.getCuentaPlaya().getPlaya().getId() != playa.getId())
		    continue;
		transaccion.setLiquidacion(liquidacion);
		getTransaccionPlayaService().update(transaccion);
	    }
	    
	}
	return "/admin/liquidacionplayasend.html?faces-redirect=true";
    }

    public void setPlayasAprobadasList(List<Playa> playasAceptadasList) {
	this.playasAprobadasList = playasAceptadasList;
    }

    public List<Playa> getPlayasALiquidar() {
	return playasALiquidar;
    }

    public void setPlayasALiquidar(List<Playa> playasALiquidar) {
	this.playasALiquidar = playasALiquidar;
    }

    public List<Playa> getFilteredPlayas() {
	return filteredPlayas;
    }

    public void setFilteredPlayas(List<Playa> filteredPlayas) {
	this.filteredPlayas = filteredPlayas;
    }

    public Playa getPlayaSeleccionada() {
	return playaSeleccionada;
    }

    public void setPlayaSeleccionada(Playa playaSeleccionada) {
	this.playaSeleccionada = playaSeleccionada;
    }

    public Playa[] getPlayasSeleccionadas() {
	return playasSeleccionadas;
    }

    public void setPlayasSeleccionadas(Playa[] playasSeleccionadas) {
	this.playasSeleccionadas = playasSeleccionadas;
    }

    public String getCuit() {
	return cuit;
    }

    public void setCuit(String cuit) {
	this.cuit = cuit;
    }

    public Integer getDisponibilidad() {
	return disponibilidad;
    }

    public void setDisponibilidad(Integer disponibilidad) {
	this.disponibilidad = disponibilidad;
    }

    public String getDireccionBusqueda() {
	return direccionBusqueda;
    }

    public void setDireccionBusqueda(String direccionBusqueda) {
	this.direccionBusqueda = direccionBusqueda;
    }

    public String getDomicilio() {
	return domicilio;
    }

    public void setDomicilio(String domicilio) {
	this.domicilio = domicilio;
    }

    public String getNombreComercial() {
	return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
	this.nombreComercial = nombreComercial;
    }

    public String getRazonSocial() {
	return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
	this.razonSocial = razonSocial;
    }

    public Barrio getBarrio() {
	return barrio;
    }

    public void setBarrio(Barrio barrio) {
	this.barrio = barrio;
    }

    public String getTelefono() {
	return telefono;
    }

    public void setTelefono(String telefono) {
	this.telefono = telefono;
    }

    public String getEmailPlaya() {
	return emailPlaya;
    }

    public void setEmailPlaya(String emailPlaya) {
	this.emailPlaya = emailPlaya;
    }

    public List<TransaccionPlaya> getTransaccionesALiquidar() {
	if (transaccionesALiquidar == null) {
	    transaccionesALiquidar = new ArrayList<TransaccionPlaya>();
	    transaccionesALiquidar = getTransaccionPlayaService().findTransaccionesNoLiquidadas();
	}
	return transaccionesALiquidar;
    }

    public void setTransaccionesALiquidar(List<TransaccionPlaya> transaccionesALiquidar) {
	this.transaccionesALiquidar = transaccionesALiquidar;
    }

    public PlayaDataModel getPlayasModel() {
	playasModel = new PlayaDataModel(getPlayasAprobadasList());
	return playasModel;
    }
}