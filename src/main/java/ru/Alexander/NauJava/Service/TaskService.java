package ru.Alexander.NauJava.Service;

import ru.Alexander.NauJava.model.Task;
import java.util.List;

public interface TaskService {
    Task createTask(String title, String description);
    List<Task> getAllTasks();
    void markAsCompleted(Long id);
    void deleteTask(Long id);
    List<Task> searchByTitle(String keyword);
}
