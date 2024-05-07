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
    public ResponseEntity<MenuDetailDto> getMenuDetails(@PathVariable Long id) {
        MenuDetailDto menuDetail = menuService.getAllMenuDetails(id);

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


    //아이디 값으로 메뉴리스트
//    @GetMapping("/menuList/{id}")
//    public ResponseEntity<Menu> getMenu(@PathVariable Long id) {
//        Optional<Menu> menuOptional = menuService.menuId(id);
//        return menuOptional.map(ResponseEntity::ok)
//                .orElseGet(()-> ResponseEntity.notFound().build());
//    }
//
//    //아이디 값으로 영양정보
//    @GetMapping("/menuInfo/{id}")
//    public ResponseEntity<MenuInfo> getMenuInfoById(@PathVariable Long id) {
//        Optional<MenuInfo> menuInfoOptional = menuService.menuInfoId(id);
//        return menuInfoOptional.map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }
//    //아이디 값으로 칼로리정보
//    @GetMapping("/nutrient/{id}")
//    public ResponseEntity<Nutrient> getNutrientById(@PathVariable Long id) {
//        Optional<Nutrient> nutrientOptional = menuService.nutrient(id);
//            return  nutrientOptional.map(ResponseEntity::ok)
//                    .orElseGet(() -> ResponseEntity.notFound().build());
//        }

    //영양 정보
//    @GetMapping("/menuInfos")
//    public ResponseEntity<List<MenuInfo>> getMenuInfos(){
//        List<MenuInfo> menuInfos = menuService.menuInfos();
//        return ResponseEntity.ok(menuInfos);
//    }
//    //칼로리 정보
//    @GetMapping("nutrient")
//    public ResponseEntity<List<Nutrient>> getNutrients(){
//        List<Nutrient> nutrients = menuService.nutrients();
//        return ResponseEntity.ok(nutrients);
//    }
    }

