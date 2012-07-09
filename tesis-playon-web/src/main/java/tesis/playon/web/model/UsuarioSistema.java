package tesis.playon.web.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Clase de negocio que contiene los comentarios sobre una playa
 * 
 * @author gmorales
 * 
 */
@Entity
@Table(name = "usuario_sistema", catalog = "tesis_playon" , uniqueConstraints = { @UniqueConstraint(columnNames = "rolUsuarioID") })
public class UsuarioSistema implements Serializable {

    private static final long serialVersionUID = 2382561437443895633L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "usuarioID")
    private int id;

    @ManyToOne
    //@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rolUsuarioID")
    private RolUsuario rolUsuario;

    @ManyToOne
    //@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuarioSistemaID")    
    private Usuario usuario;
    
    public UsuarioSistema() {
    }
    
    public UsuarioSistema(RolUsuario rolUsuario, Usuario usuario) {
	this.rolUsuario = rolUsuario;
	this.usuario = usuario;
    }


    public RolUsuario getRolUsuario() {
        return rolUsuario;
    }


    public void setRolUsuario(RolUsuario rolUsuario) {
        this.rolUsuario = rolUsuario;
    }


    public Usuario getUsuario() {
        return usuario;
    }


    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }


    public int getId() {
        return id;
    }

    @Override
    public String toString() {
	return "UsuarioSistema:\t [usuarioSistemaID= " + id + ", " + rolUsuario + ", " + usuario + "]";
    }

}
