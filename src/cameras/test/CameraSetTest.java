package cameras.test;

import cameras.Camera;
import cameras.CameraSet;
import cameras.ScreenShot;
import org.junit.*;
import static org.junit.Assert.*;

public class CameraSetTest {

	private CameraSet newSet;
	private Camera newFront;
	private Camera newSide;
	private Camera newTop;

	@Before
	public void init() {
		newSet = CameraSet.getBuilder()
				.setPackages(new int[][]{{1, 2, 3, 4}, {0, 4, 1, 5}, {1, 2, 0, 1}}).setHeight(5).build();
		ScreenShot frontShot = ScreenShot.of(
				new Boolean[][]{{true, false, false, false, false},
						{true, true, true, true, false},
						{true, true, true, false, false},
						{true, true, true, true, true}}
		);
		ScreenShot sideShot = ScreenShot.of(
				new Boolean[][]{{true, true, true, true, false},
						{true, true, true, true, true},
						{true, true, false, false, false}}
		);
		ScreenShot topShot = ScreenShot.of(
				new Boolean[][]{{true, true, true, true},
						{false, true, true, true},
						{true, true, false, true}}
		);
		newFront = Camera.getBuilder().setScreenShot(frontShot).setSide(true).build();
		newSide = Camera.getBuilder().setScreenShot(sideShot).setSide(true).build();
		newTop = Camera.getBuilder().setScreenShot(topShot).setSide(false).build();
	}

	@Test
	public void testCameraSetFront() {
		assertEquals(newSet.getFrontCam(), newFront);
	}

	@Test
	public void testCameraSetSide() {
		assertEquals(newSet.getSideCam(), newSide);
	}

	@Test
	public void testCameraSetTop() {
		assertEquals(newSet.getTopCam(), newTop);
	}

	@Test
	public void testCameraAddNoChange() {
		newSet.addData(ScreenShot.of(
				new Boolean[][]{{true, false, false, false, false},
						{true, true, true, true, false},
						{true, true, true, false, false},
						{true, true, true, true, true}}
		), ScreenShot.of(
				new Boolean[][]{{true, true, true, true, false},
						{true, true, true, true, true},
						{true, true, false, false, false}}
		), ScreenShot.of(
				new Boolean[][]{{true, true, true, true},
						{false, true, true, true},
						{true, true, false, true}}
		));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCameraAddWrong() {
		newSet.addData(ScreenShot.of(
				new Boolean[][]{{true, false, false, false, false},
						{true, true, true, true, false},
						{true, true, true, false, false},
						{true, true, true, true, true}}
		), ScreenShot.of(
				new Boolean[][]{{true, true, true, true, false},
						{true, true, true, true, true},
						{true, true, false, false, false}}
		), ScreenShot.of(
				new Boolean[][]{{true, true, true, false},
						{false, true, true, true},
						{true, true, false, true}}
		));
		newSet.addData(ScreenShot.of(
				new Boolean[][]{{true, false, false, false, false},
						{true, true, true, true, false},
						{true, true, true, false, false},
						{true, true, true, true, true}}
		), ScreenShot.of(
				new Boolean[][]{{true, true, true, true, false},
						{true, true, true, true, true},
						{true, true, false, false, false}}
		), ScreenShot.of(
				new Boolean[][]{{true, true, true, false},
						{false, true, true, true},
						{true, true, false, true}}
		));
	}
}