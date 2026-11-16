package ru.Alexander.NauJava.Service;


import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.Alexander.NauJava.model.Report;
import ru.Alexander.NauJava.model.ReportStatus;
import ru.Alexander.NauJava.model.Task;
import ru.Alexander.NauJava.repository.ReportRepository;
import ru.Alexander.NauJava.repository.TaskRepository;
import ru.Alexander.NauJava.repository.UserRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public ReportService(ReportRepository reportRepository,
                         UserRepository userRepository,
                         TaskRepository taskRepository) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }


    public Long createReport() {
        Report report = new Report();
        report.setStatus(ReportStatus.CREATED);
        report.setContent("Отчет формируется...");
        return reportRepository.save(report).getId();
    }

    public Report getReport(Long id) {
        return reportRepository.findById(id).orElseThrow(() -> new RuntimeException("Отчет не найден"));
    }

    @Async
    public void generateReportAsync(Long reportId) {
        Report report = getReport(reportId);

        long totalStartTime = System.currentTimeMillis();

        try {
            AtomicLong userCalcTime = new AtomicLong(0);
            AtomicLong taskCalcTime = new AtomicLong(0);
            AtomicLong userCount = new AtomicLong(0);

            CompletableFuture<Void> usersFuture = CompletableFuture.runAsync(() -> {
                long start = System.currentTimeMillis();

                try { Thread.sleep(1000); } catch (InterruptedException e) {}

                userCount.set(userRepository.count());
                userCalcTime.set(System.currentTimeMillis() - start);
            });

            CompletableFuture<List<Task>> tasksFuture = CompletableFuture.supplyAsync(() -> {
                long start = System.currentTimeMillis();

                try { Thread.sleep(1500); } catch (InterruptedException e) {}

                List<Task> tasks = (List<Task>) taskRepository.findAll();
                taskCalcTime.set(System.currentTimeMillis() - start);
                return tasks;
            });

            CompletableFuture.allOf(usersFuture, tasksFuture).join();

            List<Task> tasks = tasksFuture.get();

            long totalElapsedTime = System.currentTimeMillis() - totalStartTime;

            String htmlContent = buildHtmlReport(
                    userCount.get(),
                    userCalcTime.get(),
                    tasks,
                    taskCalcTime.get(),
                    totalElapsedTime
            );

            report.setContent(htmlContent);
            report.setStatus(ReportStatus.FINISHED);
            reportRepository.save(report);

        } catch (Exception e) {
            report.setStatus(ReportStatus.ERROR);
            report.setContent("Ошибка при формировании отчета: " + e.getMessage());
            reportRepository.save(report);
        }
    }

    private String buildHtmlReport(long userCount, long userTime, List<Task> tasks, long taskTime, long totalTime) {
        StringBuilder html = new StringBuilder();
        html.append("<html><head><title>Отчет</title></head><body>");
        html.append("<h1>Статистика приложения</h1>");

        html.append("<h2>Сводка</h2>");
        html.append("<ul>");
        html.append("<li><b>Количество пользователей:</b> ").append(userCount).append("</li>");
        html.append("<li><b>Время подсчета пользователей:</b> ").append(userTime).append(" мс</li>");
        html.append("<li><b>Время получения списка задач:</b> ").append(taskTime).append(" мс</li>");
        html.append("<li><b>Общее время формирования отчета:</b> ").append(totalTime).append(" мс</li>");
        html.append("</ul>");

        html.append("<h2>Список задач</h2>");
        html.append("<table border='1'><tr><th>ID</th><th>Название</th><th>Статус</th></tr>");
        for (Task task : tasks) {
            html.append("<tr>")
                    .append("<td>").append(task.getId()).append("</td>")
                    .append("<td>").append(task.getTitle()).append("</td>")
                    .append("<td>").append(task.getStatus()).append("</td>")
                    .append("</tr>");
        }
        html.append("</table>");
        html.append("</body></html>");

        return html.toString();
    }
}