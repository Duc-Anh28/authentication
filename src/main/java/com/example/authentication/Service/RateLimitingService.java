package com.example.authentication.Service;

public interface RateLimitingService {
    boolean preventSpamLogin(String keyBucketCache);

}
