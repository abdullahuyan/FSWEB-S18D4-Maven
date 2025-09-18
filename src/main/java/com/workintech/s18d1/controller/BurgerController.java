package com.workintech.s18d1.controller;

import com.workintech.s18d1.dao.BurgerDao;
import com.workintech.s18d1.entity.BreadType;
import com.workintech.s18d1.entity.Burger;
import com.workintech.s18d1.exceptions.BurgerException;
import com.workintech.s18d1.util.BurgerValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/burger")
public class BurgerController {
    private  final BurgerDao burgerDao;

    @Autowired
    public BurgerController(BurgerDao burgerDao) {
        this.burgerDao = burgerDao;
    }

    @PostMapping
    public Burger save(@RequestBody Burger burger){
        BurgerValidation.checkName(burger.getName());
        return burgerDao.save(burger);
    }

    @GetMapping
    public List<Burger> findAll(){
        return burgerDao.findAll();
    }

    @GetMapping("/{id}")
    public Burger find(@PathVariable long id){
        return burgerDao.findById(id);
    }

    @PutMapping
    public  Burger update(@RequestBody Burger burger){
        BurgerValidation.checkName(burger.getName());
        return burgerDao.update(burger);
    }

    @DeleteMapping("/{id}")
    public Burger delete(@PathVariable long id){
        return burgerDao.remove(id);
    }

    @GetMapping("/breadType/{breadType}")
    @ResponseStatus(HttpStatus.OK)
    public List<Burger> getBurgerByBreadType(@PathVariable BreadType breadType) {
        return burgerDao.findByBreadType(breadType);
    }

    @GetMapping("/content/{content}")
    @ResponseStatus(HttpStatus.OK)
    public List<Burger> getBurgerContent(@PathVariable("content") String content) {
        return burgerDao.findByContent(content);
    }

    @GetMapping("/price/{price}")
    @ResponseStatus(HttpStatus.OK)
    public List<Burger> getBurgerByPrice(@PathVariable Integer price) {
        if (price <= 0) {
            throw new BurgerException("Price değeri 0 ve negatif olamaz. Price: " + price, HttpStatus.BAD_REQUEST);
        }
        return burgerDao.findByPrice(price);
    }

}
