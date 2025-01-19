package com.bill.tech.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.bill.tech.entity.KeyValue;
import com.bill.tech.repository.KeyValueRepo;
import com.bill.tech.util.KeyValueStore;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class AwsConfig {

    private final KeyValueRepo keyValueRepo;

    public List<KeyValue> getAllValues() {
    	if(keyValueRepo.count()!=0)
    		return keyValueRepo.findAll();
			return null;
 }

    @Bean
    KeyValueStore keyValueStore() {
        List<KeyValue> keyValuePairs = getAllValues();
        return new KeyValueStore(
            keyValuePairs.stream().filter(kv -> "access-key".equals(kv.getKey())).findFirst().map(KeyValue::getValue).orElse(""),
            keyValuePairs.stream().filter(kv -> "secret-key".equals(kv.getKey())).findFirst().map(KeyValue::getValue).orElse(""),
            keyValuePairs.stream().filter(kv -> "region".equals(kv.getKey())).findFirst().map(KeyValue::getValue).orElse("us-east-1"),
            keyValuePairs.stream().filter(kv -> "bucket.name".equals(kv.getKey())).findFirst().map(KeyValue::getValue).orElse("")
        );
    }

    @Bean
    AmazonS3 amazonS3(KeyValueStore keyValueStore) {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(keyValueStore.getAccessKey(), keyValueStore.getSecretKey());
        return AmazonS3ClientBuilder.standard()
                .withRegion(keyValueStore.getRegion())
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();
    }
    @Bean
    public String bucketName(KeyValueStore keyValueStore) {
        return keyValueStore.getBucketName(); // Provide bucket name as a bean
    }
}


