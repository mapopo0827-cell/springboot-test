package com.test.demo.service;

import com.test.demo.dto.TodoDto;
import com.test.demo.entity.Todo;
import com.test.demo.repository.TodoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * TODOに関するビジネスロジックを担当するサービスクラスです。
 */
@Service
public class TodoService {
    private final TodoRepository repository;

    public TodoService() {
        this.repository = new TodoRepository();
    }

    public List<TodoDto> getAllTodos() {
        return repository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public TodoDto createTodo(TodoDto todoDto) {
        Todo todo = new Todo();
        todo.setTitle(todoDto.getTitle());
        todo.setCompleted(false);
        return toDto(repository.save(todo));
    }

    public TodoDto updateTodo(Long id, TodoDto todoDto) {
        Todo todo = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Todo not found"));
        todo.setTitle(todoDto.getTitle());
        todo.setCompleted(todoDto.isCompleted());
        return toDto(repository.save(todo));
    }

    public void deleteTodo(Long id) {
        if (repository.findById(id).isEmpty()) {
            throw new ResponseStatusException(NOT_FOUND, "Todo not found");
        }
        repository.deleteById(id);
    }

    private TodoDto toDto(Todo todo) {
        return new TodoDto(todo.getId(), todo.getTitle(), todo.isCompleted());
    }
}
