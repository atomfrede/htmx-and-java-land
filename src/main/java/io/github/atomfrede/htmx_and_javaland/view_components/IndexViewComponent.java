package io.github.atomfrede.htmx_and_javaland.view_components;

import de.tschuehly.spring.viewcomponent.core.component.ViewComponent;
import de.tschuehly.spring.viewcomponent.thymeleaf.ViewContext;

@ViewComponent
public class IndexViewComponent {

    public ViewContext render() {
        return new IndexView();
    }

    public record IndexView() implements ViewContext {
    }
}
