package br.com.jnsdev.task.model;

import org.springframework.data.annotation.Id;

import java.time.LocalDate;

/**
 * @Autor Jairo Nascimento
 * @Created 22/05/2024 - 15:59
 */
public class Task {
    @Id
    private String id;
    private String title;
    private String description;
    private int priority;
    private TaskState state;
    private Address address;
    private LocalDate created;

    public Task() {
    }

    public Task(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.description = builder.description;
        this.priority = builder.priority;
        this.state = builder.state;
        this.address = builder.address;
        this.created = builder.created;
    }

    public Task insert() {
        return builderFrom(this)
                .state(TaskState.INSERT)
                .created(LocalDate.now())
                .build();
    }

    public Task update(Task oldTask) {
        return builderFrom(this)
                .state(oldTask.getState())
                .build();
    }

    public Task updateAddress(Address address) {
        return Task.builderFrom(this)
                .address(address)
                .build();
    }

    public Task start() {
        return builderFrom(this)
                .state(TaskState.DOING)
                .build();
    }

    public Task done() {
        return builderFrom(this)
                .state(TaskState.DONE)
                .build();
    }

    public boolean createdIsEmpty() {
        return this.created == null;
    }

    public boolean isValidaState() {
        return this.state != null;
    }

    public boolean isValidaPriority() {
        return this.priority > 0;
    }

    public Task createdNow() {
        return Task.builderFrom(this)
                .created(LocalDate.now())
                .build();
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    public TaskState getState() {
        return state;
    }

    public Address getAddress() {
        return address;
    }

    public LocalDate getCreated() {
        return created;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builderFrom(Task task) {
        return new Builder(task);
    }

    public static class Builder {
        private String id;
        private String title;
        private String description;
        private int priority;
        private TaskState state;
        private Address address;
        private LocalDate created;

        public Builder() {
        }

        public Builder(Task task) {
            this.id = task.id;
            this.title = task.title;
            this.description = task.description;
            this.priority = task.priority;
            this.state = task.state;
            this.address = task.address;
            this.created = task.created;
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder priority(int priority) {
            this.priority = priority;
            return this;
        }

        public Builder state(TaskState state) {
            this.state = state;
            return this;
        }

        public Builder address(Address address) {
            this.address = address;
            return this;
        }

        public Builder created(LocalDate created) {
            this.created = created;
            return this;
        }

        public Task build() {
            return new Task(this);
        }
    }
}
