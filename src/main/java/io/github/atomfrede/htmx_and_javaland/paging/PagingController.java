package io.github.atomfrede.htmx_and_javaland.paging;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import net.datafaker.Faker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.stream.IntStream;

@Controller
@RequestMapping("/paging")
public class PagingController {

    private final Faker faker = new Faker();
    private final LocationRepository repository;

    public PagingController(LocationRepository repository) {
        this.repository = repository;

        IntStream.range(1, 555).forEach(it -> {
            Location location = new Location();
            location.setId((long) it);
            location.setName(faker.name().fullName());
            repository.save(location);
        });

    }

    @GetMapping
    public String index(Model model, Pageable pageable) {

        Page<Location> page = repository.findAll(pageable);

        model.addAttribute("page", page);
        model.addAttribute("pager", PagerModel.fromPage(page, 3));
        return "paging/index";
    }

    @GetMapping
    @HxRequest
    public String table(Model model, Pageable pageable) {

        Page<Location> page = repository.findAll(pageable);

        model.addAttribute("page", page);
        model.addAttribute("pager", PagerModel.fromPage(page, 3));
        return "paging/item-table";
    }

}
