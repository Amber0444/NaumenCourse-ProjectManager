package ru.Alexander.NauJava.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Task {
    private Long id;
    private String title;
    private String description;
    private boolean isCompleted;

    public Task() {}

    public Task(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.isCompleted = false;
    }

    @Override
    public String toString() {
        return String.format("[ID: %d] %s - %s (Выполнено: %b)", id, title, description, isCompleted);
    }
}
