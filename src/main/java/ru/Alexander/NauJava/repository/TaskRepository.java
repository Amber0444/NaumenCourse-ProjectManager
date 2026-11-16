package ru.Alexander.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import ru.Alexander.NauJava.model.Task;

import java.time.LocalDateTime;
import java.util.List;


@Repository
@RepositoryRestResource(collectionResourceRel = "tasks", path = "tasks")
public interface TaskRepository extends CrudRepository<Task, Long> {

    List<Task> findByStatusAndAssigneeUsername(String status, String username);

    List<Task> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT t FROM Task t WHERE t.project.name = :projectName")
    List<Task> findTasksByProjectName(@Param("projectName") String projectName);
}
