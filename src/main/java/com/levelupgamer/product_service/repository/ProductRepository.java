package com.levelupgamer.product_service.repository;

import com.levelupgamer.product_service.model.Product;
import com.levelupgamer.product_service.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
    List<Product> findByCategory(Category category);
    List<Product> findByCategoryId(Category category);
    List<Product> findByActiveTrue();
    List<Product> findByNameContainingIgnoreCase(String name);
    
    List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
    
    List<Product> findByBrandIgnoreCase(String brand);
    
    List<Product> findByPlatformIgnoreCase(String platform);
    
    List<Product> findByStockGreaterThan(Integer stock);
    
    @Query("SELECT p FROM Product p WHERE " +
           "(:categoryId IS NULL OR p.category.id = :categoryId) AND " +
           "(:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
           "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
           "(:maxPrice IS NULL OR p.price <= :maxPrice) AND " +
           "(:brand IS NULL OR LOWER(p.brand) = LOWER(:brand)) AND " +
           "(:platform IS NULL OR LOWER(p.platform) = LOWER(:platform)) AND " +
           "p.active = true")
    List<Product> searchProducts(
        @Param("categoryId") Long categoryId,
        @Param("name") String name,
        @Param("minPrice") BigDecimal minPrice,
        @Param("maxPrice") BigDecimal maxPrice,
        @Param("brand") String brand,
        @Param("platform") String platform
    );
}
