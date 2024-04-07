package io.github.atomfrede.htmx_and_javaland;

import org.springframework.web.bind.annotation.GetMapping;

public class IndexController {

    @GetMapping("/")
    public String index() {
        return "index";
    }
}
