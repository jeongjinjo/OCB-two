package com.green.onezo.menu;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final MenuInfoRepository menuInfoRepository;
    private final NutrientRepository nutrientRepository;


    public List<Menu> menuList(){

        List<Menu> menus = menuRepository.findAll();
        return menus;
    }

    public MenuDetailDto getAllMenuDetails(Long menuId,Long menuInfoId) {
        ModelMapper modelMapper = new ModelMapper();

        Optional<Menu> menuOpt = menuRepository.findById(menuId);
        if (menuOpt.isPresent()) {
            Menu menu = menuOpt.get();

            List<MenuInfo> menuInfos = menuInfoRepository.findAllByMenuId(menuId);
            List<Nutrient> nutrients = nutrientRepository.findAllByMenuInfoId(menuInfoId);

            MenuDetailDto menuDetailDto = modelMapper.map(menu, MenuDetailDto.class);

            menuDetailDto.getMenuInfo().addAll(menuInfos);
            menuDetailDto.getNutrient().addAll(nutrients);

            return menuDetailDto;
        }
        return null;
    }

//    public List<MenuStatus> status(){
//        return status();
//    }

    }
