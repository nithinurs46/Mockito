package mockito.powermock;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.stub;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
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

//Testing constructor call with mockito an powermock
/*
 * To be able to mock the Constructor, we need to add in the Class that creates
 * the new object in PrepareForTest
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ SystemUnderTest.class })
public class SystemUnderTestConstructorTest {

	@InjectMocks
	SystemUnderTest systemUnderTest;

	private static final int SOME_DUMMY_SIZE = 100;

	@Test
	public void testConstructorCall() throws Exception {
		ArrayList<String> mockList = mock(ArrayList.class);

		stub(mockList.size()).toReturn(SOME_DUMMY_SIZE);

		PowerMockito.whenNew(ArrayList.class).withAnyArguments().thenReturn(mockList);

		int size = systemUnderTest.methodUsingAnArrayListConstructor();

		assertEquals(SOME_DUMMY_SIZE, size);

	}

}
