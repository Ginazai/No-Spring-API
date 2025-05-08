package com.dashboard.Services;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.dashboard.Entities.ProductCategory;
@Stateless
public class CategoryService {
	// Inject the EntityManager
	@PersistenceContext(unitName = "myUnit")
    private EntityManager entityManager;
	// Save a new category
	public ProductCategory guardar(ProductCategory category) {
		category.setLast_update(LocalDateTime.now());
		category.setCreate_date(LocalDateTime.now());
		category.setActive(true);
		entityManager.persist(category);
    	return category;
	}
	// Find a category by name
    public ProductCategory buscarPorNombre(String name) {
    	List<ProductCategory> result = entityManager.createQuery("SELECT c FROM ProductCategory c WHERE c.nombre = :name", ProductCategory.class)
            .setParameter("name", name)
            .getResultList();
        return result.isEmpty() ? null : result.get(0);
    }
	// Find a category by ID
	public ProductCategory buscarPorId(Long id) {
		return entityManager.find(ProductCategory.class, id);
	}
	// List all categories
	public List<ProductCategory> listarCategorias() {
	    try {
	        return entityManager.createQuery("SELECT c FROM ProductCategory c", ProductCategory.class).getResultList();
	    } catch (Exception e) {
	        e.printStackTrace(); // Log the exception
	        return Collections.emptyList(); // Return an empty list if an error occurs
	    }
	}
	// Update a category
	@Transactional
	public ProductCategory actualizarCategoria(Long id, ProductCategory updatedCategory) {
		// Find the existing category by ID
		ProductCategory existingCategory = entityManager.find(ProductCategory.class, id);
		if (existingCategory != null) {
			LocalDateTime newLastUpdate = LocalDateTime.now();
			// Update the fields of the existing entity with the new values
			existingCategory.setName(updatedCategory.getName() != null ? updatedCategory.getName() 
					: existingCategory.getName());
			existingCategory.setLast_update(newLastUpdate);
			existingCategory.setActive(updatedCategory.getActive() != null ? updatedCategory.getActive()
					: existingCategory.getActive());
			existingCategory.setCreate_date(updatedCategory.getCreate_date() != null ? updatedCategory.getCreate_date()	
					: existingCategory.getCreate_date());
			existingCategory.setLast_update(newLastUpdate);
			// Merge the updated entity
			// to synchronize it with the database
			// and return the updated entity
			entityManager.persist(existingCategory);
			return existingCategory;
		} else {return null;}
	}
	// Delete a category (deactivate it)
	public ProductCategory borrarCategoria(Long id) {
		ProductCategory category = entityManager.find(ProductCategory.class, id);
        if (category == null) {
        	category = entityManager.merge(category);
        }
        category.setActive(false);
        return category;
	}
}
