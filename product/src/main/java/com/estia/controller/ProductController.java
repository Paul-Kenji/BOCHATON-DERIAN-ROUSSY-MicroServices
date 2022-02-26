package com.estia.controller;

import com.estia.domain.Product;
import com.estia.repository.ProductRepository;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

    @Autowired
    ProductRepository productRepository;

 //   @RequestMapping(method = RequestMethod.GET)
    @GetMapping(value = "/products")
    public List<Product> list()
    {
        List<Product> products = productRepository.findAll();
        return products;
    }

    @GetMapping(value = "/products/{id}")
    public Product get(@PathVariable Long id){
        Optional<Product> product = productRepository.findById(id);
        if(!product.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product was not found");
        return product.get();
    }
}
