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
@Table(name = "Usuarios")
public class User {
	//constructor
	public User() {
		super();
	}
	public User(String name, Boolean active, Long id) {
		super();
		this.id = id;
		this.name = name;
		this.active = active;
		//this.roles = roles;Set<Role> roles
	}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="usuario_ID")
    private Long id;
    
    @Column(name="nombre_completo")
    private String name;

    @Column(name="username", nullable = false, unique = true)
    private String username;

    @Column(name="clave", nullable = false)
    private String password;
    
    @Column(name="fecha_de_creacion")
    private LocalDateTime create_date;
    
    @Column(name="ultimo_acceso")
    private LocalDateTime last_access;
    
    @Column(name="activo", nullable = false)
    private Boolean active;
    //Join
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "usuario_roles",
        joinColumns = @JoinColumn(name = "usuario_ID"),
        inverseJoinColumns = @JoinColumn(name = "rol_ID")
    )    
    private Set<Role> roles = new HashSet<>();
    // Getters y setters
    
	public String getUsername() {
		return username;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDateTime getCreate_date() {
		return create_date;
	}

	public void setCreate_date(LocalDateTime localDateTime) {
		this.create_date = localDateTime;
	}

	public LocalDateTime getLast_access() {
		return last_access;
	}

	public void setLast_access(LocalDateTime last_access) {
		this.last_access = last_access;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> role) {
		this.roles = role;
	}
}
