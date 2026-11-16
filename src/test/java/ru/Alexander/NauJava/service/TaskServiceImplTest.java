package ru.Alexander.NauJava.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.Alexander.NauJava.Service.TaskServiceImpl;
import ru.Alexander.NauJava.model.Task;
import ru.Alexander.NauJava.repository.CommentRepository;
import ru.Alexander.NauJava.repository.TaskCriteriaRepository;
import ru.Alexander.NauJava.repository.TaskRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private TaskCriteriaRepository taskCriteriaRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    void saveTask_Positive() {
        Task task = new Task();
        task.setTitle("Новая задача");

        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task saved = taskService.saveTask(task);

        assertNotNull(saved);
        assertEquals("Новая задача", saved.getTitle());
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void createTaskWithFirstComment_Negative_ThrowsException() {
        Task task = new Task();
        task.setTitle("Задача для отката");

        when(taskRepository.save(any(Task.class))).thenReturn(task);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            taskService.createTaskWithFirstComment(task, "ERROR");
        });

        assertTrue(exception.getMessage().contains("Ошибка при создании комментария"));
        verify(commentRepository, times(1)).save(any());
    }
}
