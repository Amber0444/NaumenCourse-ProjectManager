package ru.Alexander.NauJava.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.Alexander.NauJava.Service.ReportService;
import ru.Alexander.NauJava.model.Report;
import ru.Alexander.NauJava.model.ReportStatus;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportRestController {

    private final ReportService reportService;

    public ReportRestController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createReport() {
        Long reportId = reportService.createReport();

        reportService.generateReportAsync(reportId);

        Map<String, Object> response = new HashMap<>();
        response.put("reportId", reportId);
        response.put("message", "Отчет создан, формирование запущено в фоне.");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReport(@PathVariable Long id) {
        try {
            Report report = reportService.getReport(id);

            if (report.getStatus() == ReportStatus.CREATED) {
                return ResponseEntity.ok(Map.of(
                        "status", report.getStatus(),
                        "message", "Отчет еще формируется. Попробуйте позже."
                ));
            } else if (report.getStatus() == ReportStatus.ERROR) {
                return ResponseEntity.internalServerError().body(Map.of(
                        "status", report.getStatus(),
                        "message", report.getContent()
                ));
            }

            return ResponseEntity.ok()
                    .header("Content-Type", "text/html; charset=UTF-8")
                    .body(report.getContent());

        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
