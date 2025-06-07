package tenant;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class TodoService {

    TodoRepository repository;

    @Inject
    public TodoService(TodoRepository repository) {
        this.repository = repository;
    }

    public List<Todo> getTodosForCurrentUser() {
        return repository.listAll();
    }

    public Optional<Todo> getTodoByIdForCurrentUser(Long id) {
        return repository.findByIdOptional(id);
    }

    @Transactional
    public Todo createTodoForCurrentUser(Todo todoData) {
        Todo todo = new Todo();
        todo.title = todoData.title;
        todo.completed = todoData.completed;
        todo.tenantId = null; // Let Hibernate fill this
        repository.persist(todo);
        return todo;
    }

    @Transactional
    public Optional<Todo> updateTodoForCurrentUser(Long id, Todo data) {
        return repository.findByIdOptional(id)
                .map(existing -> {
                    existing.title = data.title;
                    existing.completed = data.completed;
                    repository.persist(existing);
                    return existing;
                });
    }

    @Transactional
    public boolean deleteTodoForCurrentUser(Long id) {
        return repository.deleteById(id);
    }
}
