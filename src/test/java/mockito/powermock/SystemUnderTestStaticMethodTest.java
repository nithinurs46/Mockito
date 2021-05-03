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

//With Mockito, we cannot test static methods, hence we make use of powermock
//we need to use powermock runner only.
//call @PrepareForTest and PowerMockito.mockStatic with the class where static method is present

@RunWith(PowerMockRunner.class)
@PrepareForTest(UtilityClass.class)
public class SystemUnderTestStaticMethodTest {

	@Mock
	Dependency dependencyMock;

	@InjectMocks
	SystemUnderTest systemUnderTest;

	@Captor
	ArgumentCaptor<String> argumentCaptor;

	@Test
	public void testStaticMethodCall() {
		List<Integer> stats = Arrays.asList(1, 2, 3);
		when(dependencyMock.retrieveAllStats()).thenReturn(stats);
		PowerMockito.mockStatic(UtilityClass.class);
		when(UtilityClass.staticMethod(6)).thenReturn(150);
		int result = systemUnderTest.methodCallingAStaticMethod();
		assertEquals(150, result);

		PowerMockito.verifyStatic();
		UtilityClass.staticMethod(6);

	}

}
