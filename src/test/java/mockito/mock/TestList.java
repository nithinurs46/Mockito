package mockito.mock;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;
import static org.mockito.BDDMockito.given;

public class TestList {

	@Test
	public void testListSize() {
		// creat a mock of list
		List<String> listMock = mock(List.class);
		// when size method is called, return 4
		when(listMock.size()).thenReturn(4);
		// test it with assert
		assertEquals(4, listMock.size());

	}

	@Test
	public void testListSizeWithMultipleReturn() {
		List<String> listMock = mock(List.class);
		// when size method is called, first return 4, then 6, then 17
		when(listMock.size()).thenReturn(4).thenReturn(6).thenReturn(17);
		assertEquals(4, listMock.size());
		assertEquals(6, listMock.size());
		assertEquals(17, listMock.size());

	}

	@Test
	public void testGetListValue() {
		// creat a mock of list
		List<String> listMock = mock(List.class);
		when(listMock.get(0)).thenReturn("AB");
		assertEquals("AB", listMock.get(0));
		// null will be returned here, since get(1) is not defined
		assertEquals(null, listMock.get(1));

	}

	@Test
	public void testGetListAny() {
		List<String> listMock = mock(List.class);
		// Argument Matcher is used with when, if get of list is called with any index,
		// then AB is returned
		when(listMock.get(anyInt())).thenReturn("AB");
		assertEquals("AB", listMock.get(17));

	}

	@Test
	public void testGetListAny_BDD() {
		List<String> listMock = mock(List.class);
		// Argument Matcher is used with when, if get of list is called with any index,
		// then AB is returned
		// given
		given(listMock.get(anyInt())).willReturn("AB");

		// when
		String someElement = listMock.get(4);

		// then
		assertThat(someElement, is("AB"));

	}

	@Test(expected = ArrayIndexOutOfBoundsException.class)
	public void testListForException() {
		List<String> listMock = mock(List.class);
		when(listMock.get(10)).thenThrow(ArrayIndexOutOfBoundsException.class);
		assertEquals("AB", listMock.get(10));

	}

}
