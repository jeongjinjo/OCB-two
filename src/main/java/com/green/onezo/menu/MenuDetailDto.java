package com.green.onezo.menu;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.green.onezo.menu.Menu;
import com.green.onezo.menu.MenuInfo;
import com.green.onezo.menu.Nutrient;
import com.green.onezo.store.Store;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MenuDetailDto {

    private Long id;
    private Store store;
    private int price;
    private String menuName;
    private List<MenuInfo> menuInfo = new ArrayList<>();
    private List<Nutrient> nutrient = new ArrayList<>();


}
