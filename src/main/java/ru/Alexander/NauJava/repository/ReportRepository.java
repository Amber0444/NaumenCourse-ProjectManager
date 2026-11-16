package ru.Alexander.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.Alexander.NauJava.model.Report;

@Repository
public interface ReportRepository extends CrudRepository<Report, Long> {
}
