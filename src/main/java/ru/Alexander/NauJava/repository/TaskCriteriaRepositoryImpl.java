package ru.Alexander.NauJava.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;
import ru.Alexander.NauJava.model.Tag;
import ru.Alexander.NauJava.model.Task;

import java.util.List;

@Repository
public class TaskCriteriaRepositoryImpl implements TaskCriteriaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Task> findTasksByKeywordAndStatus(String keyword, String status) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Task> query = cb.createQuery(Task.class);
        Root<Task> root = query.from(Task.class);

        Predicate keywordPredicate = cb.like(cb.lower(root.get("title")), "%" + keyword.toLowerCase() + "%");
        Predicate statusPredicate = cb.equal(root.get("status"), status);

        query.select(root).where(cb.and(keywordPredicate, statusPredicate));
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Task> findTasksByTagName(String tagName) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Task> query = cb.createQuery(Task.class);
        Root<Task> root = query.from(Task.class);

        Join<Task, Tag> tagsJoin = root.join("tags");

        query.select(root).where(cb.equal(tagsJoin.get("name"), tagName)).distinct(true);
        return entityManager.createQuery(query).getResultList();
    }
}
