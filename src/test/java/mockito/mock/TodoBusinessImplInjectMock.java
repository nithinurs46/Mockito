package mockito.mock;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import mockito.business.TodoBusinessImpl;
import mockito.svc.TodoService;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.runners.MockitoJUnitRunner;

//@RunWith(MockitoJUnitRunner.class)
public class TodoBusinessImplInjectMock {
// BDD - Behavior Driven Development. It follows scenario based approach in  given, then, when order.
//Mocking is creating objects that simulates the behavior of real world objects

// Instead of creating todoSvcMock object in every method by calling mock(), we can make use of @Mock Annotation
//Instead of creating todoBusinessImpl object in every method, we can make use of @InjectMocks, 
//	which will look into the constructor of the class and create an object on its own by injecting the mock object	
	
	@Mock
	TodoService todoSvcMock;

	@InjectMocks
	TodoBusinessImpl todoBusinessImpl;
	
	@Captor
	ArgumentCaptor<String> argumentCaptor;

	//If we are using @RunWith, it binds the test runner to only that specific runner.
	//Incase if we need to use multiple runners like Mockito and Spring, then it would not be possible,
	//To overcome this, @Rule is introduced. We can create multiple rule and remove @RunWith annotation
	//Rule should be public, else exception will be thrown.
	
	@Rule
	public MockitoRule mockitoJRule = MockitoJUnit.rule();
	
	@Test
	public void testRetreiveTodoWithKeyword_checkSize() {
		List<String> todos = Arrays.asList("Learn Spring MVC", "Learn Spring", "Learn to Dance");

		// Given
		given(todoSvcMock.retrieveTodos("User1")).willReturn(todos);

		// when
		todos = todoBusinessImpl.retrieveTodosRelatedToKeyword("User1", "Spring");

		// then
		assertThat(todos.size(), is(2));
	}

	@Test
	public void testRetreiveTodoWithKeyword_checkValidData() {

		List<String> todos = Arrays.asList("Learn Spring MVC", "Learn Spring", "Learn to Dance");
		// Given
		given(todoSvcMock.retrieveTodos("User1")).willReturn(todos);

		// when
		todos = todoBusinessImpl.retrieveTodosRelatedToKeyword("User1", "Spring");

		// then
		assertThat(todos.get(0), is("Learn Spring MVC"));
	}

	@Test
	public void testRetreiveTodoWithKeyword_checkInValidData() {
		List<String> todos = Arrays.asList("Learn Spring MVC", "Learn Spring", "Learn Core Java");

		// Given
		given(todoSvcMock.retrieveTodos("User1")).willReturn(todos);

		// when
		todos = todoBusinessImpl.retrieveTodosRelatedToKeyword("User1", "Learn C++");

		// then
		assertThat(todos.contains("Learn C++"), is(false));
	}

	@Test
	public void testDeleteTodo() {
		List<String> todos = Arrays.asList("Learn Spring MVC", "Learn Spring", "Learn Core Java");

		// Given
		given(todoSvcMock.retrieveTodos("User1")).willReturn(todos);

		// when --> remove element from list if it doesn't contain keyword as Spring
		todoBusinessImpl.deleteTodosRelatedToKeyword("User1", "Spring");

		// then --> pass value which doesn't have keyword spring and see if delete svc
		// method is called once
		verify(todoSvcMock, times(1)).deleteTodo("Learn Core Java");
		verify(todoSvcMock).deleteTodo("Learn Core Java"); // verify if svc is called
		verify(todoSvcMock, atLeastOnce()).deleteTodo("Learn Core Java"); // verify if svc is called
		verify(todoSvcMock, atLeast(1)).deleteTodo("Learn Core Java"); // verify if svc is called

		then(todoSvcMock).should().deleteTodo("Learn Core Java");

		// then --> pass value which as keyword spring and see if delete svc method is
		// never called
		verify(todoSvcMock, times(0)).deleteTodo("Learn Spring");
		verify(todoSvcMock, never()).deleteTodo("Learn Spring");
		then(todoSvcMock).should(never()).deleteTodo("Learn Spring");
	}

	@Test
	public void captureArgument() {
		ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

		List<String> todos = Arrays.asList("Learn Spring MVC", "Learn Spring", "Learn Core Java");
		Mockito.when(todoSvcMock.retrieveTodos("User1")).thenReturn(todos);

		todoBusinessImpl.deleteTodosRelatedToKeyword("User1", "Spring");
		Mockito.verify(todoSvcMock).deleteTodo(argumentCaptor.capture());
		assertEquals("Learn Core Java", argumentCaptor.getValue());
	}

	@Test
	public void captureArgument_MultipleValues() {
		List<String> todos = Arrays.asList("Learn C++", "Learn Spring", "Learn Core Java");
		Mockito.when(todoSvcMock.retrieveTodos("User1")).thenReturn(todos);

		todoBusinessImpl.deleteTodosRelatedToKeyword("User1", "Spring");
		then(todoSvcMock).should(times(2)).deleteTodo(argumentCaptor.capture());
		assertThat(argumentCaptor.getAllValues().size(), is(2));
	}
}
