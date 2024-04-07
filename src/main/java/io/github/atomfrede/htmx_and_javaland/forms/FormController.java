package io.github.atomfrede.htmx_and_javaland.forms;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/forms")
public class FormController {

    @GetMapping
    public String index(Model model) {
        model.addAttribute("item", new ItemFormData());
        return "forms/form";
    }

    @PostMapping
    public String submitForm(@Valid @ModelAttribute("item") ItemFormData formData, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "forms/form";
        }
        return "forms/form";

    }

    @PostMapping
    @HxRequest
    public String submitInlineForm(@Valid @ModelAttribute("item") ItemFormData formData, BindingResult bindingResult, Model model) {

        return "forms/fragments :: form";
    }
}
