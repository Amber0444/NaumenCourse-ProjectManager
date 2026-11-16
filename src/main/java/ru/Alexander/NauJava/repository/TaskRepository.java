package ru.Alexander.NauJava.repository;

import ru.Alexander.NauJava.model.Task;
import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    Task save(Task task);
    List<Task> findAll();
    Optional<Task> findById(Long id);
    void update(Task task);
    void deleteById(Long id);
}
