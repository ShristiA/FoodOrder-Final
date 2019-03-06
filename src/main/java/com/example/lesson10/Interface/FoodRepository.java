package com.example.lesson10.Interface;

import com.example.lesson10.Modules.Food;
import org.springframework.data.repository.CrudRepository;

public interface FoodRepository extends CrudRepository<Food, Long> {

}
