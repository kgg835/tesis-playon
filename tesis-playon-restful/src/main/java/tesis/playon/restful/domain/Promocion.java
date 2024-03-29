package tesis.playon.restful.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(name = "promocion")
public class Promocion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "promocionID")
    private int id;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "descuento")
    private float descuento;

    @Column(name = "fechaAlta")
    private Date fechaAlta;

    @Column(name = "fechaFin")
    private Date fechaFin;

    @Column(name = "fechaInicio")
    private Date fechaInicio;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "montoFijo")
    private float montoFijo;

    @ManyToOne
    @JoinColumn(name = "playaID")
    private Playa playa;

    @ManyToOne
    @JoinColumn(name = "tarifaID")
    private Tarifa tarifa;

    @ManyToOne
    @JoinColumn(name = "estadoPromocionID")
    private EstadoPromocion estadoPromocion;

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public String getDescripcion() {
	return descripcion;
    }

    public void setDescripcion(String descripcion) {
	this.descripcion = descripcion;
    }

    public float getDescuento() {
	return descuento;
    }

    public void setDescuento(float descuento) {
	this.descuento = descuento;
    }

    public Date getFechaAlta() {
	return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
	this.fechaAlta = fechaAlta;
    }

    public Date getFechaFin() {
	return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
	this.fechaFin = fechaFin;
    }

    public Date getFechaInicio() {
	return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
	this.fechaInicio = fechaInicio;
    }

    public String getNombre() {
	return nombre;
    }

    public void setNombre(String nombre) {
	this.nombre = nombre;
    }

    public float getMontoFijo() {
	return montoFijo;
    }

    public void setMontoFijo(float montoFijo) {
	this.montoFijo = montoFijo;
    }

    public Playa getPlaya() {
	return playa;
    }

    public void setPlaya(Playa playa) {
	this.playa = playa;
    }

    public Tarifa getTarifa() {
	return tarifa;
    }

    public void setTarifa(Tarifa tarifa) {
	this.tarifa = tarifa;
    }

    public EstadoPromocion getEstadoPromocion() {
	return estadoPromocion;
    }

    public void setEstadoPromocion(EstadoPromocion estadoPromocion) {
	this.estadoPromocion = estadoPromocion;
    }

}