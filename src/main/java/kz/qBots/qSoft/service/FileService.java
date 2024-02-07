package kz.qBots.qSoft.service;

import kz.qBots.qSoft.data.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface FileService {
    Set<Image> uploadShopFeedbackFile(List<MultipartFile> multipartFiles);
}
