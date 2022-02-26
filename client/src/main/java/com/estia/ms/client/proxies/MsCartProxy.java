package com.estia.ms.client.proxies;

import com.estia.ms.client.beans.CartBean;
import com.estia.ms.client.beans.CartItemBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "ms-cart", url = "localhost:8092")
public interface MsCartProxy {

    @GetMapping(value = "/cart/{id}")
    public CartBean get(@PathVariable Long id);

    @PostMapping(value="/cart")
    public ResponseEntity<CartBean> createNewCart(@RequestBody CartBean cartData);

    @PostMapping(value= "/cart/{id}")
    public ResponseEntity<CartItemBean> addProductToCart(@PathVariable Long id, @RequestBody CartItemBean cartItem);

    @PostMapping(value= "/cart/{cartId}/add/{productId}")
    public ResponseEntity<CartBean> addProductToCart(@PathVariable("cartId") Long cartId, @PathVariable("productId") Long productId, @RequestBody CartItemBean cartItem);
}