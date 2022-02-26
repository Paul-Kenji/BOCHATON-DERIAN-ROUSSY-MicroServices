package com.estia.ms.client.proxies;

import com.estia.ms.client.beans.OrderBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ms-orders", url = "localhost:8093")
public interface MsOrderProxy {

    @PostMapping(value="/orders")
    public ResponseEntity<OrderBean> createNewOrder(@RequestBody OrderBean orderData);

    @PostMapping(value = "/orders/test")
    public ResponseEntity<OrderBean> createOrder(@RequestBody OrderBean order);
}
