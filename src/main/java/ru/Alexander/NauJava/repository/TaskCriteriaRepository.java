package ru.Alexander.NauJava.repository;

import ru.Alexander.NauJava.model.Task;
import java.util.List;

public interface TaskCriteriaRepository {
    List<Task> findTasksByKeywordAndStatus(String keyword, String status);
    List<Task> findTasksByTagName(String tagName);
}
