package com.dashboard.Entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Categorias")
public class ProductCategory {
	//constructor
	public ProductCategory() {
		super();
	}
	public ProductCategory(String name, Boolean active, Long id) {
		super();
		this.id = id;
		this.name = name;
		this.active = active;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="categoria_ID", nullable = false)
	private Long id;
	@Column(name="nombre", nullable = false)
	private String name;
	@Column(name="fecha_de_creacion")
    private LocalDateTime create_date;
    @Column(name="ultima_actualizacion")
    private LocalDateTime last_update;
	@Column(name="activo", nullable = false)
	private Boolean active;
	//Getters and Setters
	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDateTime getCreate_date() {
		return create_date;
	}
	public void setCreate_date(LocalDateTime create_date) {
		this.create_date = create_date;
	}
	public LocalDateTime getLast_update() {
		return last_update;
	}
	public void setLast_update(LocalDateTime last_update) {
		this.last_update = last_update;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
}
