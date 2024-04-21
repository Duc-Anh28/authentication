package com.example.authentication.Service.impl;

import com.example.authentication.Service.RateLimitingService;
import com.example.authentication.repository.AccountRepository;
import io.github.bucket4j.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import javax.transaction.Transactional;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class RateLimitingServiceImpl implements RateLimitingService {

    @Value("${jwt.secret}")
    private String secretKey;
    private final AccountRepository accountRepository;
    private final MessageSource messageSource;

    private static final Map<String, Bucket> bucketCache = new ConcurrentHashMap<>();
    private final int LIMIT_LOGIN = 4;
    public final int LIMIT_LOGIN_ERROR = 2;
    public final int DURATION_LIMIT_LOGIN_IN_SECOND = 60;
    public final int DURATION_LIMIT_AFTER_ERROR_ONCE_IN_SECOND = 120;

    public Bucket resolveBucket(String keyBucketCache, int limit, Duration duration) {
        return bucketCache.computeIfAbsent(keyBucketCache, k -> newBucket(limit, duration));
    }

    public void deleteIfExists(final String email) {
        bucketCache.remove(email);
    }

    public boolean checkIfExists(String email) {
        return bucketCache.containsKey(email);
    }

    private Bucket newBucket(int limit, Duration duration) {
        return Bucket4j.builder()
                .addLimit(Bandwidth.classic(limit, Refill.intervally(limit, duration)))
                .build();
    }

    @Transactional
    public boolean preventSpamLogin(String keyBucketCache) {
        var keyBucketCacheAfterErrorOnce = keyBucketCache.concat(secretKey);
        final Bucket tokenBucket = resolveBucket(keyBucketCache, 4, Duration.ofMinutes(1));
        final ConsumptionProbe probe = tokenBucket.tryConsumeAndReturnRemaining(1);
        if (!checkIfExists(keyBucketCacheAfterErrorOnce)) {
            resolveBucket(keyBucketCacheAfterErrorOnce, 2, Duration.ofMinutes(2));
        }
        var remainAvailableToken = this.bucketCache.get(keyBucketCacheAfterErrorOnce).getAvailableTokens();
        if (!probe.isConsumed()) {
            var message = messageSource.getMessage("login.too-many-request",
                    new Integer[]{4, 1},
                    LocaleContextHolder.getLocale()
            );
            if (remainAvailableToken == 2) {
                this.bucketCache.get(keyBucketCacheAfterErrorOnce).tryConsume(1);
                throw new HttpClientErrorException(HttpStatus.TOO_MANY_REQUESTS, message);
            } else if (remainAvailableToken == 1) {
                throw new HttpClientErrorException(HttpStatus.TOO_MANY_REQUESTS, message);
            } else {
                this.bucketCache.get(keyBucketCacheAfterErrorOnce).tryConsumeAndReturnRemaining(1);
                deleteIfExists(keyBucketCache);
                deleteIfExists(keyBucketCacheAfterErrorOnce);
//                accountRepository.updateAccountActive(keyBucketCache);
                return false;
            }
        } else if (remainAvailableToken == 1) {
            this.bucketCache.get(keyBucketCacheAfterErrorOnce).tryConsume(1);
        }
        return true;
    }

}
