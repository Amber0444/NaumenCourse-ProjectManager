package ru.Alexander.NauJava;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.Alexander.NauJava.Service.TaskService;
import ru.Alexander.NauJava.model.Task;
import ru.Alexander.NauJava.repository.CommentRepository;
import ru.Alexander.NauJava.repository.TaskCriteriaRepository;
import ru.Alexander.NauJava.repository.TaskRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TaskIntegrationTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TaskCriteriaRepository taskCriteriaRepository;

    @Autowired
    private TaskService taskService;

    @AfterEach
    void tearDown() {
        commentRepository.deleteAll();
        taskRepository.deleteAll();
    }

    @Test
    void testCriteriaApi() {
        Task task1 = new Task();
        task1.setTitle("Написать тесты");
        task1.setStatus("TODO");
        taskRepository.save(task1);

        Task task2 = new Task();
        task2.setTitle("Написать код");
        task2.setStatus("IN_PROGRESS");
        taskRepository.save(task2);

        List<Task> found = taskCriteriaRepository.findTasksByKeywordAndStatus("написать", "TODO");
        assertEquals(1, found.size());
        assertEquals("Написать тесты", found.get(0).getTitle());
    }

    @Test
    void testTransactionalRollback() {
        Task task = new Task();
        task.setTitle("Тестовая задача");
        task.setStatus("TODO");

        assertDoesNotThrow(() -> taskService.createTaskWithFirstComment(task, "Нормальный комментарий"));

        long countAfterSuccess = taskRepository.count();
        assertEquals(1, countAfterSuccess);

        Task task2 = new Task();
        task2.setTitle("Задача с ошибкой");

        assertThrows(RuntimeException.class, () -> {
            taskService.createTaskWithFirstComment(task2, "ERROR");
        });

        long countAfterFail = taskRepository.count();
        assertEquals(countAfterSuccess, countAfterFail, "Количество задач не должно измениться из-за отката транзакции");
    }
}