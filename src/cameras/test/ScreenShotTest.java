package cameras.test;

import cameras.ScreenShot;
import org.junit.*;
import static org.junit.Assert.*;

public class ScreenShotTest {
	@Test
	public void testShiftColBy() {
		ScreenShot s = ScreenShot.of(
				new Boolean[][]{{true, true, true},
						{true, false, false},
						{true, true, false}});
		assertEquals(ScreenShot.of(
				new Boolean[][]{{false, true, true},
						{false, true, false},
						{false, true, true}}), ScreenShot.shiftColBy(s, 1));
	}

	@Test
	public void testShiftRowBy() {
		ScreenShot s = ScreenShot.of(
				new Boolean[][]{{true, true, true},
						{true, false, false},
						{true, true, false}});
		assertEquals(ScreenShot.of(
				new Boolean[][]{{false, false, false},
						{true, true, true},
						{true, false, false}}), ScreenShot.shiftRowBy(s, 1));
	}

	@Test
	public void testIsShifted() {
		assertTrue("Error: Should be True", ScreenShot.isShifted(
				ScreenShot.of(new Boolean[][]{{true, false, false, false, false},
						{true, true, true, false, false},
						{true, true, true, true, false}}),
				ScreenShot.of(new Boolean[][]{{false, false, false, false, true},
						{false, false, false, false, true},
						{false, false, false, false, true}})));
		assertFalse("Error: Should be False", ScreenShot.isShifted(
				ScreenShot.of(new Boolean[][]{{true, false, false},
						{true, true, false},
						{true, true, true}}),
				ScreenShot.of(new Boolean[][]{{true, true, false},
						{true, true, true},
						{false, false, true}})));
	}

	@Test
	public void testRemoveFloat() {
		ScreenShot s = ScreenShot.of(new Boolean[][]{{true, false, true},
				{true, true, false},
				{false, false, false}});
		assertEquals(ScreenShot.of(new Boolean[][]{{true, false, false},
				{true, true, false},
				{false, false, false}}), ScreenShot.removeFloat(s));
	}
}
