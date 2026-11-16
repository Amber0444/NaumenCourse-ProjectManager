package ru.Alexander.NauJava.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.Alexander.NauJava.model.Project;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {
}
