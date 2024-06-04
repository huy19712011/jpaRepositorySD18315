package com.example.jparepositorysd18315;

import com.example.jparepositorysd18315.entity.Product;
import com.example.jparepositorysd18315.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@SpringBootApplication
public class JpaRepositorySd18315Application implements CommandLineRunner {

    Logger logger = LoggerFactory.getLogger(getClass());

    final
    ProductRepository repository;

    public JpaRepositorySd18315Application(ProductRepository repository) {
        this.repository = repository;
    }

    public static void main(String[] args) {
        SpringApplication.run(JpaRepositorySd18315Application.class, args);
        System.out.println("running...");
    }

    @Override
    public void run(String... args) throws Exception {
        //1. save
        Product product = new Product();
        product.setName("Product 1");
        repository.save(product);

        repository.findAll().forEach(p -> logger.info(p.toString()));

        //2. Query methods or finder methods
        repository.findByNameContaining("Product 101").forEach(p -> logger.info(p.toString()));

        //3. Query creation
        // JPQL / Native query


        // 4. Named queries

        //5. Pagination
        int pageNo = 0; // default = 0 ... 1,2
        int pageSize = 5;

        Pageable pageable = PageRequest.of(pageNo, pageSize);

        // get all info via Page object
        Page<Product> page = repository.findAll(pageable);

        // all of page 0
        List<Product> products = page.getContent();
        products.forEach(p -> logger.info(p.toString()));

        // total pages
        int totalPages = page.getTotalPages();
        logger.info("Total pages: " + totalPages);

        // total elements
        long totalElements = page.getTotalElements();
        logger.info("Total elements: " + totalElements);

        // size
        int size = page.getSize();
        logger.info("Size: " + size);

        // last, first
        boolean isLast = page.isLast();
        logger.info("isLast: " + isLast);

        //7. Sorting
        List<Product> products1 = repository.findAll(Sort.by("name").descending());
        products1.forEach(p -> logger.info(p.toString()));

        String sortBy = "name";
        String sortDir = "asc";
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy)
                : Sort.by(sortBy).descending();
        List<Product> products2 = repository.findAll(sort);
        products2.forEach(p -> logger.info(p.toString()));

        // multiple fields: "name", "nickName"
        //Sort.by("name").descending().and(Sort.by("nickName").descending());

        //7. Pagination + Sorting
        Page<Product> products3 = repository.findAll(PageRequest.of(pageNo, pageSize, sort));
        products3.forEach(p -> logger.info(p.toString()));
    }
}
