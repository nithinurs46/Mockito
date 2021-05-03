package mockito.stub;

import java.util.Arrays;
import java.util.List;

import mockito.svc.TodoService;

//A stub class is created to provide values for the TodoService,
//TodoService might call external APIs to get the data, for testing business class, service needs to provide the data,
//hence a stub is created to provide the data.
//disadvantage with stub is, if there is any new method added to interface, then we need to create that method in stub as 
//well since we are implementing the svc interface
//We are taking user as parameter here, to filter data for a user it requires logic to be written.
public class TodoServiceStub implements TodoService {

	public List<String> retrieveTodos(String user) {
		return Arrays.asList("Learn Spring MVC", "Learn Spring", "Learn Core Java");
	}

	public void deleteTodo(String todo) {

	}

}
