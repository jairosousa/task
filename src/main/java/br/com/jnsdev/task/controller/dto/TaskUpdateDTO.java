package br.com.jnsdev.task.controller.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * @Autor Jairo Nascimento
 * @Created 03/06/2024 - 15:21
 */
public class TaskUpdateDTO {

    @NotBlank(message = "{black.id}")
    private String id;
    @NotBlank(message = "{black.title}")
    @Size(min = 3, max = 20, message = "{size.title}")
    private String title;
    @NotBlank(message = "{black.description}")
    @Size(min = 10, max = 50, message = "{size.description}")
    private String description;
    @Min(value = 1, message = "{min.priority}")
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
