package com.example.lesson10.Modules;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @NotNull
  @Size(min = 4)
  @Column(unique = true)
  private String foodCategory;

  @OneToMany(mappedBy = "category")
  public Set<Food> Food;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getFoodCategory() {
    return foodCategory;
  }

  public void setFoodCategory(String foodCategory) {
    this.foodCategory = foodCategory;
  }

  public Set<Food> getFood() {
    return Food;
  }

  public void setFood(Set<Food> food) {
    Food = food;
  }
}
