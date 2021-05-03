package mockito.business;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import mockito.stub.TodoServiceStub;
import mockito.svc.TodoService;

public class TodoBusinessImplStubTest {

	@Test
	public void testRetreiveTodoWithKeyword_checkSize() {
		TodoService todoSvc = new TodoServiceStub();
		TodoBusinessImpl todoImpl = new TodoBusinessImpl(todoSvc);
		List<String> todos = todoImpl.retrieveTodosRelatedToKeyword("User1", "Spring");
		assertEquals(2, todos.size());
	}

	@Test
	public void testRetreiveTodoWithKeyword_checkValidData() {
		TodoService todoSvc = new TodoServiceStub();
		TodoBusinessImpl todoImpl = new TodoBusinessImpl(todoSvc);
		List<String> todos = todoImpl.retrieveTodosRelatedToKeyword("User1", "Spring");
		assertEquals(true, todos.contains("Learn Spring"));
	}

	@Test
	public void testRetreiveTodoWithKeyword_checkInValidData() {
		TodoService todoSvc = new TodoServiceStub();
		TodoBusinessImpl todoImpl = new TodoBusinessImpl(todoSvc);
		List<String> todos = todoImpl.retrieveTodosRelatedToKeyword("User1", "Spring");
		assertEquals(false, todos.contains("Learn C++"));
	}

}
