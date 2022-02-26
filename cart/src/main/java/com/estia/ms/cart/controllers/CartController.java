package com.estia.ms.cart.controllers;

import com.estia.ms.cart.domain.Cart;
import com.estia.ms.cart.domain.CartItem;
import com.estia.ms.cart.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
public class CartController {

    @Autowired
    CartRepository cartRepository;

    @GetMapping(value = "/cart/{id}")
    public Cart get(@PathVariable Long id){
        Optional<Cart> cart = cartRepository.findById(id);
        if(!cart.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product was not found");
        return cart.get();
    }

    @PostMapping(value= "/cart/{cartId}/add/{productId}")
    public ResponseEntity<CartItem> addProductToCart(@PathVariable("cartId") Long cartId, @PathVariable("productId") Long productId, @RequestBody CartItem cartItem){
        Optional<Cart> cart = cartRepository.findById(cartId);
        if(!cart.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart was not found");

        cart.get().addProduct(cartItem);
        cartRepository.save(cart.get());

        return new ResponseEntity<CartItem>(cartItem, HttpStatus.CREATED);
    }

    @PostMapping(value="/cart")
    public ResponseEntity<Cart> createNewCart(@RequestBody Cart cartData)
    {
        Cart cartInstance = cartRepository.save(new Cart());
        if(cartInstance == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Something went wrong");

        return new ResponseEntity<Cart>(cartInstance, HttpStatus.CREATED);
    }
}
