package com.bill.tech.config;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bill.tech.entity.KeyValue;
import com.bill.tech.repository.KeyValueRepo;
import com.bill.tech.util.KeyValueStore;
import com.cloudinary.Cloudinary;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class CloudinaryConfig {
    private final KeyValueRepo keyValueRepo;

    public List<KeyValue> getAllValues() {
        return keyValueRepo.count() > 0 ? keyValueRepo.findAll() : List.of();
    }

    @Bean
    public KeyValueStore keyValueStore() {
        List<KeyValue> keyValuePairs = getAllValues();

        // Safely retrieve key-value pairs using defaults if missing
        String accessKey = findKeyValue(keyValuePairs, "access-key").orElse("");
        String secretKey = findKeyValue(keyValuePairs, "secret-key").orElse("");
        String region = findKeyValue(keyValuePairs, "region").orElse("us-east-1");
        String bucketName = findKeyValue(keyValuePairs, "bucket.name").orElse("");

        return new KeyValueStore(accessKey, secretKey, region, bucketName);
    }

    // Helper method to get values safely with defaults if not present
    private Optional<String> findKeyValue(List<KeyValue> keyValuePairs, String key) {
        return keyValuePairs.stream()
                            .filter(kv -> key.equals(kv.getKey()))
                            .map(KeyValue::getValue)
                            .findFirst();
    }

    @Bean
    public Cloudinary cloudinary(KeyValueStore keyValueStore) {
        // Fetch necessary configuration values from the KeyValueStore
        String apiKey = keyValueStore.getAccessKey();
        String apiSecret = keyValueStore.getSecretKey();
        String cloudName = keyValueStore.getBucketName(); // Assuming the "bucket.name" is used for cloud name

        String cloudinaryUrl = "cloudinary://" + apiKey + ":" + apiSecret + "@" + cloudName;
        return new Cloudinary(cloudinaryUrl);
    }
}
