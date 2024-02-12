package kz.qBots.qSoft.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
    void uploadShopFeedbackFile(int shopFeedbackId,List<MultipartFile> multipartFiles);
    void uploadItemPhotos(int itemId,List<MultipartFile> multipartFiles);
}
