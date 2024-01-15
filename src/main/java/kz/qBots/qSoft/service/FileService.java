package kz.qBots.qSoft.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
  String uploadFile(MultipartFile file, String bucketName, String filePrefix) throws IOException;

}
