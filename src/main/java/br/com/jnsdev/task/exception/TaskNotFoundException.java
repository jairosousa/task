package br.com.jnsdev.task.exception;

/**
 * @Autor Jairo Nascimento
 * @Created 03/06/2024 - 16:28
 */
public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException() {
        super("Task not found");
    }
}
