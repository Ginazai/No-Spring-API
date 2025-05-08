package com.dashboard.Services;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.dashboard.Entities.Product;
import com.dashboard.Entities.ProductCategory;
import com.dashboard.Entities.Role;
import com.dashboard.Entities.User;

@Stateless
public class ProductService {
	@PersistenceContext(unitName = "myUnit")
    private EntityManager entityManager;
	@Inject
    private CategoryService categoryService;
	
	public Product guardar(Product producto) {
		producto.setLast_update(LocalDateTime.now());
		producto.setCreate_date(LocalDateTime.now());
		producto.setActive(true);
		entityManager.persist(producto);
    	return producto;
	}
    
	public Product buscarPorId(Long id) {
		return entityManager.find(Product.class, id);
	}
	
	public List<Product> listarProductos() {
	    try {
	        return entityManager.createQuery("SELECT p FROM Product p", Product.class).getResultList();
	    } catch (Exception e) {
	        e.printStackTrace(); // Log the exception
	        return Collections.emptyList(); // Return an empty list if an error occurs
	    }
	}
	
	@Transactional
	public Product actualizarProducto(Long id, Product updatedProduct) {
		Product existingProduct = entityManager.find(Product.class, id);
		if (existingProduct == null) {
			 throw new IllegalArgumentException("Product not found with ID: " + id);
		}
		LocalDateTime newLastUpdate = LocalDateTime.now();
		existingProduct.setName(updatedProduct.getName() != null ? updatedProduct.getName() 
				: existingProduct.getName());
		existingProduct.setPrice(updatedProduct.getPrice() != 0 ? updatedProduct.getPrice()
				: existingProduct.getPrice());
		existingProduct.setTags(updatedProduct.getTags() != null ? updatedProduct.getTags()
				: existingProduct.getTags());
		existingProduct.setCreate_date(updatedProduct.getCreate_date() != null ? updatedProduct.getCreate_date()
				: existingProduct.getCreate_date());
		existingProduct.setLast_update(newLastUpdate);
		existingProduct.setActive(updatedProduct.getActive() != null ? updatedProduct.getActive()
				: existingProduct.getActive());
		return entityManager.merge(existingProduct);
	}	
	@Transactional
	public Product actualizarRoles(Long id, Set<String> newCategories) {
		Product product = entityManager.find(Product.class, id);
		if (newCategories == null || newCategories.isEmpty()) {
	        throw new IllegalArgumentException("Debe proporcionar al menos una categoria.");
	    }		
		Set<ProductCategory> categories = newCategories.stream()
	            .map(category -> categoryService.buscarPorNombre(category))
	            .filter(Objects::nonNull)
	            .collect(Collectors.toSet());
		if (categories.isEmpty()) {
	        throw new IllegalArgumentException("No se encontraron categorías válidas.");
	    }
		product.getCategories().clear();
		product.getCategories().addAll(categories);
		
		entityManager.persist(product);
		return product;
	}
	public Product borrarProducto(Long id) {
		Product product = entityManager.find(Product.class, id);
        if (product == null) {
        	product = entityManager.merge(product);
        }
        product.setActive(false);
        return product;
	}
}
