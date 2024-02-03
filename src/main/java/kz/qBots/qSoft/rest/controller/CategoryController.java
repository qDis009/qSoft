package kz.qBots.qSoft.rest.controller;

import kz.qBots.qSoft.data.dto.CategoryDto;
import kz.qBots.qSoft.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {
  private final CategoryService categoryService;

  @GetMapping("/get-all")
  public ResponseEntity<Page<CategoryDto>> getAll(
      @PageableDefault(size = Integer.MAX_VALUE) Pageable pageable) {
    return ResponseEntity.ok(categoryService.findAll(pageable));
  }

  @GetMapping("/{id}")
  public ResponseEntity<CategoryDto> getById(@PathVariable("id") int id) {
    return ResponseEntity.ok(categoryService.findById(id));
  }

  @PatchMapping("/{id}/set-enable")
  public ResponseEntity<Void> setEnable(
      @PathVariable("id") int id, @RequestParam("enable") boolean enable) {
    categoryService.setEnable(enable, id);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
