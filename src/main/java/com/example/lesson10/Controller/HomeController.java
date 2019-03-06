package com.example.lesson10.Controller;

import com.cloudinary.utils.ObjectUtils;
import com.example.lesson10.Interface.CategoryRepository;
import com.example.lesson10.Interface.CustomerRepository;
import com.example.lesson10.Interface.FoodRepository;
import com.example.lesson10.Modules.Category;
import com.example.lesson10.Modules.CloudinaryConfig;
import com.example.lesson10.Modules.Customer;
import com.example.lesson10.Modules.Food;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    FoodRepository foodRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CloudinaryConfig cloudc;

    @Autowired
    CustomerRepository customerRepository;

    @RequestMapping("/")
    public String list(Model model){
        model.addAttribute("foods", foodRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        return "list";
    }

    @GetMapping("/add")
    public String foodForm(Model model){
        model.addAttribute("food", new Food());
        model.addAttribute("categories", categoryRepository.findAll());
        return "foodForm";
    }

    @PostMapping("/process")
    public String processForm(@Valid Food food, BindingResult result,
                              Model model, @RequestParam("file") MultipartFile file ){
        if(result.hasErrors()){
            model.addAttribute("categories", categoryRepository.findAll());
            return "foodForm";
        }
        if(file.isEmpty()){
            return "redirect:/add";
        }
//        foodRepository.save(food);
        try{
            Map uploadResult = cloudc.upload(file.getBytes(),
                    ObjectUtils.asMap("resourceType","auto"));
            food.setImage(uploadResult.get("url").toString());
            foodRepository.save(food);
        }catch(IOException e){
            e.printStackTrace();
            return "redirect:/add";
        }
        foodRepository.save(food);
        return "redirect:/";
    }
    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/addCategories")
    public String subjectForm(Model model){
        model.addAttribute("category", new Category());
        return "category";
    }

    @PostMapping("/processCategory")
    public String processSubject(@Valid Category category, BindingResult result,
                                 Model model){
        if(result.hasErrors()){
            return "category";
        }
//        if(categoryRepository.findByCategory(category.getCategory()) != null){
//            model.addAttribute("message", "You already have a subject called " +
//                    category.getCategory() + "!" + " Try something else.");
//            return "category";
//        }
        categoryRepository.save(category);
        return "redirect:/";
    }

    @RequestMapping("/addQuantity")
    public String customerForm(Model model){
        model.addAttribute("customer", new Customer());
        model.addAttribute("foods",foodRepository.findAll());
        return "customerForm";
    }

    @PostMapping("/processQuantity")
    public String processingCustomer(Model model, @ModelAttribute Customer customer,@ModelAttribute Food food){

//        Customer cust_to_update = customerRepository.findById(customer.getId()).get();
//        cust_to_update.setQuantity(customer.getQuantity());
//        customerRepository.save(cust_to_update);


//
//
////
//        customerRepository.save(customer);
//        foodRepository.save(food);
        String item=customer.getFood().getName();
        model.addAttribute("item",item);
       double price = food.getPrice();
       model.addAttribute("price",price);
       double quantity = customer.getQuantity();
       model.addAttribute("quantity",quantity);
       double cost = price * quantity;
       model.addAttribute("cost",cost);

       double subtotal = 0.0;
       subtotal +=cost;
        model.addAttribute("subtotal",subtotal);
       int tax=6;
       Double salesTax= 0.06*subtotal;
        model.addAttribute("salesTax", salesTax);

       Double total = salesTax+subtotal;
        model.addAttribute("total",total);


       return "Receipt";
    }


    @RequestMapping("/update/{id}")
    public String updateFood(@PathVariable("id") long id, Model model){
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("food", foodRepository.findById(id).get());
        return "foodform";
    }

    @RequestMapping("/delete/{id}")
    public String delfood(@PathVariable("id") long id){
        foodRepository.deleteById(id);
        return "redirect:/";
    }

}









