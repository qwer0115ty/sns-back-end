package com.boot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties("aws.s3")
public class AmazonS3Prop {
	private String region;
    private String bucketName;
    private String accessKey;
    private String secretKey;
}
