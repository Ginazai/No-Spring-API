package com.dashboard.Entities;

import javax.persistence.*;

@Entity
@Table(name = "Roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="rol_ID")
    private Long id;

    @Column(name="rol", unique = true, nullable = false)
    private String name;

    // Getters y setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
