package br.com.jnsdev.task.controller.dto;

/**
 * @Autor Jairo Nascimento
 * @Created 03/06/2024 - 15:21
 */
public class TaskUpdateDTO {

    private String id;
    private String title;
    private String description;
    private int priority;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
