package com.epay.transaction.entity.cache;

import com.epay.transaction.dto.MerchantDto;
import com.epay.transaction.service.TokenService;
import com.sbi.epay.logging.utility.LoggerFactoryUtility;
import com.sbi.epay.logging.utility.LoggerUtility;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MerchantCacheRepository {
    private static final LoggerUtility log = LoggerFactoryUtility.getLogger(TokenService.class);


    public Optional<MerchantDto> getActiveMerchantByKeys(String merchantApiKey, String merchantSecretKey) {
        //TODO : Call Hazelcast utility get method for Merchant by keys
        return Optional.empty();
    }

    public Optional<MerchantDto> getActiveMerchantByMID(String mID) {
        //TODO : Call Hazelcast utility get method for Merchant by MID
        return Optional.empty();
    }
}