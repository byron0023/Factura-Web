package edu.ucacue.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "facturas_cabeceras")
public class FacturaCabecera  implements Serializable{

	private static final long serialVersionUID = 1607830177598686701L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Generated(GenerationTime.INSERT)
	@Column(columnDefinition = "serial ", unique = true)
	private int numeroFactura;
	
	private Date fechaEmision;
	private Double totalFactura;

	@ManyToOne
	@JoinColumn(name = "persona_fk")
	private Persona persona;

	@JsonManagedReference
	@OneToMany(mappedBy = "facturaCabecera", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<DetalleFactura> detallesFacturas;

	public FacturaCabecera() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getNumeroFactura() {
		return numeroFactura;
	}

	public void setNumeroFactura(int numeroFactura) {
		this.numeroFactura = numeroFactura;
	}

	public Date getFechaEmision() {
		return fechaEmision;
	}

	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public Double getTotalFactura() {
		return totalFactura;
	}

	public void setTotalFactura(Double totalFactura) {
		this.totalFactura = totalFactura;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public List<DetalleFactura> getDetallesFacturas() {
		return detallesFacturas;
	}

	public void setDetallesFacturas(List<DetalleFactura> detallesFacturas) {
		this.detallesFacturas = detallesFacturas;
		double total = 0;
		for (DetalleFactura detalleFactura : detallesFacturas) {
			total = total + detalleFactura.getValorVenta();
		}
		this.totalFactura = total;
	}
	
}
