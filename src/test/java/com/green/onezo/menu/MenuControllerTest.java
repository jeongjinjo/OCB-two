package com.green.onezo.menu;

import com.green.onezo.menu.MenuInfoRepository;
import com.green.onezo.store.Store;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MenuControllerTest {

    @Autowired
    MenuInfoRepository menuInfoRepository;
    @Autowired
    NutrientRepository nutrientRepository;
    @Autowired
    MenuRepository menuRepository;


    @Test
    @DisplayName("메뉴 정보 보내주기")
    void MeniInfo(){

        Store store = new Store();
        store.setStoreName("원조치킨 반월당점");

        Menu menu = new Menu();
        menu.setMenuName("핫 후라이드");
        menu.setMenuCategory(MenuCategory.CHICKEN);
        menu.setMenuImage("없음");
        menu.setPrice(19000);
        menu.setStock("Test");

        menuRepository.save(menu);

        MenuInfo menuInfo = new MenuInfo();
        menuInfo.setMenu(menu);
        menuInfo.setAllergy("우유 대두 밀 계란 닭고기 조개류(굴) 쇠고기 새우 땅콩");
        menuInfo.setOrigin("국내산");

        Nutrient nutrient = new Nutrient();
        nutrient.setFat(2.86f);
        nutrient.setKcal(253.73f);
        nutrient.setNa(408.19f);
        nutrient.setSugar(102.4f);
        nutrient.setProtein(18.77f);

        nutrientRepository.save(nutrient);
        menuInfoRepository.save(menuInfo);
        List<MenuInfo> infoList = menuInfoRepository.findAllByMenuId(menu.getId());
        assertFalse(infoList.isEmpty());
        MenuInfo menuList = infoList.get(0);

        assertEquals(menuList.getAllergy(),menuInfo.getAllergy());
        assertEquals(menuList.getOrigin(),menuInfo.getOrigin());

        MenuDetailDto menuDetailDto = new MenuDetailDto();
        menuDetailDto.setStore(store);
        menuDetailDto.setMenuName("핫 후라이드");
        menuDetailDto.setPrice(19000);
        menuDetailDto.getMenuInfo().add(menuInfo);
        menuDetailDto.getNutrient().add(nutrient);

        assertEquals(store,menuDetailDto.getStore());
        assertEquals(19000,menuDetailDto.getPrice());
        assertEquals("핫 후라이드",menuDetailDto.getMenuName());
        assertTrue(menuDetailDto.getMenuInfo().contains(menuInfo));
        assertTrue(menuDetailDto.getNutrient().contains(nutrient));

    }
}