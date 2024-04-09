package io.github.atomfrede.htmx_and_javaland.view_components;

import de.tschuehly.spring.viewcomponent.core.action.GetViewAction;
import de.tschuehly.spring.viewcomponent.core.component.ViewComponent;
import de.tschuehly.spring.viewcomponent.thymeleaf.ActionViewContext;
import de.tschuehly.spring.viewcomponent.thymeleaf.ViewContext;

@ViewComponent
public class HtmxCounterViewComponent {

    Integer counter = 0;

    public record ActionView(Integer counter) implements ActionViewContext {
    }

    public ViewContext render() {
        return new ActionView(counter);
    }

    @GetViewAction
    public ViewContext countUp() {
        counter += 1;
        return render();
    }
}
