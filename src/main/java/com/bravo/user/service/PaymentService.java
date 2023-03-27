package com.bravo.user.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.bravo.user.dao.model.Payment;
import com.bravo.user.exception.DataNotFoundException;
import com.bravo.user.model.dto.*;
import com.bravo.user.utility.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bravo.user.dao.model.mapper.ResourceMapper;
import com.bravo.user.dao.repository.PaymentRepository;
import com.bravo.user.model.dto.PaymentDto;

@Service
public class PaymentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentService.class);

    private final PaymentRepository paymentRepository;
    private final ResourceMapper resourceMapper;

    public PaymentService(PaymentRepository paymentRepository, ResourceMapper resourceMapper) {
        this.paymentRepository = paymentRepository;
        this.resourceMapper = resourceMapper;
    }

    public List<PaymentDto> retrieveByUserId(final String userId) {
        final List<Payment> paymentList = paymentRepository.findByUserId(userId);
        LOGGER.info("found {} payment(s)", paymentList.size());

        return resourceMapper.convertPayments(paymentList);
    }

    public PaymentDto create(final PaymentSaveDto request){
        Payment payment = paymentRepository.save(new Payment(request));

        LOGGER.info("created payment '{}'", payment.getId());
        return resourceMapper.convertPayment(payment);
    }

    public PaymentDto update(final String id, final PaymentSaveDto request){

        final Optional<Payment> optional = paymentRepository.findById(id);
        final Payment payment = getPayment(id, optional);
        payment.setUpdated(LocalDateTime.now());

        if(ValidatorUtil.isValid(request.getUserId())){
            payment.setUserId(request.getUserId());
        }
        if(ValidatorUtil.isValid(request.getCardNumber())){
            payment.setCardNumber(request.getCardNumber());
        }
        if(ValidatorUtil.isValid(request.getExpiryMonth())){
            payment.setExpiryMonth(request.getExpiryMonth());
        }
        if(ValidatorUtil.isValid(request.getExpiryYear())){
            payment.setExpiryYear(request.getExpiryYear());
        }

        final Payment updated = paymentRepository.save(payment);

        LOGGER.info("updated payment '{}'", updated.getId());
        return resourceMapper.convertPayment(updated);
    }

    public boolean delete(final String id){

        final Optional<Payment> optional = paymentRepository.findById(id);
        final Payment payment = getPayment(id, optional);

        paymentRepository.deleteById(payment.getId());

        final boolean isDeleted = paymentRepository.findById(id).isEmpty();
        if(isDeleted){
            LOGGER.info("deleted payment '{}'", id);
        } else {
            LOGGER.warn("failed to delete payment '{}'", id);
        }
        return isDeleted;
    }

    public boolean deleteByUserId(final String userId){
        final List<Payment> paymentList = paymentRepository.findByUserId(userId);
        boolean isDeleted = true;

        for (Payment payment : paymentList) {
            paymentRepository.deleteById(payment.getId());
            if (paymentRepository.findById(payment.getId()).isEmpty()) {
                LOGGER.info("deleted user {} payment '{}'", userId, payment.getId());
            } else {
                LOGGER.warn("failed to delete user {} payment '{}'", userId, payment.getId());
                isDeleted = false;
            }
        }

        return isDeleted;
    }

    private Payment getPayment(final String id, final Optional<Payment> payment){

        if(payment.isEmpty()){
            final String message = String.format("payment '%s' doesn't exist", id);
            LOGGER.warn(message);
            throw new DataNotFoundException(message);
        }
        return payment.get();
    }
}
