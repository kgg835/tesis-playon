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
 * Clase de negocio que contiene los diferentes Estados de publicidad.
 * 
 * @author Pablo
 * @date 04/07/2012
 * 
 */
@Entity
@Table(name = "marca_vehiculo", catalog = "tesis_playon", uniqueConstraints = { @UniqueConstraint(columnNames = "nombre") })
public class MarcaVehiculo implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "marcaVehiculoID", nullable = false)
    private Integer id;

    @Column(name = "nombre", unique = true, nullable = false, length = 50)
    private String nombre;

    @Column(name = "descripcion", unique = false, nullable = true)
    private String descripcion;
    
    /**
     * Constructor con parámetros.
     * 
     * @param id
     * @param nombre
     * @param descripcion
     */
    public MarcaVehiculo(Integer id, String nombre, String descripcion) {
	this.id = id;
	this.nombre = nombre;
	this.descripcion = descripcion;
    }
    
    /**
     * Devuelve el ID del objeto.
     * 
     * @return El ID del objeto.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Devuelve el nombre del objeto.
     * 
     * @return El nombre del objeto.
     */
    public String getNombre() {
        return nombre;
    }
    
    /**
     * Setea un nuevo nombre al objeto.
     * 
     * @param nombre
     *            El nombre del objeto.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve la descripción del objeto.
     * 
     * @return
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Setea una nueva descripción al objeto.
     * 
     * @param descripcion
     *            La descripción del objeto.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    @Override
    public String toString() {
	return "MarcaVehiculo [marcaVehiculoID=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + "]";
    }

}
