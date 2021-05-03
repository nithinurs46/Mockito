package mockito.powermock;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

//With Mockito, we cannot test private methods, hence we make use of powermock

@RunWith(PowerMockRunner.class)
public class SystemUnderTestPrivateMethodTest {

	@Mock
	Dependency dependencyMock;

	@InjectMocks
	SystemUnderTest systemUnderTest;

	@Captor
	ArgumentCaptor<String> argumentCaptor;

	@Test
	public void testPrivateMethodCall() throws Exception {
		List<Integer> stats = Arrays.asList(1, 2, 3);
		when(dependencyMock.retrieveAllStats()).thenReturn(stats);
		//make use of below line to call private method
		long result = Whitebox.invokeMethod(systemUnderTest, "privateMethodUnderTest");
		assertEquals(6, result);

	}

}
