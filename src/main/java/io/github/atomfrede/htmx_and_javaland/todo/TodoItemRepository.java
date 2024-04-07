package io.github.atomfrede.htmx_and_javaland.todo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;


@NoRepositoryBean
public interface TodoItemRepository extends CrudRepository<TodoItem, Long> {

    long nextId();
    int countAllByCompleted(boolean completed);

    List<TodoItem> findAllByCompleted(boolean completed);
}
