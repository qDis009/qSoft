package kz.qBots.qSoft.service.impl;

import kz.qBots.qSoft.data.component.ImageComponent;
import kz.qBots.qSoft.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageComponent imageComponent;
    @Override
    public void delete(int id) {
        imageComponent.delete(id);
    }
}
