
package com.bill.tech.config;

import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.bill.tech.payload.request.KeyValueDto;
import com.bill.tech.service.KeyValueService;
import static com.bill.tech.enums.ApiResponse. *;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class AwsConfig {

    private final KeyValueService keyValueService;

    @SuppressWarnings("unchecked")
	public List<KeyValueDto> getAllValues() {
        if (keyValueService.getAll().getBody() != null && keyValueService.getAll().getBody().get(DATA) != null) {
            return (List<KeyValueDto>) keyValueService.getAll().getBody().get(DATA);
        }
        return Collections.emptyList(); 
    }


    @Bean
    public AmazonS3 amazonS3() {
        List<KeyValueDto> keyValuePairs = getAllValues();
        String accessKey = keyValuePairs.stream().filter(kv -> "access-key".equals(kv.getKey())).findFirst().map(KeyValueDto::getValue).orElse("");
        String secretKey = keyValuePairs.stream().filter(kv -> "secret-key".equals(kv.getKey())).findFirst().map(KeyValueDto::getValue).orElse("");
        String region = keyValuePairs.stream().filter(kv -> "region".equals(kv.getKey())).findFirst().map(KeyValueDto::getValue).orElse("us-east-1");
        String bucket_name = keyValuePairs.stream().filter(kv -> "region".equals(kv.getKey())).findFirst().map(KeyValueDto::getValue).orElse("us-east-1");

        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretKey);
        return AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();
    }
}
