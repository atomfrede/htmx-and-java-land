package io.github.atomfrede.htmx_and_javaland.view_components;

import de.tschuehly.spring.viewcomponent.thymeleaf.ViewContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("view-components")
public class ViewComponentsController {

    IndexViewComponent indexViewComponent;

    public ViewComponentsController(IndexViewComponent indexViewComponent) {
        this.indexViewComponent = indexViewComponent;
    }

    @GetMapping
    public ViewContext index(Model model) {

        return indexViewComponent.render();
    }
}
