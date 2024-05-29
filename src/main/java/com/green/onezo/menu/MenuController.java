package com.green.onezo.menu;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "menu-controller", description = "메뉴")
public class  MenuController {

    private final MenuService menuService;

    @Operation(summary = "메뉴 정보 보내주기")
    @GetMapping("/menus/{id}")
    public ResponseEntity<MenuDetailDto> getMenuDetails(@PathVariable Long id,Long menuInfoId) {
        MenuDetailDto menuDetail = menuService.getAllMenuDetails(id,menuInfoId);

        if (menuDetail != null){
            return ResponseEntity.ok(menuDetail);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(menuDetail);
        }
    }

    @Operation(summary = "메뉴 전체 리스트")
    @GetMapping("/menuAll")
    public ResponseEntity<List<Menu>> menuList(){
        List<Menu> menus = menuService.menuList();
        return ResponseEntity.ok(menus);
    }

//    @Operation(summary = "메뉴재고 상태 여부")
//    @GetMapping("/status")
//    public ResponseEntity<List<MenuStatus>> menuStatus(){
//        List<MenuStatus> statuses = menuService.status();
//        return ResponseEntity.ok(statuses);
//    }


    }

