package com.hsob.payment.httpClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "order-service", url = "localhost:2021/order-service/orders")
public interface OrderClient {
    @RequestMapping(method = RequestMethod.PUT, value = "/{id}/pago")
    void approvePayment(@PathVariable Long id);

}
