package com.example.tacocloud.tacos;

import com.example.tacocloud.ingredient.Ingredient;
import com.example.tacocloud.ingredient.IngredientRepository;
import com.example.tacocloud.order.TacoOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import jakarta.validation.Valid;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.example.tacocloud.ingredient.Ingredient.Type;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {

    private final IngredientRepository ingredientRepo;

    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepo){
        this.ingredientRepo = ingredientRepo;
    }

    @ModelAttribute
    public void addIngredientsToModel(Model model){
        Iterable<Ingredient> ingredientsIterable = this.ingredientRepo.findAll();

        List<Ingredient> ingredients = StreamSupport
                .stream(ingredientsIterable.spliterator(), false)
                .collect(Collectors.toList());

        Type[] types = Ingredient.Type.values();
        for(Type type: types){
            model.addAttribute(type.toString().toLowerCase(),filterByType(ingredients,type));
        }
    }

    @ModelAttribute(name="tacoOrder")
    public TacoOrder order(){
        return new TacoOrder();
    }

    @ModelAttribute(name="taco")
    public Taco taco(){
        return new Taco();
    }

    // helper

    private Iterable<Ingredient> filterByType(List<Ingredient> ingredients, Type type){
        return ingredients
                .stream()
                .filter(ingredientType -> ingredientType.getType().equals(type))
                .collect(Collectors.toList());
    }

    // requests

    @GetMapping
    public String showDesignForm(Model model){
        model.addAttribute("welcomeText","Welcome");
        model.addAttribute("greeting","Hola Amigos!");
        return "design";
    }

    @PostMapping
    public String processTaco(@Valid Taco taco, Errors errors, @ModelAttribute TacoOrder tacoOrder){

        if(errors.hasErrors()){
            return "design";
        }

        tacoOrder.addTaco(taco);
        log.info("Processing taco: {}", taco);
        return "redirect:/orders/current";
    }

}
