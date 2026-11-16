package ru.Alexander.NauJava.Service;

import ru.Alexander.NauJava.model.Task;
import java.util.List;
import java.time.LocalDateTime;
import java.util.Optional;

public interface TaskService {

    Task saveTask(Task task);
    Optional<Task> getTaskById(Long id);
    Iterable<Task> getAllTasks();
    void deleteTask(Long id);

    List<Task> getTasksByStatusAndAssignee(String status, String username);

    List<Task> getTasksCreatedBetween(LocalDateTime start, LocalDateTime end);

    List<Task> getTasksByProjectName(String projectName);

    List<Task> findTasksByKeywordAndStatusCriteria(String keyword, String status);
    List<Task> findTasksByTagNameCriteria(String tagName);

    void createTaskWithFirstComment(Task task, String commentText);
}
