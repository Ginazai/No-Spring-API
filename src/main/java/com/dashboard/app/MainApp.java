package com.dashboard.app;

import com.dashboard.Entities.User;
import com.dashboard.util.PasswordUtil;
import com.dashboard.Entities.Role;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

public class MainApp {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myUnit");
        EntityManager em = emf.createEntityManager();

        try {
        	System.out.println("exec completed");
            em.getTransaction().begin();
            
            Role user_role = new Role();
            user_role.setName("ROLE_USER");
            em.persist(user_role);
            
            Role admin_role = em.createQuery("SELECT r FROM Role r WHERE r.name = :name", Role.class)
            .setParameter("name", "ROLE_ADMIN")
            .getResultList()
            .stream()
    	    .findFirst()
    	    .orElseGet(() -> {
    	        Role newRole = new Role();
    	        newRole.setName("ROLE_ADMIN");
    	        em.persist(newRole);
    	        return newRole;
    	    });
            
            User u = new User();
            u.setName("admin user");
            u.setUsername("admin");
            u.setPassword("@bc123");
            u.setPassword(PasswordUtil.hashPassword(u.getPassword()));
            u.setActive(true);
            u.setCreate_date(LocalDateTime.now());
            u.setLast_access(LocalDateTime.now());
            
            Set<Role> roles = new HashSet<>();
            roles.add(admin_role);
            u.setRoles(roles);

            em.persist(u);
			em.flush(); 

            em.getTransaction().commit();
        } finally {
            em.close();
            emf.close();
        }
    }
}
