package tesis.playon.web.managed.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import tesis.playon.web.model.CargoEmpleado;
import tesis.playon.web.model.CategoriaVehiculo;
import tesis.playon.web.model.Cliente;
import tesis.playon.web.model.CuentaCliente;
import tesis.playon.web.model.CuentaPlaya;
import tesis.playon.web.model.DetalleEstadia;
import tesis.playon.web.model.Empleado;
import tesis.playon.web.model.Estadia;
import tesis.playon.web.model.MarcaVehiculo;
import tesis.playon.web.model.ModeloVehiculo;
import tesis.playon.web.model.Playa;
import tesis.playon.web.model.TransaccionCliente;
import tesis.playon.web.model.Usuario;
import tesis.playon.web.model.Vehiculo;
import tesis.playon.web.service.ICargoEmpleadoService;
import tesis.playon.web.service.ICuentaClienteService;
import tesis.playon.web.service.ICuentaPlayaService;
import tesis.playon.web.service.IEmpleadoService;
import tesis.playon.web.service.IPlayaService;
import tesis.playon.web.service.IUsuarioService;
import tesis.playon.web.service.IVehiculoService;

@ManagedBean(name = "ingresoEgresoMB")
@SessionScoped
public class IngresoEgresoManagedBean implements Serializable {

    private static final long serialVersionUID = 2624234897160641014L;

    @ManagedProperty(value = "#{EmpleadoService}")
    IEmpleadoService empleadoService;

    @ManagedProperty(value = "#{UsuarioService}")
    IUsuarioService usuarioService;

    @ManagedProperty(value = "#{CargoEmpleadoService}")
    ICargoEmpleadoService cargoEmpleadoService;

    @ManagedProperty(value = "#{CuentaClienteService}")
    ICuentaClienteService cuentaClienteService;

    @ManagedProperty(value = "#{PlayaService}")
    IPlayaService playaService;

    @ManagedProperty(value = "#{CuentaPlayaService}")
    ICuentaPlayaService cuentaPlayaService;

    @ManagedProperty(value = "#{VehiculoService}")
    IVehiculoService vehiculoService;

    // @ManagedProperty(value = "#{MarcaVehiculoService}")
    // IMarcaVehiculoService marcaVehiculoService;

    private Usuario usuario;

    private Usuario usuarioCliente;

    private Empleado empleado;

    private CargoEmpleado cargoEmpleado;

    private Playa playa;

    private CuentaPlaya cuentaPlaya;

    private Estadia estadia;

    private DetalleEstadia detalleEstadia;

    private Cliente cliente;

    private CuentaCliente cuentaCliente;

    private TransaccionCliente transaccionCliente;

    private Vehiculo vehiculo;

    private CategoriaVehiculo categoriaVehiculo;

    private ModeloVehiculo modeloVehiculo;

    private MarcaVehiculo marcaVehiculo;

    private String nombreUsuario;

    private String patente;

    private Boolean existeVehiculo = false;

    public void preRenderView() {
	FacesContext facesContext = FacesContext.getCurrentInstance();
	setNombreUsuario(facesContext.getExternalContext().getRemoteUser());
	if (null != nombreUsuario) {
	    setUsuario(getUsuarioService().findByNombreUsuario(nombreUsuario));
	}
	if (null != usuario) {
	    setEmpleado(getEmpleadoService().findByUsuario(usuario));
	    setPlaya(usuario.getPlaya());
	}
	if (null != playa) {
	    setCuentaPlaya(getCuentaPlayaService().findByPlaya(playa));
	    if (null != cuentaPlaya)
		System.out.println("\n\n\nCuenta de playa: " + cuentaPlaya.toString() + "\n\n\n");
	}
	if (null == cuentaPlaya) {
	    // la cuenta aun no existe y debe ser creada
	    cuentaPlaya = new CuentaPlaya(playa);
	    if (null != cuentaPlaya)
		getCuentaPlayaService().save(cuentaPlaya);
	}
	setCargoEmpleado(empleado.getCargoEmpleado());
    }

    public void searchVehiculo() {
	limpiar();
	setVehiculo(getVehiculoService().findByPatenteVehiculo(patente.toUpperCase()));
	if (null != vehiculo) {
	    setCategoriaVehiculo(vehiculo.getCategoriaVehiculo());
	    setModeloVehiculo(vehiculo.getModeloVehiculo());
	    setMarcaVehiculo(modeloVehiculo.getMarcaVehiculo());
	    setCliente(vehiculo.getCliente());
	}
	if (null != cliente) {
	    setUsuarioCliente(cliente.getUsuario());
	    setCuentaCliente(getCuentaClienteService()
		    .findByNroCuentaCliente(cliente.getCuentaCliente().getNroCuenta()));
	}
	if (cliente != null && null != vehiculo && null != cuentaCliente)
	    setExisteVehiculo(true);
    }

    public void registrarIngresoVehiculo() {
	long timeNow = Calendar.getInstance().getTimeInMillis();
	Timestamp ts = new Timestamp(timeNow);
	System.out.println("\n\n\nHora de entrada: " + ts + "\n\n\n");
    }

    public void registrarEgresoVehiculo() {

    }

    public void limpiar() {
	this.vehiculo = null;
	this.categoriaVehiculo = null;
	this.modeloVehiculo = null;
	this.marcaVehiculo = null;
	this.cliente = null;
	this.usuarioCliente = null;
	this.cuentaCliente = null;
	this.existeVehiculo = false;
    }

