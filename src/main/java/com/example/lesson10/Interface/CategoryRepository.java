package com.example.lesson10.Interface;

import com.example.lesson10.Modules.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
//  Category findByCategory(String subject_name);
}
