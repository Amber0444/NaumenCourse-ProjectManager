package ru.Alexander.NauJava.Service;




import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.Alexander.NauJava.model.Comment;
import ru.Alexander.NauJava.model.Task;
import ru.Alexander.NauJava.repository.CommentRepository;
import ru.Alexander.NauJava.repository.TaskCriteriaRepository;
import ru.Alexander.NauJava.repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final CommentRepository commentRepository;
    private final TaskCriteriaRepository taskCriteriaRepository; // Из пункта 6

    public TaskServiceImpl(TaskRepository taskRepository,
                           CommentRepository commentRepository,
                           TaskCriteriaRepository taskCriteriaRepository) {
        this.taskRepository = taskRepository;
        this.commentRepository = commentRepository;
        this.taskCriteriaRepository = taskCriteriaRepository;
    }

    @Override
    public Task saveTask(Task task) {
        if (task.getCreatedAt() == null) {
            task.setCreatedAt(LocalDateTime.now());
        }
        return taskRepository.save(task);
    }

    @Override
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    @Override
    public Iterable<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public List<Task> getTasksByStatusAndAssignee(String status, String username) {
        return taskRepository.findByStatusAndAssigneeUsername(status, username);
    }

    @Override
    public List<Task> getTasksCreatedBetween(LocalDateTime start, LocalDateTime end) {
        return taskRepository.findByCreatedAtBetween(start, end);
    }

    @Override
    public List<Task> getTasksByProjectName(String projectName) {
        return taskRepository.findTasksByProjectName(projectName);
    }

    @Override
    public List<Task> findTasksByKeywordAndStatusCriteria(String keyword, String status) {
        return taskCriteriaRepository.findTasksByKeywordAndStatus(keyword, status);
    }

    @Override
    public List<Task> findTasksByTagNameCriteria(String tagName) {
        return taskCriteriaRepository.findTasksByTagName(tagName);
    }

    @Override
    @Transactional
    public void createTaskWithFirstComment(Task task, String commentText) {

        if (task.getCreatedAt() == null) {
            task.setCreatedAt(LocalDateTime.now());
        }
        Task savedTask = taskRepository.save(task);

        Comment comment = new Comment();
        comment.setText(commentText);
        comment.setCreatedDate(LocalDateTime.now());
        comment.setTask(savedTask);

        commentRepository.save(comment);

        if ("ERROR".equals(commentText)) {
            throw new RuntimeException("Ошибка при создании комментария! Транзакция должна откатиться, задача не сохранится.");
        }
    }
}
