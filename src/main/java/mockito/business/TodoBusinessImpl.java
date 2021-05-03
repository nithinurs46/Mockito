package mockito.business;

import java.util.ArrayList;
import java.util.List;

import mockito.svc.TodoService;

//TodoBusinessImpl -> SUT (System Under Test)
//TodoService -> Dependency
public class TodoBusinessImpl {

	private TodoService todoService;

	public TodoBusinessImpl(TodoService todoService) {
		this.todoService = todoService;
	}

	public List<String> retrieveTodosRelatedToKeyword(String user, String keyword) {
		List<String> filteredTodos = new ArrayList<String>();
		List<String> allTodos = todoService.retrieveTodos(user);
		for (String todo : allTodos) {
			if (todo.contains(keyword)) {
				filteredTodos.add(todo);
			}
		}
		return filteredTodos;
	}

	public void deleteTodosRelatedToKeyword(String user, String keyword) {
		List<String> allTodos = todoService.retrieveTodos(user);
		for (String todo : allTodos) {
			if (!todo.contains(keyword)) {
				todoService.deleteTodo(todo);
			}
		}
	}
}