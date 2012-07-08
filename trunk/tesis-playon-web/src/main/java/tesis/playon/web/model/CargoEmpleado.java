/**
 * 
 */
package tesis.playon.web.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author Pablo
 *
 */
@Entity
@Table(name = "cargo_empleado", catalog = "tesis_playon", uniqueConstraints = { @UniqueConstraint(columnNames = "nombre")})
public class CargoEmpleado implements Serializable{

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "cargoEmpleadoID")
    private Integer id;
    
    @Column(name = "nombre")
    private String nombre;
    
    @Column(name = "descripcion")
    private String descripcion;

    public CargoEmpleado(){}
    
    public CargoEmpleado(String nombre, String descripcion) {
	this.nombre = nombre;
	this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
	return "CargoEmpleado:\t [cargoEmpleadoID= " + id + ", nombre= " + nombre + ", descripcion= " + descripcion + "]";
    }
}
