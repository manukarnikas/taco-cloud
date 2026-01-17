package com.example.tacocloud.tacos;

import com.example.tacocloud.ingredient.Ingredient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.example.tacocloud.ingredient.Ingredient.Type;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {

    @ModelAttribute
    public void addIngredientsToModel(Model model){
        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("FLTO","Flour Tortilla",Type.WRAP),
                new Ingredient("COTO","Corn Tortilla", Type.WRAP),
                new Ingredient("GRBF", "Ground Beef",Type.PROTEIN),
                new Ingredient("CARN","Carnitas",Type.PROTEIN),
                new Ingredient("TMTO","Diced Tomatoes",Type.VEGGIES),
                new Ingredient("LETC","lettuce",Type.VEGGIES),
                new Ingredient("CHED","Cheddar",Type.CHEESE),
                new Ingredient("PARM","Parmesan",Type.CHEESE),
                new Ingredient("SLSA","Salsa",Type.SAUCE),
                new Ingredient("SRCR","Sour Cream",Type.SAUCE)
        );

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
    public String processTaco(Taco taco, @ModelAttribute TacoOrder tacoOrder){
        tacoOrder.addTaco(taco);
        log.info("Processing taco: {}", taco);
        return "redirect:/orders/current";
    }

}
