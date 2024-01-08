package kz.qBots.qSoft.rest.controller;

import kz.qBots.qSoft.data.dto.SubCategoryDto;
import kz.qBots.qSoft.service.SubCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sub-category")
public class SubCategoryController {
  private final SubCategoryService subCategoryService;

  @GetMapping("/{id}")
  public ResponseEntity<SubCategoryDto> getById(@PathVariable("id") int id) {
    return ResponseEntity.ok(subCategoryService.findById(id));
  }
}
