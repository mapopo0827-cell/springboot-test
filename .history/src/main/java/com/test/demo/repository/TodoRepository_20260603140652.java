package com.test.demo.repository;

import com.test.demo.entity.Todo;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * メモリ上でTODOを保持するリポジトリです。
 * 通常のDBリポジトリと同じようなインターフェースを提供します。
 */
@Repository
public class TodoRepository {
    private final Map<Long, Todo> store = new ConcurrentHashMap<>();
    private final AtomicLong nextId = new AtomicLong(1);

    public List<Todo> findAll() {
        return Collections.unmodifiableList(new ArrayList<>(store.values()));
    }

    public Optional<Todo> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public Todo save(Todo todo) {
        if (todo.getId() == null) {
            todo.setId(nextId.getAndIncrement());
        }
        store.put(todo.getId(), todo);
        return todo;
    }

    public void deleteById(Long id) {
        store.remove(id);
    }
}
