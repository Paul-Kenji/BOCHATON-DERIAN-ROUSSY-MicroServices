package com.estia.ms.client.proxies;

import com.estia.ms.client.beans.ProductBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name= "ms-product", url= "localhost:8091")
public interface MsProductProxy {

    @GetMapping(value = "/products")
    public List<ProductBean> list();

    @GetMapping(value = "/products/{id}")
    public ProductBean get(@PathVariable Long id);

}
