package com.lilwel.elearning.AWS;

import com.lilwel.elearning.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.aspectj.bridge.MessageUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.iot.model.CannedAccessControlList;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class AmazonS3FileService extends AmazonClientService {

    public String uploadFileToAmazon(MultipartFile multipartFile) throws Exception {

        // Valid extensions array, like jpeg/jpg and png.
        List<String> validExtensions = Arrays.asList("pdf", "zip", "rar");

        // Get extension of MultipartFile
        String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        if (!validExtensions.contains(extension)) {
            // If file have a invalid extension, call an Exception.
            log.warn("invalid file extension");
            throw new Exception("invalid file extension");
        } else {

            // Upload file to Amazon.

            return uploadMultipartFile(multipartFile);
        }

    }

    public byte[] getFileFromAmazon(String fileKey) throws Exception{
        return  getClient()
                .getObject(GetObjectRequest
                        .builder()
                        .bucket(getBucketName())
                        .key(fileKey)
                        .build()).readAllBytes();

    }


    // Make upload to Amazon.
    private String uploadMultipartFile(MultipartFile multipartFile) throws Exception {
        String fileName;

        try {
            // Get the file from MultipartFile.
            File file = FileUtil.convertMultipartToFile(multipartFile);

            // Extract the file name.
            fileName = new Date().getTime() + "-" + UUID.randomUUID();
            // Upload file.
            uploadPublicFile(fileName, file);
            // Delete the file and get the File Url.
            file.delete();
        } catch (IOException e) {
            // If IOException on conversion or any file manipulation, call exception.
            log.warn("multipart.to.file.convert.except");
            throw new Exception("multipart.to.file.convert.except");
        }

        return fileName;
    }

    private void uploadPublicFile(String fileName, File file)  {
            getClient()
                    .putObject(PutObjectRequest
                            .builder()
                            .bucket(getBucketName())
                            .key(fileName)
                            .build(), RequestBody.fromFile(file));

    }

}
