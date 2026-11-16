package ru.Alexander.NauJava.controller;

import org.springframework.web.bind.annotation.*;
import ru.Alexander.NauJava.Service.TaskService;
import ru.Alexander.NauJava.model.Task;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskRestController {

    private final TaskService taskService;

    public TaskRestController(TaskService taskService) {
        this.taskService = taskService;
    }

    // 1. Кастомный поиск (Query Lookup Strategy)
    @GetMapping("/search/by-status-and-user")
    public List<Task> getTasksByStatusAndUser(
            @RequestParam String status,
            @RequestParam String username) {
        return taskService.getTasksByStatusAndAssignee(status, username);
    }

    // 2. Кастомный поиск (Criteria API)
    @GetMapping("/search/criteria")
    public List<Task> getTasksByCriteria(
            @RequestParam String keyword,
            @RequestParam String status) {
        return taskService.findTasksByKeywordAndStatusCriteria(keyword, status);
    }
}
