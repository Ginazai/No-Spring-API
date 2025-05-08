package com.dashboard.Services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.dashboard.Entities.Role;
@Stateless
public class RoleService {
	@PersistenceContext(unitName = "myUnit")
    private EntityManager entityManager;
    
    public Role guardar(Role role) {
    	entityManager.persist(role);
    	return role;
    }

    public Role buscarPorId(Long id) {
        return entityManager.find(Role.class, id);
    }

    public Role actualizarRol(Role role) {
    	return entityManager.merge(role);
    }
    
    public Role buscarPorNombre(String name) {
        List<Role> result = entityManager.createQuery("SELECT r FROM Role r WHERE r.name = :name", Role.class)
            .setParameter("name", name)
            .getResultList();

        return result.isEmpty() ? null : result.get(0);
    }
}
