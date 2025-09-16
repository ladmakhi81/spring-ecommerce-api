package com.codyproo.ecommerce.services.impl;

import com.codyproo.ecommerce.dtos.transaction.CreateTransactionDto;
import com.codyproo.ecommerce.entities.Transaction;
import com.codyproo.ecommerce.repositories.TransactionRepository;
import com.codyproo.ecommerce.services.TransactionService;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction create(CreateTransactionDto dto) {
        var verification = dto.getVerificationResult();
        var payment = dto.getPayment();
        var transaction = Transaction.builder()
                .refId(verification.getRefId().toString())
                .user(payment.getUser())
                .order(payment.getOrder())
                .amount(payment.getAmount())
                .cardHash(verification.getCardHash())
                .cardPan(verification.getCardPan())
                .payment(payment)
                .build();
        transactionRepository.save(transaction);
        return transaction;
    }
}
