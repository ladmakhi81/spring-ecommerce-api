package com.codyproo.ecommerce.services;

import com.codyproo.ecommerce.dtos.transaction.CreateTransactionDto;
import com.codyproo.ecommerce.entities.Transaction;

public interface TransactionService {
    Transaction create(CreateTransactionDto dto);
}
