package br.com.jnsdev.task.repository;

import br.com.jnsdev.task.model.Task;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.springframework.data.domain.Sort.by;

/**
 * @Autor Jairo Nascimento
 * @Created 23/05/2024 - 16:47
 */
@Repository
public class TaskCustomRepository {
    private final ReactiveMongoOperations reactiveMongoOperations;

    public TaskCustomRepository(ReactiveMongoOperations reactiveMongoOperations) {
        this.reactiveMongoOperations = reactiveMongoOperations;
    }

    public Mono<Page<Task>> findPaginated(Task task, Integer page, Integer size) {
        return queryExample(task)
                .zipWith(pageable(page, size))
                .flatMap(it-> execute(task, it.getT1(), it.getT2()));
    }

    private Mono<Page<Task>> execute(Task task, Example<Task> example, Pageable pageable) {
        return query(example, pageable, task)
                .flatMap(query -> reactiveMongoOperations.find(query, Task.class).collectList())
                .flatMap(tasks -> paginate(tasks, pageable, example));
    }

    private Mono<Page<Task>> paginate(List<Task> tasks, Pageable pageable, Example<Task> example) {
        return Mono.just(tasks)
                .flatMap(it -> reactiveMongoOperations.count(Query.query(Criteria.byExample(example)), Task.class))
                .map(counter -> PageableExecutionUtils.getPage(tasks, pageable, () -> counter));
    }

    private Mono<Pageable> pageable(int page, int size) {
        return Mono.just(PageRequest.of(page, size, by("title").ascending()));
    }

    private Mono<Example<Task>> queryExample(Task task) {
        return Mono.just(task)
                .map(it -> ExampleMatcher.matching().withIgnorePaths("priority", "state"))
                .map(matcher -> Example.of(task, matcher));
    }

    private Mono<Query> query(Example<Task> example, Pageable pageable, Task task) {
        return Mono.just(example)
                .map(ex -> Query.query(Criteria.byExample(example)).with(pageable))
                .flatMap(query -> validatePriority(query, task))
                .flatMap(query -> validateState(query, task));
    }

    private Mono<Query> validatePriority(Query query, Task task) {
        return Mono.just(task)
                .filter(Task::isValidaPriority)
                .map(it -> query.addCriteria(Criteria.where("priority").is(task.getPriority())))
                .defaultIfEmpty(query);
    }

    private Mono<Query> validateState(Query query, Task task) {
        return Mono.just(task)
                .filter(Task::isValidaState)
                .map(it -> query.addCriteria(Criteria.where("state").is(task.getState())))
                .defaultIfEmpty(query);
    }
}
