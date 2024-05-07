package com.green.onezo.menu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    List<Menu> findAll();

    Optional<Menu> findById(Long id);

    Optional<Menu> findByMenuImage(String menuImage);
}

