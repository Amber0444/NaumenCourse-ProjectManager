package ru.Alexander.NauJava.Console;


import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.Alexander.NauJava.Service.TaskService;

import java.util.Scanner;

@Component
public class ConsoleApplication implements CommandLineRunner {

    private final TaskService taskService;

    public ConsoleApplication(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("Добро пожаловать в Менеджер Задач");

        while (running) {
            System.out.println("\n1. Показать все задачи");
            System.out.println("2. Добавить задачу");
            System.out.println("3. Отметить задачу как выполненную");
            System.out.println("4. Удалить задачу");
            System.out.println("5. Поиск задач по названию");
            System.out.println("0. Выход");
            System.out.print("Выберите действие: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    taskService.getAllTasks().forEach(System.out::println);
                    break;
                case "2":
                    System.out.print("Введите название: ");
                    String title = scanner.nextLine();
                    System.out.print("Введите описание: ");
                    String desc = scanner.nextLine();
                    taskService.createTask(title, desc);
                    System.out.println("Задача добавлена!");
                    break;
                case "3":
                    System.out.print("Введите ID задачи: ");
                    try {
                        taskService.markAsCompleted(Long.parseLong(scanner.nextLine()));
                        System.out.println("Статус обновлен.");
                    } catch (NumberFormatException e) {
                        System.out.println("Ошибка: ID должен быть числом.");
                    }
                    break;
                case "4":
                    System.out.print("Введите ID задачи для удаления: ");
                    try {
                        taskService.deleteTask(Long.parseLong(scanner.nextLine()));
                        System.out.println("Задача удалена.");
                    } catch (NumberFormatException e) {
                        System.out.println("Ошибка: ID должен быть числом.");
                    }
                    break;
                case "5":
                    System.out.print("Введите часть названия: ");
                    String keyword = scanner.nextLine();
                    taskService.searchByTitle(keyword).forEach(System.out::println);
                    break;
                case "0":
                    running = false;
                    System.out.println("Выход...");
                    break;
                default:
                    System.out.println("Неверный ввод, попробуйте снова.");
            }
        }
    }
}
