package ru.Alexander.NauJava.repository;

import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.Alexander.NauJava.model.Task;

import java.util.ArrayList;
import java.util.List;

@Component
public class InMemoryDatabase {
    @Getter
    private final List<Task> tasks = new ArrayList<>();
    private Long nextId = 1L;

    public Long generateId() {
        return nextId++;
    }
}
