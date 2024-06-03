package br.com.jnsdev.task.repository;

import br.com.jnsdev.task.model.Task;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @Autor Jairo Nascimento
 * @Created 23/05/2024 - 16:14
 */
@Repository
public interface TaskRepository extends ReactiveMongoRepository<Task, String> {
}
