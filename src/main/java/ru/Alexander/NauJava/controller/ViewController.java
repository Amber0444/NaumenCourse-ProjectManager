package ru.Alexander.NauJava.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.Alexander.NauJava.Service.TaskService;

@Controller
public class ViewController {

    private final TaskService taskService;

    public ViewController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/web/tasks")
    public String showTasksPage(Model model) {
        // Передаем список задач в HTML шаблон
        model.addAttribute("tasks", taskService.getAllTasks());
        return "tasks"; // Имя файла шаблона (tasks.html)
    }
}
