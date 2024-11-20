package com.ssafy.server.config.aws;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class S3Utils {
    private AmazonS3 amazonS3;

    @Value("${cloud.aws.credentials.access-key}")
    private String accesskey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String secretkey;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Bean
    public void setAmazonS3(){
        AWSCredentials awsCredentials = new BasicAWSCredentials(accesskey, secretkey);

        amazonS3 = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(region)
                .build();
    }
}

