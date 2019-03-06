package com.example.lesson10.Interface;

import com.example.lesson10.Modules.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long>{
}
