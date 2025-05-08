package com.dashboard.Entities;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Productos")
public class Product {
	
	//constructor
	public Product() {
		super();
	}
	public Product(String name, String category, float price, String tags, 
			Boolean active, Set<ProductCategory> categories, Long id) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.tags = tags;
		this.active = active;
		this.categories = categories;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="producto_ID", nullable = false)
	private Long id;
	
	@Column(name="nombre", nullable = false)
	private String name;
	
	@Column(name="precio", nullable = false)
	private float price;
	
	@Column(name="tags", columnDefinition = "TEXT")
	private String tags;
	
	@Column(name="fecha_de_creacion")
    private LocalDateTime create_date;
    
    @Column(name="ultima_actualizacion")
    private LocalDateTime last_update;
    
    @Column(name="activo", nullable = false)
    private Boolean active;
  //Join
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "Productos_Categorias",
        joinColumns = @JoinColumn(name = "producto_ID"),
        inverseJoinColumns = @JoinColumn(name = "categoria_ID")
    )    
    private Set<ProductCategory> categories = new HashSet<>();
    //Getters & Setters
    public float getId() {
		return id;
	}
	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
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

	public Set<ProductCategory> getCategories() {
		return categories;
	}

	public void setCategories(Set<ProductCategory> categories) {
		this.categories = categories;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	 

}
