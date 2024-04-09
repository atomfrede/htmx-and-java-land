package io.github.atomfrede.htmx_and_javaland.endless_scrolling;

import io.github.atomfrede.htmx_and_javaland.paging.Location;
import io.github.atomfrede.htmx_and_javaland.paging.LocationRepository;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import net.datafaker.Faker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.stream.IntStream;

@Controller
@RequestMapping("endless-scrolling")
public class EndlessScrollingController {

    private final Faker faker = new Faker();
    private final LocationRepository repository;

    public EndlessScrollingController(LocationRepository repository) {
        this.repository = repository;

        IntStream.range(1000, 1555).forEach(it -> {
            Location location = new Location();
            location.setId((long) it);
            location.setName(faker.animal().name());
            repository.save(location);
        });
    }

    @GetMapping
    public String index(Model model, Pageable pageable) {

        Slice<Location> slice = repository.findAll(pageable);
        model.addAttribute("slice", slice);
        model.addAttribute("items", slice.getContent());
        return "endless-scrolling/index";
    }

    @GetMapping
    @HxRequest
    public String items(Model model, Pageable pageable) {
        Slice<Location> slice = repository.findAll(pageable);
        model.addAttribute("slice", slice);
        model.addAttribute("items", slice.getContent());

        return "endless-scrolling/table :: list";
    }
}
