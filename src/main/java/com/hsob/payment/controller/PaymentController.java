package com.hsob.payment.controller;

import com.hsob.payment.dto.PaymentDto;
import com.hsob.payment.service.PaymentService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @GetMapping
    public List<PaymentDto> getAll(@PageableDefault(size = 10) Pageable pageable) {
        return paymentService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDto> getById(@PathVariable @NotNull Long id) {
        return ResponseEntity.ok(paymentService.getById(id));
    }

    @PostMapping
    public ResponseEntity<PaymentDto> savePayment(@RequestBody @Valid PaymentDto dto,
                                                  UriComponentsBuilder uriBuilder) {
        PaymentDto payment = paymentService.savePayment(dto);
        URI uri = uriBuilder.path("/pagamentos/{id}").buildAndExpand(payment.getId()).toUri();
        return ResponseEntity.created(uri).body(payment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentDto> updatePayment(@PathVariable @NotNull Long id,
                                                    @RequestBody @Valid PaymentDto dto) {
        return ResponseEntity.ok(paymentService.updatePayment(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PaymentDto> deletePayment(@PathVariable @NotNull Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/confirmar")
    @CircuitBreaker(name = "approvePayment", fallbackMethod = "approvePaymentPending")
    public void  approvePayment(@PathVariable @NotNull Long id){
        paymentService.approvePayment(id);
    }

    public void approvePaymentPending(@PathVariable @NotNull Long id, Exception e){
        paymentService.approvePaymentPending(id);
    }

}
