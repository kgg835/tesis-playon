package tesis.playon.web.managed.bean;

import java.io.Serializable;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import tesis.playon.web.model.EstadoPromocion;
import tesis.playon.web.model.Playa;
import tesis.playon.web.model.Promocion;
import tesis.playon.web.model.Tarifa;
import tesis.playon.web.model.Usuario;
import tesis.playon.web.service.IEstadoPromocionService;
import tesis.playon.web.service.IPlayaService;
import tesis.playon.web.service.IPromocionService;
import tesis.playon.web.service.ITarifaService;
import tesis.playon.web.service.IUsuarioService;

/**
 * 
 * @author pablo
 * 
 */
@ManagedBean(name = "promocionMB")
public class PromocionManagedBean implements Serializable {

    private static final long serialVersionUID = 6826797453747733748L;

    @ManagedProperty(value = "#{EstadoPromocionService}")
    IEstadoPromocionService estadoPromocionService;

    @ManagedProperty(value = "#{PromocionService}")
    IPromocionService promocionService;

    @ManagedProperty(value = "#{PlayaService}")
    IPlayaService playaService;

    @ManagedProperty(value = "#{TarifaService}")
    ITarifaService tarifaService;

    @ManagedProperty(value = "#{UsuarioService}")
    IUsuarioService usuarioService;

    // Atributtes Promocion

    private String descripcion;

    private Float descuento;

    private Date fechaAlta;

    private Date fechaInicio;

    private Date fechaFin;

    private Date horaInicio;

    private Date horaFin;

    private static Date horaInicioSelected;

    private static Date horaFinSelected;

    private Float montoFijo;

    private Float montoConDescuento;

    private String nombre;

    private static Tarifa tarifa;

    private Playa playa;

    private EstadoPromocion estadoPromocion;

    private Date today;

    private List<Promocion> promocionPlayaList;

    private static Promocion promocionSelected;
    
    private List<Promocion> promocionPlayaListSelected;
    
    private static Playa playaSelected;

    @PostConstruct
    private void init() {
	FacesContext facesContext = FacesContext.getCurrentInstance();
	String userName = facesContext.getExternalContext().getRemoteUser();
	Usuario user = getUsuarioService().findByNombreUsuario(userName);
	if (user != null && user.getPlaya() != null) {
	    this.playa = user.getPlaya();
	    promocionPlayaList = getPromocionService().findByPlaya(playa);
	}
	today = new Date();
    }

