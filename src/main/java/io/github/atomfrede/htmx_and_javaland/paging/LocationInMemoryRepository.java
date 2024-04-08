package io.github.atomfrede.htmx_and_javaland.paging;

import io.github.atomfrede.htmx_and_javaland.todo.TodoItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class LocationInMemoryRepository implements LocationRepository {
    private Map<Long, Location> locations = new HashMap<>();

    @Override
    public Iterable<Location> findAll(Sort sort) {
        return locations.values().stream().sorted(Comparator.comparing(Location::getName)).toList();
    }

    @Override
    public Page<Location> findAll(Pageable pageable) {
        if (pageable.getSort().getOrderFor("name").isAscending()) {
            List<Location> items = locations.values().stream()
                    .skip(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .sorted(Comparator.comparing(Location::getName).reversed())
                    .toList();
            return new PageImpl<>(items, pageable, locations.size());
        } else {
            List<Location> items = locations.values().stream()
                    .skip(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .sorted(Comparator.comparing(Location::getName))
                    .toList();
            return new PageImpl<>(items, pageable, locations.size());
        }

    }

    @Override
    public void save(Location location) {
        locations.putIfAbsent(location.getId(), location);
    }
}

