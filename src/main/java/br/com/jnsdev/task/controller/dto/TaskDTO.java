package br.com.jnsdev.task.controller.dto;

import br.com.jnsdev.task.model.Address;
import br.com.jnsdev.task.model.Task;
import br.com.jnsdev.task.model.TaskState;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;

/**
 * @Autor Jairo Nascimento
 * @Created 22/05/2024 - 17:27
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskDTO {
    private String id;
    private String title;
    private String description;
    private int priority;
    private TaskState state;
    private Address address;
    private LocalDate created;

    public TaskDTO() {
    }
    public TaskDTO(Task task) {
        this.setId(task.getId());
        this.setTitle(task.getTitle());
        this.setDescription(task.getDescription());
        this.setPriority(task.getPriority());
        this.setState(task.getState());
        this.setAddress(task.getAddress());
        this.setCreated(task.getCreated());
    }


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

    public TaskState getState() {
        return state;
    }

    public void setState(TaskState state) {
        this.state = state;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }
}
