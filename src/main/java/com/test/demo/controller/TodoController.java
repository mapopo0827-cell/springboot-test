package com.test.demo.controller;

import com.test.demo.dto.TodoDto;
import com.test.demo.service.TodoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Reactフロントエンドから呼ばれるREST APIを提供するコントローラです。
 */
@RestController
@RequestMapping("/api/todos")
@CrossOrigin(origins = "http://localhost:5173")
public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public List<TodoDto> getAllTodos() {
        return todoService.getAllTodos();
    }

    @PostMapping
    public TodoDto createTodo(@RequestBody TodoDto todoDto) {
        return todoService.createTodo(todoDto);
    }

    @PutMapping("/{id}")
    public TodoDto updateTodo(@PathVariable Long id, @RequestBody TodoDto todoDto) {
        return todoService.updateTodo(id, todoDto);
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
    }
}
