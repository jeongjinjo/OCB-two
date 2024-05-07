package com.green.onezo.menu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MenuInfoRepository extends JpaRepository<MenuInfo,Long> {
    Optional<MenuInfo> findById(Long id);
    List<MenuInfo> findAllByMenuId(@Param("menu_id") Long id);

}
