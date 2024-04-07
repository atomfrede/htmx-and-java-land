package io.github.atomfrede.htmx_and_javaland.todo;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxResponse;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/todo-mvc")
public class TodoController {

    private TodoItemRepository repository;

    public TodoController(TodoItemRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public String index(Model model) {
        addAttributesForIndex(model, ListFilter.ALL);
        return "todo/index";
    }

    @PostMapping
    public String addNewTodoItem(@Valid @ModelAttribute("item") TodoItemFormData formData) {
        repository.save(new TodoItem(repository.nextId(), formData.getTitle(), false));

        return "redirect:/todo-mvc";
    }

    @PostMapping
    @HxRequest
    public HtmxResponse htmxAddTodoItem(TodoItemFormData formData,
                                        Model model) {
        TodoItem item = repository.save(new TodoItem(repository.nextId(), formData.getTitle(), false));
        return getHtmxResponseForTodoItem(model, item);
    }

    @PutMapping("/{id}/toggle")
    @HxRequest
    public HtmxResponse htmxToggleTodoItem(@PathVariable("id") Long id,
                                           Model model) {
        TodoItem todoItem = repository.findById(id)
                .orElseThrow(() -> new TodoItemNotFoundException(id));

        todoItem.setCompleted(!todoItem.isCompleted());
        repository.save(todoItem);

        return getHtmxResponseForTodoItem(model, todoItem);
    }

    @DeleteMapping("/{id}")
    @HxRequest
    public HtmxResponse htmxDeleteTodoItem(@PathVariable("id") Long id,
                                           Model model) {
        repository.deleteById(id);

        return activeItemsCountFragment(model);
    }

    @GetMapping("/active-items-count")
    @HxRequest
    public String htmxActiveItemsCount(Model model) {
        model.addAttribute("numberOfActiveItems", getNumberOfActiveItems());

        return "todo/fragments :: active-items-count";
    }

    private HtmxResponse getHtmxResponseForTodoItem(Model model,
                                                    TodoItem item) {
        model.addAttribute("item", toDto(item));

        return HtmxResponse.builder()
                .view("todo/fragments :: todoItem")
                .and(activeItemsCountFragment(model))
                .build();
    }


    @PutMapping("/{id}/toggle")
    public String toggleSelection(@PathVariable("id") Long id) {
        TodoItem todoItem = repository.findById(id)
                .orElseThrow(() -> new TodoItemNotFoundException(id));

        todoItem.setCompleted(!todoItem.isCompleted());
        repository.save(todoItem);
        return "redirect:/todo-mvc";
    }

    @PutMapping("/toggle-all")
    public String toggleAll() {
        Iterable<TodoItem> todoItems = repository.findAll();
        for (TodoItem todoItem : todoItems) {
            todoItem.setCompleted(!todoItem.isCompleted());
            repository.save(todoItem);
        }
        return "redirect:/todo-mvc";
    }

    @DeleteMapping("/{id}")
    public String deleteTodoItem(@PathVariable("id") Long id) {
        repository.deleteById(id);

        return "redirect:/todo-mvc";
    }

    @DeleteMapping("/completed")
    public String deleteCompletedItems() {
        List<TodoItem> items = repository.findAllByCompleted(true);
        for (TodoItem item : items) {
            repository.deleteById(item.getId());
        }
        return "redirect:/todo-mvc";
    }

    private void addAttributesForIndex(Model model,
                                       ListFilter listFilter) {
        model.addAttribute("item", new TodoItemFormData());
        model.addAttribute("filter", listFilter);
        model.addAttribute("todos", getTodoItems(listFilter));
        model.addAttribute("totalNumberOfItems", repository.count());
        model.addAttribute("numberOfActiveItems", getNumberOfActiveItems());
        model.addAttribute("numberOfCompletedItems", getNumberOfCompletedItems());
    }

    private HtmxResponse activeItemsCountFragment(Model model) {
        model.addAttribute("numberOfActiveItems", getNumberOfActiveItems());
        return HtmxResponse.builder()
                .view("todo/fragments :: active-items-count")
                .build();
    }


    private int getNumberOfActiveItems() {
        return repository.countAllByCompleted(false);
    }

    private int getNumberOfCompletedItems() {
        return repository.countAllByCompleted(true);
    }

    private List<TodoItemDto> getTodoItems(ListFilter filter) {

        return switch (filter) {
            case ALL -> convertToDto(repository.findAll());
            case ACTIVE -> convertToDto(repository.findAllByCompleted(false));
            case COMPLETED -> convertToDto(repository.findAllByCompleted(true));
        };
    }

    private List<TodoItemDto> convertToDto(Iterable<TodoItem> todoItems) {
        List<TodoItemDto> results = new ArrayList<>();
        todoItems.forEach(it -> results.add(toDto(it)));
        return results;
    }

    private TodoItemDto toDto(TodoItem todoItem) {
        return new TodoItemDto(todoItem.getId(),
                todoItem.getTitle(),
                todoItem.isCompleted());
    }

    public static record TodoItemDto(long id, String title, boolean completed) {
    }

    public enum ListFilter {
        ALL,
        ACTIVE,
        COMPLETED
    }
}


