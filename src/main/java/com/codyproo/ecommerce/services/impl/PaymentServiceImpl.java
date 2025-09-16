package com.codyproo.ecommerce.services.impl;

import com.codyproo.ecommerce.common.exceptions.BadRequestException;
import com.codyproo.ecommerce.common.exceptions.NotFoundException;
import com.codyproo.ecommerce.dtos.payment.CreatePaymentDto;
import com.codyproo.ecommerce.dtos.payment.InitRequestDto;
import com.codyproo.ecommerce.dtos.payment.VerifyRequestDto;
import com.codyproo.ecommerce.dtos.payment.ZarinpalConfigDto;
import com.codyproo.ecommerce.dtos.transaction.CreateTransactionDto;
import com.codyproo.ecommerce.entities.OrderStatus;
import com.codyproo.ecommerce.entities.Payment;
import com.codyproo.ecommerce.entities.PaymentStatus;
import com.codyproo.ecommerce.events.PurchaseOrderEvent;
import com.codyproo.ecommerce.repositories.PaymentRepository;
import com.codyproo.ecommerce.services.PaymentGatewayService;
import com.codyproo.ecommerce.services.PaymentService;
import com.codyproo.ecommerce.services.TransactionService;
import jakarta.transaction.Transactional;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentGatewayService paymentGatewayService;
    private final ZarinpalConfigDto zarinpalConfigDto;
    private final TransactionService transactionService;
    private final ApplicationEventPublisher eventPublisher;

    private final Long verifiedBeforeStatusCode = 101L;
    private final Long verifiedSuccessfullyStatusCode = 100L;

    public PaymentServiceImpl(PaymentRepository paymentRepository, PaymentGatewayService paymentGatewayService, ZarinpalConfigDto zarinpalConfigDto, TransactionService transactionService, ApplicationEventPublisher eventPublisher) {
        this.paymentRepository = paymentRepository;
        this.paymentGatewayService = paymentGatewayService;
        this.zarinpalConfigDto = zarinpalConfigDto;
        this.transactionService = transactionService;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Payment createPayment(CreatePaymentDto dto) throws Exception {
        var paymentRequest = paymentGatewayService.initRequest(
                new InitRequestDto(dto.getAmount())
        );
        if (paymentRequest == null) {
            throw new BadRequestException("payment gateway has problem!");
        }
        var payLink = zarinpalConfigDto.getPayLink() + "/" + paymentRequest.getAuthority();
        var payment = Payment.builder()
                .order(dto.getOrder())
                .user(dto.getUser())
                .authority(paymentRequest.getAuthority())
                .amount(dto.getAmount())
                .expirationDate(LocalDateTime.now().plusMinutes(30))
                .payLink(payLink)
                .build();
        paymentRepository.save(payment);
        return payment;
    }

    @Override
    @Transactional
    public void verifyPayment(String authority, String status) throws Exception {
        var payment = paymentRepository.findByAuthority(authority)
                .orElseThrow(() -> new NotFoundException("payment not found!"));
        if (!payment.getStatus().equals(PaymentStatus.Pending)) {
            throw new BadRequestException("payment status is incorrect!");
        }
        var verifyResult = paymentGatewayService.verifyRequest(
                new VerifyRequestDto(payment.getAmount(), authority)
        );
        if (verifyResult == null) {
            throw new BadRequestException("payment gateway can't verified!");
        }
        if (verifyResult.getCode().equals(verifiedBeforeStatusCode)) {
            throw new BadRequestException("your payment verified before");
        }
        var isSuccess = (status.equals("OK") && verifyResult.getCode().equals(verifiedSuccessfullyStatusCode));
        if (isSuccess) {
            payment.setStatus(PaymentStatus.Payed);
            payment.setPayedDate(LocalDateTime.now());
            payment.getOrder().setStatus(OrderStatus.Successed);
            transactionService.create(
                    new CreateTransactionDto(verifyResult, payment)
            );
            eventPublisher.publishEvent(
                    PurchaseOrderEvent.builder()
                            .id(payment.getOrder().getId())
                            .fullName(payment.getUser().getFulllName())
                            .email(payment.getUser().getEmail())
                            .subject("Purchase Order")
                            .build()
            );
        } else {
            payment.setStatus(PaymentStatus.Error);
            payment.getOrder().setStatus(OrderStatus.Cancelled);
        }
        paymentRepository.save(payment);
    }
}
