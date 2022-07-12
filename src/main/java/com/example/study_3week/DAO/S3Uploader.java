package com.example.study_3week.DAO;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String upload(MultipartFile multipartFile, String dirName) throws IOException {
        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File로 전환이 실패했습니다."));

        return upload(uploadFile, dirName); // 밑으로 이동
    }

    private String upload(File uploadFile, String dirName) {
        String fileName = dirName + "/" + uploadFile.getName();
        String uploadImageUrl = putS3(uploadFile, fileName); // 여기에서 S3에 올리는듯 // 밑에 함수
        removeNewFile(uploadFile); // 맨 밑에 함수
        return uploadImageUrl;
    }

    private String putS3(File uploadFile, String fileName) { // 여기서 실제로 s3에 올라감
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private void removeNewFile(File targetFile) { // 로컬에 저장한 정보를 이용해서 올렸다가, 로컬에 저장된거를 지우는거 같은데?
        if (targetFile.delete()) {
            log.info("파일이 삭제되었습니다."); //이게 제대로 실행 되는거
        } else {
            log.info("파일이 삭제되지 못했습니다.");
        }
    }

    private Optional<File> convert(MultipartFile file) throws IOException {
        System.out.println("================1");
        String fileName = file.getOriginalFilename();
        File convertFile = new File(file.getOriginalFilename());
        //System.out.println(convertFile.createNewFile()); // 이미 있으면 false, 없으면 true
        System.out.println("================2");
        if(convertFile.createNewFile()) { // 파일이 이전에 존재하지 않으면 true를 띄우고 들어감.
            System.out.println("================3");
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
                System.out.println("================4");
            }
            return Optional.of(convertFile);
        }

        return Optional.empty();
    }


}
