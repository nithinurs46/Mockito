package mockito.mock;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class HamcrestMatchersTest {

	@Test
	public void test() {
		List<Integer> scores = Arrays.asList(11, 2, 3, 4);
		assertThat(scores, hasSize(4));
		assertThat(scores, hasItem(3));

		assertThat(scores, everyItem(greaterThan(1)));

		// String
		assertThat("", isEmptyString());
		assertThat(null, isEmptyOrNullString());

		// Array
		Integer[] marks = { 1, 2, 3 };

		assertThat(marks, arrayWithSize(3));
		assertThat(marks, arrayContainingInAnyOrder(2, 3, 1));

	}

	@Test
	public void testMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("1", "ONE");
		map.put("2", "TWO");
		map.put("3", "THREE");

		assertThat(map, hasEntry("1", "ONE"));
	}

}
