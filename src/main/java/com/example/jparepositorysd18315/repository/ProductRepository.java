package com.example.jparepositorysd18315.repository;

import com.example.jparepositorysd18315.entity.Product;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    //1. Methods: save, saveAll, findById

    //2
    List<Product> findByNameContaining(String s);

    //3. Query creation
    // JPQL / Native query

    // JPQL + index
    @Query("SELECT p FROM Product p WHERE p.name=?1 OR p.id=?2")
    List<Product> findByNameOrId(String name, long id);

    // Native + name
    @Query(value = "SELECT * FROM product p WHERE p.name=:name OR p.id=:id", nativeQuery = true)
    List<Product> findByNameOrIdV2(String name, long id);

    //4. Named queries
    // JPQL / Native

    List<Product> findByNameV3(String name);

    List<Product> findByNameV4(String name);















}
