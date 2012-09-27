package tesis.playon.restful.bean;

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

@XmlRootElement(name = "denuncia_playa")
@Entity
@Table(name = "denuncia_playa")
public class DenunciaPlaya implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "denunciaPlayaID", nullable = false)
    private Integer id;

    @Column(name = "asunto", unique = false, nullable = false)
    private String asunto;

    @Column(name = "fechaAlta", unique = true, nullable = false)
    private Date fechaAlta;

    @ManyToOne
    @JoinColumn(name = "clienteID", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "playaID", nullable = false)
    private Playa playa;

    public DenunciaPlaya() {
    }

    public DenunciaPlaya(String asunto, Date fechaAlta, Playa playa, Cliente cliente) {
	this.asunto = asunto;
	this.fechaAlta = fechaAlta;
	this.playa = playa;
	this.cliente = cliente;
    }

    public String getAsunto() {
	return asunto;
    }

    public void setAsunto(String asunto) {
	this.asunto = asunto;
    }

    public Date getFechaAlta() {
	return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
	this.fechaAlta = fechaAlta;
    }

    public Cliente getCliente() {
	return cliente;
    }

    public void setCliente(Cliente cliente) {
	this.cliente = cliente;
    }

    public Playa getPlaya() {
	return playa;
    }

    public void setPlaya(Playa playa) {
	this.playa = playa;
    }

    public boolean equals(Object object) {
	if (object == this)
	    return true;
	if (object == null || getClass() != object.getClass())
	    return false;

	DenunciaPlaya otroDenuncia = (DenunciaPlaya) object;
	if (id != otroDenuncia.id)
	    return false;

	return true;
    }

    @Override
    public String toString() {
	return "DenunciaPlaya:\t [denunciaPlayaID= " + id + ", asunto= " + asunto + ", fechaAlta= "
		+ fechaAlta.toString() + ", Cliente= " + cliente.getNroCliente() + ", " + playa + "]";
    }

}
