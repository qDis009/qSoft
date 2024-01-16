package kz.qBots.qSoft.rest.controller;

import kz.qBots.qSoft.data.dto.RoleDto;
import kz.qBots.qSoft.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/role")
public class RoleController {
    private final RoleService roleService;
    @GetMapping("/get-all")
    public ResponseEntity<List<RoleDto>> getAll(){
        return ResponseEntity.ok(roleService.getAll());
    }
}
