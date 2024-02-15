package kz.qBots.qSoft.rest.controller;

import kz.qBots.qSoft.data.component.ImageComponent;
import kz.qBots.qSoft.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequiredArgsConstructor
@RequestMapping("/image")
public class ImageController {
  private final ImageService imageService;
  private final ImageComponent imageComponent;

  @GetMapping("/{id}")
  public ResponseEntity<Resource> getPhotos(@PathVariable("id") int id) throws IOException {
    Path filePath = Paths.get(imageComponent.findById(id).getPath());
    String mediaType = Files.probeContentType(filePath);
    return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(mediaType))
            .body(new UrlResource(filePath.toUri()));
  }
  @DeleteMapping("/{id}/delete")
  public ResponseEntity<Void> delete(@PathVariable("id") int id) {
    imageService.delete(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