    public IEmpleadoService getEmpleadoService() {
	return empleadoService;
    }

    public void setEmpleadoService(IEmpleadoService empleadoService) {
	this.empleadoService = empleadoService;
    }

    public IUsuarioService getUsuarioService() {
	return usuarioService;
    }

    public void setUsuarioService(IUsuarioService usuarioService) {
	this.usuarioService = usuarioService;
    }

    public ICargoEmpleadoService getCargoEmpleadoService() {
	return cargoEmpleadoService;
    }

    public void setCargoEmpleadoService(ICargoEmpleadoService cargoEmpleadoService) {
	this.cargoEmpleadoService = cargoEmpleadoService;
    }

    public ICuentaClienteService getCuentaClienteService() {
	return cuentaClienteService;
    }

    public void setCuentaClienteService(ICuentaClienteService cuentaClienteService) {
	this.cuentaClienteService = cuentaClienteService;
    }

    public IPlayaService getPlayaService() {
	return playaService;
    }

    public void setPlayaService(IPlayaService playaService) {
	this.playaService = playaService;
    }

    public ICuentaPlayaService getCuentaPlayaService() {
	return cuentaPlayaService;
    }

    public void setCuentaPlayaService(ICuentaPlayaService cuentaPlayaService) {
	this.cuentaPlayaService = cuentaPlayaService;
    }

    public IVehiculoService getVehiculoService() {
	return vehiculoService;
    }

    public void setVehiculoService(IVehiculoService vehiculoService) {
	this.vehiculoService = vehiculoService;
    }

    // public IMarcaVehiculoService getMarcaVehiculoService() {
    // return marcaVehiculoService;
    // }
    //
    // public void setMarcaVehiculoService(IMarcaVehiculoService marcaVehiculoService) {
    // this.marcaVehiculoService = marcaVehiculoService;
    // }

    public Usuario getUsuario() {
	return usuario;
    }

    public void setUsuario(Usuario usuario) {
	this.usuario = usuario;
    }

    public Usuario getUsuarioCliente() {
	return usuarioCliente;
    }

    public void setUsuarioCliente(Usuario usuarioCliente) {
	this.usuarioCliente = usuarioCliente;
    }

    public Empleado getEmpleado() {
	return empleado;
    }

    public void setEmpleado(Empleado empleado) {
	this.empleado = empleado;
    }

    public CargoEmpleado getCargoEmpleado() {
	return cargoEmpleado;
    }

    public void setCargoEmpleado(CargoEmpleado cargoEmpleado) {
	this.cargoEmpleado = cargoEmpleado;
    }

    public Playa getPlaya() {
	return playa;
    }

    public void setPlaya(Playa playa) {
	this.playa = playa;
    }

    public CuentaPlaya getCuentaPlaya() {
	return cuentaPlaya;
    }

    public void setCuentaPlaya(CuentaPlaya cuentaPlaya) {
	this.cuentaPlaya = cuentaPlaya;
    }

    public Estadia getEstadia() {
	return estadia;
    }

    public void setEstadia(Estadia estadia) {
	this.estadia = estadia;
    }

    public DetalleEstadia getDetalleEstadia() {
	return detalleEstadia;
    }

    public void setDetalleEstadia(DetalleEstadia detalleEstadia) {
	this.detalleEstadia = detalleEstadia;
    }

    public Cliente getCliente() {
	return cliente;
    }

    public void setCliente(Cliente cliente) {
	this.cliente = cliente;
    }

    public CuentaCliente getCuentaCliente() {
	return cuentaCliente;
    }

    public void setCuentaCliente(CuentaCliente cuentaCliente) {
	this.cuentaCliente = cuentaCliente;
    }

    public TransaccionCliente getTransaccionCliente() {
	return transaccionCliente;
    }

    public void setTransaccionCliente(TransaccionCliente transaccionCliente) {
	this.transaccionCliente = transaccionCliente;
    }

    public Vehiculo getVehiculo() {
	return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
	this.vehiculo = vehiculo;
    }

    public CategoriaVehiculo getCategoriaVehiculo() {
	return categoriaVehiculo;
    }

    public void setCategoriaVehiculo(CategoriaVehiculo categoriaVehiculo) {
	this.categoriaVehiculo = categoriaVehiculo;
    }

    public ModeloVehiculo getModeloVehiculo() {
	return modeloVehiculo;
    }

    public void setModeloVehiculo(ModeloVehiculo modeloVehiculo) {
	this.modeloVehiculo = modeloVehiculo;
    }

    public MarcaVehiculo getMarcaVehiculo() {
	return marcaVehiculo;
    }

    public void setMarcaVehiculo(MarcaVehiculo marcaVehiculo) {
	this.marcaVehiculo = marcaVehiculo;
    }

    public String getNombreUsuario() {
	return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
	this.nombreUsuario = nombreUsuario;
    }

    public String getPatente() {
	return patente;
    }

    public void setPatente(String patente) {
	this.patente = patente;
    }

    public Boolean getExisteVehiculo() {
	return existeVehiculo;
    }

    public void setExisteVehiculo(Boolean existeVehiculo) {
	this.existeVehiculo = existeVehiculo;
    }

}