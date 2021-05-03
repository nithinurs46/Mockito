package mockito.mock;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import mockito.svc.TodoService;

public class TodoBusinessImplMockTest {

//Mocking is creating objects that simulates the behavior of real world objects
	
	@Test
	public void testRetreiveTodoWithKeyword_checkSize() {
		// create a mock of svc class instead of a stub
		TodoService todoSvc = mock(TodoService.class);
		List<String> todos = new ArrayList<String>();
		// when method in svc class is called, then return a pre-built list
		when(todoSvc.retrieveTodos("User1")).thenReturn(todos);
		// test using assert
		assertEquals(0, todos.size());
	}

	@Test
	public void testRetreiveTodoWithKeyword_checkValidData() {
		TodoService todoSvc = mock(TodoService.class);
		List<String> todos = Arrays.asList("Learn Spring MVC", "Learn Spring", "Learn Core Java");
		when(todoSvc.retrieveTodos("User1")).thenReturn(todos);
		assertEquals(true, todos.contains("Learn Spring"));
	}

	@Test
	public void testRetreiveTodoWithKeyword_checkInValidData() {
		TodoService todoSvc = mock(TodoService.class);
		List<String> todos = Arrays.asList("Learn Spring MVC", "Learn Spring", "Learn Core Java");
		when(todoSvc.retrieveTodos("User1")).thenReturn(todos);
		assertEquals(false, todos.contains("Learn C++"));
	}
}