    public String addPromocion() {
	Promocion promocion = null;

	try {
	    promocion = new Promocion();
	    promocion.setDescripcion(getDescripcion());
	    promocion.setDescuento(getDescuento());
	    promocion.setEstadoPromocion(getEstadoPromocion());
	    promocion.setFechaAlta(new Date());
	    promocion.setFechaFin(getFechaFin());
	    promocion.setFechaInicio(getFechaInicio());

	    Calendar calendario = Calendar.getInstance();
	    calendario.setTime(getHoraInicio());
	    int hora = calendario.get(Calendar.HOUR_OF_DAY);
	    int minutos = calendario.get(Calendar.MINUTE);
	    int segundos = calendario.get(Calendar.SECOND);

	    String sHora = hora + ":" + minutos + ":" + segundos;

	    promocion.setHoraInicio(Time.valueOf(sHora));

	    calendario.setTime(getHoraFin());
	    hora = calendario.get(Calendar.HOUR_OF_DAY);
	    minutos = calendario.get(Calendar.MINUTE);
	    segundos = calendario.get(Calendar.SECOND);
	    sHora = hora + ":" + minutos + ":" + segundos;

	    promocion.setHoraFin(Time.valueOf(sHora));
	    promocion.setMontoFijo(getTarifa().getImporte());
	    promocion.setNombre(getNombre());
	    promocion.setPlaya(getPlaya());
	    promocion.setTarifa(getTarifa());

	    getPromocionService().save(promocion);

	    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
		    "Se registró exitosamente la promoción: " + promocion.getNombre(), null);
	    FacesContext.getCurrentInstance().addMessage(null, message);

	    return "promocionaddend";
	} catch (Exception e) {
	    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
		    "Error, no se pudo registrar la promoción, Disculpe las molestias ocacionadas.", null);
	    FacesContext.getCurrentInstance().addMessage(null, message);
	    e.printStackTrace();
	}
	return null;
    }

    public String updatePromocion() {
	try {
	    Promocion promo = new Promocion();
	    promo = promocionSelected;

	    Calendar calendario = Calendar.getInstance();
	    calendario.setTime(horaInicioSelected);
	    int hora = calendario.get(Calendar.HOUR_OF_DAY);
	    int minutos = calendario.get(Calendar.MINUTE);
	    int segundos = calendario.get(Calendar.SECOND);

	    String sHora = hora + ":" + minutos + ":" + segundos;

	    promo.setHoraInicio(Time.valueOf(sHora));

	    calendario.setTime(horaFinSelected);
	    hora = calendario.get(Calendar.HOUR_OF_DAY);
	    minutos = calendario.get(Calendar.MINUTE);
	    segundos = calendario.get(Calendar.SECOND);
	    sHora = hora + ":" + minutos + ":" + segundos;

	    promo.setHoraFin(Time.valueOf(sHora));

	    getPromocionService().update(promo);

	    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
		    "Se modificó exitosamente la promoción: " + promocionSelected.getNombre(), null);
	    FacesContext.getCurrentInstance().addMessage(null, message);

	    return "promocioneslist";
	} catch (Exception e) {
	    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
		    "Error, no se pudo modificar la promoción, Disculpe las molestias ocacionadas.", null);
	    FacesContext.getCurrentInstance().addMessage(null, message);
	    e.printStackTrace();
	}
	return "error";
    }

    public void deletePromocion() {
	try {
	    if (promocionSelected != null) {
		EstadoPromocion estado = getEstadoPromocionService().findByNombreEstadoPromocion("De baja");
		promocionSelected.setEstadoPromocion(estado);

		getPromocionService().update(promocionSelected);

		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
			"Se dió de baja la promoción correctamente.", null);
		FacesContext.getCurrentInstance().addMessage(null, message);

	    }
	} catch (Exception ex) {
	    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
		    "Error, no se pudo dar de baja la promoción, Disculpe las molestias ocacionadas.", null);
	    FacesContext.getCurrentInstance().addMessage(null, message);
	    ex.printStackTrace();
	}
    }

    public void calcularDescuento() {
	this.montoConDescuento = tarifa.getImporte() * (1 - (descuento / 100));
    }

    public void seleccionarTarifa(Tarifa tarifaParameter) {
	if (tarifaParameter != null) {
	    tarifa = tarifaParameter;
	    this.montoFijo = tarifaParameter.getImporte();
	}
    }

    public void validateDateInicial(FacesContext context, UIComponent component, Object value) {
	Date dateInicial = (Date) value;
	if (dateInicial != null) {
	    this.setFechaInicio(dateInicial);
	}
    }

    public void validateDate(FacesContext context, UIComponent component, Object value) {
	Date dateFin = (Date) value;
	if (getFechaInicio() != null) {
	    if (dateFin.before(getFechaInicio())) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
			"La fecha de finalización debe ser mayor a la fecha de inicio.", null);
		throw new ValidatorException(message);
	    }
	}
    }
    
    public void findPlayaById() {
	FacesContext facesContext = FacesContext.getCurrentInstance();
	int idPlayaSelected = Integer.parseInt(facesContext.getExternalContext().getRequestParameterMap().get("id"));
	playaSelected = getPlayaService().findById(idPlayaSelected);
    }

    public IUsuarioService getUsuarioService() {
	return usuarioService;
    }

    public void setUsuarioService(IUsuarioService usuarioService) {
	this.usuarioService = usuarioService;
    }

    public IPlayaService getPlayaService() {
	return playaService;
    }

    public void setPlayaService(IPlayaService playaService) {
	this.playaService = playaService;
    }

    public ITarifaService getTarifaService() {
	return tarifaService;
    }

    public void setTarifaService(ITarifaService tarifaService) {
	this.tarifaService = tarifaService;
    }

    public IEstadoPromocionService getEstadoPromocionService() {
	return estadoPromocionService;
    }

    public void setEstadoPromocionService(IEstadoPromocionService estadoPromocionService) {
	this.estadoPromocionService = estadoPromocionService;
    }

    public IPromocionService getPromocionService() {
	return promocionService;
    }

    public void setPromocionService(IPromocionService promocionService) {
	this.promocionService = promocionService;
    }

    // ============================ GETTER && SETTER =======================================

    public String getDescripcion() {
	return descripcion;
    }

    public void setDescripcion(String descripcion) {
	this.descripcion = descripcion;
    }

    public Float getDescuento() {
	return descuento;
    }

    public void setDescuento(Float descuento) {
	this.descuento = descuento;
    }

    public Date getFechaAlta() {
	return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
	this.fechaAlta = fechaAlta;
    }

    public Date getFechaInicio() {
	return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
	this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
	return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
	this.fechaFin = fechaFin;
    }

    public Float getMontoFijo() {
	return montoFijo;
    }

    public void setMontoFijo(Float montoFijo) {
	this.montoFijo = montoFijo;
    }

    public String getNombre() {
	return nombre;
    }

    public void setNombre(String nombre) {
	this.nombre = nombre;
    }

    public Tarifa getTarifa() {
	return tarifa;
    }

    public void setTarifa(Tarifa tarifa) {
	PromocionManagedBean.tarifa = tarifa;
    }

    public Playa getPlaya() {
	return playa;
    }

    public void setPlaya(Playa playa) {
	this.playa = playa;
    }

    public EstadoPromocion getEstadoPromocion() {
	return estadoPromocion;
    }

    public void setEstadoPromocion(EstadoPromocion estadoPromocion) {
	this.estadoPromocion = estadoPromocion;
    }

    public Float getMontoConDescuento() {
	if (promocionSelected != null) {
	    this.montoConDescuento = promocionSelected.getTarifa().getImporte()
		    * (1 - (promocionSelected.getDescuento() / 100));
	}
	return montoConDescuento;
    }

    public void setMontoConDescuento(Float montoConDescuento) {
	this.montoConDescuento = montoConDescuento;
    }

    public Date getToday() {
	return today;
    }

    public void setToday(Date today) {
	this.today = today;
    }

    public List<Promocion> getPromocionPlayaList() {
	return promocionPlayaList;
    }

    public void setPromocionPlayaList(List<Promocion> promocionPlayaList) {
	this.promocionPlayaList = promocionPlayaList;
    }

    public List<Promocion> getPromocionPlayaListSelected() {
	if(playaSelected != null){
	    estadoPromocion = getEstadoPromocionService().findByNombreEstadoPromocion("Vigente");
	    promocionPlayaListSelected = getPromocionService().findByPlaya(playaSelected,estadoPromocion);
	}
        return promocionPlayaListSelected;
    }

    public void setPromocionPlayaListSelected(List<Promocion> promocionPlayaListSelected) {
        this.promocionPlayaListSelected = promocionPlayaListSelected;
    }

    public Promocion getPromocionSelected() {
	if(promocionSelected != null){
	    Calendar calendario = Calendar.getInstance();
	    int hora = getHora(promocionSelected.getHoraInicio());
	    int minute = getMinutos(promocionSelected.getHoraInicio());
	    int second = getSegundos(promocionSelected.getHoraInicio());
	    calendario.set(2012, 01, 01, hora, minute, second);
	    horaInicioSelected = calendario.getTime();
	    
	    hora = getHora(promocionSelected.getHoraFin());
	    minute = getMinutos(promocionSelected.getHoraFin());
	    second = getSegundos(promocionSelected.getHoraFin());
	    calendario.set(2012, 01, 01, hora, minute, second);
	    horaFinSelected = calendario.getTime();
	}
	return promocionSelected;
    }

    public void setPromocionSelected(Promocion promocionSelected) {
	PromocionManagedBean.promocionSelected = promocionSelected;
    }

    public Playa getPlayaSelected() {
        return playaSelected;
    }

    public void setPlayaSelected(Playa playaSelected) {
        PromocionManagedBean.playaSelected = playaSelected;
    }

    public Date getHoraInicio() {
	return horaInicio;
    }

    public void setHoraInicio(Date horaInicio) {
	this.horaInicio = horaInicio;
    }

    public Date getHoraFin() {
	return horaFin;
    }

    public void setHoraFin(Date horaFin) {
	this.horaFin = horaFin;
    }

    public Date getHoraInicioSelected() {
	return horaInicioSelected;
    }

    public void setHoraInicioSelected(Date horaInicioSelected) {
	PromocionManagedBean.horaInicioSelected = horaInicioSelected;
    }

    public Date getHoraFinSelected() {
	return horaFinSelected;
    }

    public void setHoraFinSelected(Date horaFinSelected) {
	PromocionManagedBean.horaFinSelected = horaFinSelected;
    }
    
    private int getHora(Time time){
	String horaCompleta = time.toString();
	String toObject[] = horaCompleta.split(":");
	return Integer.parseInt(toObject[0]);
    }
    
    private int getMinutos(Time time){
	String horaCompleta = time.toString();
	String toObject[] = horaCompleta.split(":");
	return Integer.parseInt(toObject[1]);
    }
    
    private int getSegundos(Time time){
	String horaCompleta = time.toString();
	String toObject[] = horaCompleta.split(":");
	return Integer.parseInt(toObject[2]);
    }
}