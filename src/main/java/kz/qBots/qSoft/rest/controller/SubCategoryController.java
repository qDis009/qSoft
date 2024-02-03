package kz.qBots.qSoft.rest.controller;

import kz.qBots.qSoft.data.dto.ItemDto;
import kz.qBots.qSoft.data.dto.SubCategoryDto;
import kz.qBots.qSoft.service.SubCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sub-category")
public class SubCategoryController {
  private final SubCategoryService subCategoryService;

  @GetMapping("/{id}")
  public ResponseEntity<SubCategoryDto> getById(@PathVariable("id") int id) {
    return ResponseEntity.ok(subCategoryService.findById(id));
  }

  @GetMapping("/{id}/items/{userId}")
  public ResponseEntity<List<ItemDto>> getItemsBySubCategory(
      @PathVariable("id") int id, @PathVariable("userId") int userId) {
    return ResponseEntity.ok(subCategoryService.findItemsBySubCategoryId(id, userId));
  }

  @PatchMapping("/{id}/set-enable")
  public ResponseEntity<Void> setEnable(
      @PathVariable("id") int id, @RequestParam("enable") boolean enable) {
    subCategoryService.setEnable(enable, id);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
