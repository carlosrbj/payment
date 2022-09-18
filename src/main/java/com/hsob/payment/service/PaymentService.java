package com.hsob.payment.service;

import com.hsob.documentdb.payment.Payment;
import com.hsob.documentdb.payment.Status;
import com.hsob.payment.dto.PaymentDto;
import com.hsob.payment.httpClient.OrderClient;
import com.hsob.payment.repository.PaymentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private OrderClient orderClient;

    public Page<PaymentDto> getAll(Pageable pageable) {
        return paymentRepository
                .findAll(pageable)
                .map(p -> modelMapper.map(p, PaymentDto.class));
    }

    public PaymentDto getById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        return modelMapper.map(payment, PaymentDto.class);
    }

    public PaymentDto savePayment(PaymentDto dto) {
        Payment payment = modelMapper.map(dto, Payment.class);
        payment.setStatus(Status.CRIADO);
        paymentRepository.save(payment);

        return modelMapper.map(payment, PaymentDto.class);
    }

    public PaymentDto updatePayment(Long id, PaymentDto dto) {
        Payment payment = modelMapper.map(dto, Payment.class);
        payment.setId(id);
        payment = paymentRepository.save(payment);
        return modelMapper.map(payment, PaymentDto.class);
    }

    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }

    public void approvePayment(Long id){
        Optional<Payment> payment = paymentRepository.findById(id);
        if (!payment.isPresent())  throw new EntityNotFoundException();
        payment.get().setStatus(Status.CONFIRMADO);
        paymentRepository.save(payment.get());
        orderClient.approvePayment(payment.get().getId());
    }

    public void approvePaymentPending(Long id) {
        Optional<Payment> payment = paymentRepository.findById(id);
        if (!payment.isPresent())  throw new EntityNotFoundException();
        payment.get().setStatus(Status.CONFIRMADO_SEM_INTEGRACAO);
        paymentRepository.save(payment.get());
    }
}
