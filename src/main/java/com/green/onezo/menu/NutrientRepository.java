package com.green.onezo.menu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NutrientRepository extends JpaRepository<Nutrient,Long> {
     List<Nutrient> findAll();

     List<Nutrient> findAllByMenuId(@Param("menu_id") Long id);

}
