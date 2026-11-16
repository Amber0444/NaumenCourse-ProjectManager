package ru.Alexander.NauJava.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.Alexander.NauJava.model.Tag;

@Repository
public interface TagRepository extends CrudRepository<Tag, Long> {
}
