package br.com.jnsdev.task.controller.dto;

import br.com.jnsdev.task.model.Task;
import br.com.jnsdev.task.model.TaskState;

/**
 * @Autor Jairo Nascimento
 * @Created 22/05/2024 - 17:27
 */
public class TaskDTO {
    private String title;
    private String description;
    private int priority;
    private TaskState state;

    public TaskDTO() {
    }
    public TaskDTO(Task task) {
        this.setTitle(task.getTitle());
        this.setDescription(task.getDescription());
        this.setPriority(task.getPriority());
        this.setState(task.getState());
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

    public TaskState getState() {
        return state;
    }

    public void setState(TaskState state) {
        this.state = state;
    }
}
