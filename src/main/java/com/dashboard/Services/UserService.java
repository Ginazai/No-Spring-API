package com.dashboard.Services;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import com.dashboard.Entities.Role;
import com.dashboard.Entities.User;
import com.dashboard.util.PasswordUtil;

//import com.dashboard.Exceptions.UserNotFoundException;
import javax.ejb.Stateless;
import javax.inject.Inject;
@Stateless
public class UserService {
    @PersistenceContext(unitName = "myUnit")
    private EntityManager entityManager;
    @Inject
    private RoleService roleService;
    @Inject
    private PasswordUtil passwordUtil;
    
    
	public User guardar(User user) {
		user.setCreate_date(LocalDateTime.now());
		user.setLast_access(LocalDateTime.now());
		user.setPassword(PasswordUtil.hashPassword(user.getPassword()));
		user.setActive(true);
		entityManager.persist(user);
    	return user;
	}
    public User buscarPorUsername(String username) {
    	List<User> result = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
            .setParameter("username", username)
            .getResultList();

        return result.isEmpty() ? null : result.get(0);
    }
	public User buscarPorId(Long id) {
		return entityManager.find(User.class, id);
	}
	public List<User> listarUsuarios() {
	    try {
	        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
	    } catch (Exception e) {
	        e.printStackTrace(); // Log the exception
	        return Collections.emptyList(); // Return an empty list if an error occurs
	    }
	}
	@Transactional
	public User actualizarUsuario(Long id, User updatedUser) {
		User existingUser = entityManager.find(User.class, id);
		if (existingUser == null) {
			 throw new IllegalArgumentException("Product not found with ID: " + id);
		}
		existingUser.setName(updatedUser.getName() != null ? updatedUser.getName() 
				: existingUser.getName());
		existingUser.setUsername(updatedUser.getUsername() != null ? updatedUser.getUsername() 
				: existingUser.getUsername());
		existingUser.setPassword(updatedUser.getPassword() != null ? PasswordUtil.hashPassword(updatedUser.getPassword()) 
				: existingUser.getPassword());
		existingUser.setActive(updatedUser.getActive() != null ? updatedUser.getActive()
				: existingUser.getActive());
		existingUser.setCreate_date(updatedUser.getCreate_date() != null ? updatedUser.getCreate_date()
				: existingUser.getCreate_date());
		existingUser.setLast_access(updatedUser.getLast_access() != null ? updatedUser.getLast_access()
				: existingUser.getLast_access());
		return entityManager.merge(existingUser);
	}

	
	public User borrarUsuario(Long id) {
		User u = entityManager.find(User.class, id);
        if (u == null) {
        	u = entityManager.merge(u);
        }
        u.setActive(false);
        return u;
	}
	
	public User authenticate(User credentials) {
        try {
            User user = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username AND u.password = :password", User.class)
                .setParameter("username", credentials.getUsername())
                .setParameter("password", credentials.getPassword())
                .getSingleResult();
            user.setLast_access(LocalDateTime.now());
            return user;
        } catch (NoResultException e) {
            return null;
        }
    }
	@Transactional
	public User actualizarRoles(Long id, Set<String> newRoles) {
		User user = entityManager.find(User.class, id);
		if (newRoles == null || newRoles.isEmpty()) {
	        throw new IllegalArgumentException("Debe proporcionar al menos un rol.");
	    }		
		Set<Role> roles = newRoles.stream()
	            .map(role -> roleService.buscarPorNombre(role))
	            .filter(Objects::nonNull)
	            .collect(Collectors.toSet());
		if (roles.isEmpty()) {
	        throw new IllegalArgumentException("No se encontraron categorías válidas.");
	    }
		user.getRoles().clear();
		user.getRoles().addAll(roles);
		
		entityManager.persist(user);
		return user;
	}
}
