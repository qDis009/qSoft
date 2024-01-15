package kz.qBots.qSoft.service.impl;

import kz.qBots.qSoft.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    @Override
    public String uploadFile(MultipartFile file, String bucketName, String filePrefix) throws IOException {
        return null;
    }
}
