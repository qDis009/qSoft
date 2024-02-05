package kz.qBots.qSoft.service;

import org.springframework.web.multipart.MultipartFile;


public interface FileService {
    Integer uploadShopFeedbackFile(MultipartFile file);
}
