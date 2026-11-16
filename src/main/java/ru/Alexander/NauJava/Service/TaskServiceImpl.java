package ru.Alexander.NauJava.Service;


import org.springframework.stereotype.Service;
import ru.Alexander.NauJava.model.Task;
import ru.Alexander.NauJava.repository.TaskRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository repository;

    public TaskServiceImpl(TaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public Task createTask(String title, String description) {
        Task task = new Task(null, title, description);
        return repository.save(task);
    }

    @Override
    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    @Override
    public void markAsCompleted(Long id) {
        repository.findById(id).ifPresent(task -> {
            task.setCompleted(true);
            repository.update(task);
        });
    }

    @Override
    public void deleteTask(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Task> searchByTitle(String keyword) {
        return repository.findAll().stream()
                .filter(t -> t.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }
}
