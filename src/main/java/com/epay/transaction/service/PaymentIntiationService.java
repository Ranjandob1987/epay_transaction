
import com.example.payment.utils.ATRNGenerator;

public TransactionResponse<PaymentResponse> initiatePayment(String payMode, String orderNumber) {
    // Step 1: Fetch Merchant Details
    EPayPrincipal ePayPrincipal = (EPayPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    String merchantId = ePayPrincipal.getMerchantId();

    // Step 2: Fetch Order Details
    Optional<PaymentTransaction> existingOrder = transactionRepository.findByOrderNumber(orderNumber);
    if (existingOrder.isPresent() && existingOrder.get().getAtrn() != null) {
        throw new IllegalStateException("ATRN already exists. Cannot initiate payment again.");
    }

    // Step 3: Generate 15-digit ATRN Number
    String atrn = ATRNGenerator.generateATRN();

    // Step 4: Save PaymentTransaction to the Database
    PaymentTransaction transaction = new PaymentTransaction();
    transaction.setAtrn(atrn);
    transaction.setOrderNumber(orderNumber);
    transaction.setReferenceNumber(UUID.randomUUID().toString()); // Generate a unique reference number
    transaction.setOrderAmount(1000.00); // Placeholder for the order amount
    transaction.setStatus("PENDING");

    transactionRepository.save(transaction);

    // Step 5: Call Payment Gateway API
    String paymentUrl = callPaymentGatewayAPI(payMode, atrn);

    // Step 6: Construct Response
    PaymentResponse paymentResponse = PaymentResponse.builder()
            .atrn(atrn)
            .paymentUrl(paymentUrl)
            .status("PENDING")
            .build();

    return TransactionResponse.<PaymentResponse>builder()
            .status(1)
            .count(1L)
            .data(List.of(paymentResponse))
            .build();
}

package com.epay.transaction.service;

import com.epay.transaction.dao.PaymentTxnDao;
import com.epay.transaction.dao.TokenDao;
import com.epay.transaction.dto.PaymentDto;
import com.epay.transaction.exceptions.ValidationException;
import com.epay.transaction.model.request.PaymentRequest;
import com.epay.transaction.model.response.CustomerResponse;
import com.epay.transaction.model.response.PaymentResponse;
import com.epay.transaction.model.response.TransactionResponse;
import com.epay.transaction.util.AtrnGeneration;
import com.epay.transaction.util.ErrorConstants;
import com.epay.transaction.util.TransactionUtil;
import com.epay.transaction.util.UniqueIDGenearator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbi.epay.authentication.model.EPayPrincipal;
import com.sbi.epay.authentication.service.JwtService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

/**
 * Class Name:PaymentIntiationService
 * *
 * Description:
 * *
 * Author:V1014352(Ranjan Kumar)
 * <p>
 * Copyright (c) 2024 [State Bank of INdia]
 * All right reserved
 * *
 * Version:1.0
 */
@Service
@RequiredArgsConstructor
public class PaymentIntiationService {
    private final PaymentTxnDao paymentTxnDao;
    private final UniqueIDGenearator uniqueIDGenearator;
    private final ObjectMapper objectMapper;

    public TransactionResponse<PaymentResponse> initiatePayment(String payMode, String orderNumber) {
        EPayPrincipal ePayPrincipal = (EPayPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // Step 1 : Fetch the Merchant details
        // Step 2 : Fetch the Order details
        // Step 3 : Fetch the Transaction Details based on Order Number which is not having the atrn number, If ATRN is present then its not eligiable for initiation
        // Step 4 : Fetch the Transaction Charges Details
        // Step 5 : Call the Payment Validator for initiation and validated the
                    // Merchant is active or not
                    // Order is in Booked or Open state or not
                    // Merchant is having that payMode as initiate
                    // Order Amount Validation as per merchant limit on daily, weekly, monthly
                   // ETC...
        // Step 6 : IF validation Passed then generate the ATRN number
        // Step 7 : Call the Payment Service API for respective PayMode and share the to API
        // Step 8 : Get the Payment Response from Payment service and share back to page

        return TransactionResponse.<PaymentResponse>builder().data(List.of()).status(1).count(1L).build();
    }
}

