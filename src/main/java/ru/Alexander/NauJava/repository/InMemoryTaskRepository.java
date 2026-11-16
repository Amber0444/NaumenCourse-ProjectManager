package ru.Alexander.NauJava.repository;

import org.springframework.stereotype.Repository;
import ru.Alexander.NauJava.model.Task;

import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryTaskRepository implements TaskRepository {

    private final InMemoryDatabase database;

    public InMemoryTaskRepository(InMemoryDatabase database) {
        this.database = database;
    }

    @Override
    public Task save(Task task) {
        task.setId(database.generateId());
        database.getTasks().add(task);
        return task;
    }

    @Override
    public List<Task> findAll() {
        return database.getTasks();
    }

    @Override
    public Optional<Task> findById(Long id) {
        return database.getTasks().stream()
                .filter(task -> task.getId().equals(id))
                .findFirst();
    }

    @Override
    public void update(Task updatedTask) {
        findById(updatedTask.getId()).ifPresent(existingTask -> {
            existingTask.setTitle(updatedTask.getTitle());
            existingTask.setDescription(updatedTask.getDescription());
            existingTask.setCompleted(updatedTask.isCompleted());
        });
    }

    @Override
    public void deleteById(Long id) {
        database.getTasks().removeIf(task -> task.getId().equals(id));
    }
}
