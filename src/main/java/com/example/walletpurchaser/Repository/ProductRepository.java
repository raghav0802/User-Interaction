package com.example.walletpurchaser.Repository;


import com.example.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    //  Find by name (exact match)
    Optional<Product> findByName(String name);

    //  Find all active products

    List<Product> findByActiveTrue();

    //  Search by keyword in name or description (case-insensitive)
    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Product> searchByKeyword(String keyword);

    //  Find products below a price
    List<Product> findByPriceLessThan(Double price);



    //  List all active products sorted by price ascending
    List<Product> findByActiveTrueOrderByPriceAsc();

    //  Check if a product is purchasable
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Product p WHERE p.id = :productId AND p.active = true")
    boolean isProductActive(Long productId);



    @Query("SELECT p FROM Product p WHERE p.price BETWEEN :minPrice AND :maxPrice")
    List<Product> findByPriceBetween(double minPrice, double maxPrice);

    // find product by name (in small letters )
    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Product> findByNameContainingIgnoreCase(String name);


}
