package kz.qBots.qSoft.rest.controller;

import kz.qBots.qSoft.data.dto.CategoryDto;
import kz.qBots.qSoft.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;
    @GetMapping("/get-all")
    public ResponseEntity<Page<CategoryDto>> getAll(@PageableDefault(size = Integer.MAX_VALUE)Pageable pageable){
        return ResponseEntity.ok(categoryService.findAll(pageable));
    }
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getById(@PathVariable("id") int id){
        return ResponseEntity.ok(categoryService.findById(id));
    }

}
