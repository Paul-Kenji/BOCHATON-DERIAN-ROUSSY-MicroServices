package com.estia.ms.client.controllers;

import com.estia.ms.client.beans.*;
import com.estia.ms.client.proxies.MsCartProxy;
import com.estia.ms.client.proxies.MsOrderProxy;
import com.estia.ms.client.proxies.MsProductProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ClientController {

    @Autowired
    private MsProductProxy msProductProxy;

    @Autowired
    private MsCartProxy msCartProxy;

    @Autowired
    private MsOrderProxy msOrderProxy;

    private boolean firstConnection = true;

    @RequestMapping("/")
    public String index(Model model) {
        List<ProductBean> products =  msProductProxy.list();
        if(firstConnection){
            firstConnection = false;
            ResponseEntity<CartBean> cart = msCartProxy.createNewCart(new CartBean());
            Long cartId = cart.getBody().getId();
            model.addAttribute("cartId", cartId);
        }
        model.addAttribute("products", products);
        return "index";
    }

    @RequestMapping("product/{id}")
    public String product(Model model, @PathVariable Long id) {
        ProductBean product =  msProductProxy.get(id);
        model.addAttribute("product", product);
        return "product";
    }

    @RequestMapping("cart/{id}")
    public String cart(Model model, @PathVariable Long id) {
        CartBean cart =  msCartProxy.get(id);
        model.addAttribute("products", cart.getProducts());
        return "cart";
    }

    @RequestMapping(value= "/cart/{cartId}/add/{productId}")
    public String addProductToCart(Model model, @PathVariable("cartId") Long cartId, @PathVariable("productId") Long productId){
        ProductBean productBean = new ProductBean();
        Boolean found = false;
        List<ProductBean> products =  msProductProxy.list();
        for(ProductBean prod : products)
        {
            if (prod.getId() == productId) {
                productBean = prod;
                found = true;
                break;
            }
        }
        if (found) {
            CartBean cart = msCartProxy.addProductToCart(cartId, productId, new CartItemBean(productId, productBean.getName(), 1)).getBody();
        }

        model.addAttribute("products", products);

        return "index";
    }

    @RequestMapping("orders/{id}")
    public String order(Model model, @PathVariable Long id) {
        CartBean cart =  msCartProxy.get(id);
        OrderBean orderBean = new OrderBean(10L, new ArrayList<>(), 0D);
        for(CartItemBean cartItemsBean: cart.getProducts()) {
            OrderItemBean orderItemBean = new OrderItemBean(cartItemsBean.getProductId(), cartItemsBean.getQuantity());
            orderBean.addProduct(orderItemBean);
        }
        OrderBean lastOrderBean = msOrderProxy.createNewOrder(new OrderBean()).getBody();
//        OrderBean lastOrderBean = msOrderProxy.createOrder(new OrderBean()).getBody();
        model.addAttribute("lastOrderBean", lastOrderBean);

        return "orders";
    }
}
