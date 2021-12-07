package com.lilwel.elearning.AWS;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.*;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import javax.annotation.PostConstruct;

@Service
public class AmazonClientService {
    // AmazonS3 Client, in this object you have all AWS API calls about S3.
    private S3Client amazonS3;

    // Your bucket URL, this URL is https://{bucket-name}.s3-{region}.amazonaws.com/
    // If you don't know if your URL is ok, send one file to your bucket using AWS and
    // click on them, the file URL contains your bucket URL.


    // Your bucket name.
    @Value("${amazon.s3.bucket-name}")
    private String bucketName;

    // The IAM access key.
    @Value("${amazon.s3.access-key}")
    private String accessKey;

    // The IAM secret key.
    @Value("${amazon.s3.secret-key}")
    private String secretKey;

    // Getters for parents.
    protected S3Client getClient() {
        return amazonS3;
    }


    protected String getBucketName() {
        return bucketName;
    }

    // This method are called after Spring starts AmazonClientService into your container.
    @PostConstruct
    private void init() {

        // Init your AmazonS3 credentials using BasicAWSCredentials.
        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);

        // Start the client using AmazonS3ClientBuilder, here we goes to make a standard cliente, in the
        // region SA_EAST_1, and the basic credentials.
        this.amazonS3 = S3Client.builder().region(Region.EU_WEST_3)
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }

}