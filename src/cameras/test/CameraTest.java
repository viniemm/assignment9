package cameras.test;

import cameras.Camera;
import cameras.ScreenShot;
import org.junit.*;
import static org.junit.Assert.*;

public class CameraTest {
	@Test
	public void testCameraBuilding() {
		Camera sideCam = Camera.getBuilder()
				.setScreenShot(ScreenShot.of(
						new Boolean[][]{{true, false, false},
								{true, true, false},
								{true, true, true}})).setSide(true).build();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDegenCameraBuilding() {

		// The QA Team send their regards
		Camera degenCam = Camera.getBuilder()
				.setScreenShot(null)
				.setSide(null).build();
		Camera moreDegenCams = Camera.getBuilder().build();

	}

	@Test
	public void testCameraAddNormal() {
		Camera sideCam = Camera.getBuilder().setScreenShot(ScreenShot.of(
				new Boolean[][]{{true, false, false},
						{true, true, false},
						{true, true, true}})).setSide(true).build();
		try {
			sideCam.addData(ScreenShot.of(
					new Boolean[][]{{true, false, false},
							{true, true, false},
							{true, true, true}}));
			sideCam.addData(ScreenShot.of(
					new Boolean[][]{{true, true, false},
							{true, true, true},
							{false, false, false}}));
		}
		catch(Exception e) {
			fail("Error: New Screenshot be added smoothly!");
		}
	}

	@Test(expected = Camera.ChangeDetectedException.class)
	public void testCameraAddFail() throws Camera.ChangeDetectedException {
		Camera sideCam = Camera.getBuilder().setScreenShot(ScreenShot.of(
				new Boolean[][]{{true, false, false},
						{true, true, false},
						{true, true, true}})).setSide(true).build();
		sideCam.addData(ScreenShot.of(
				new Boolean[][]{{true, true, false},
						{true, true, false},
						{true, true, true}}));
		sideCam.addData(ScreenShot.of(
				new Boolean[][]{{true, true, false},
						{true, true, false},
						{true, true, true}}));
		fail("Error: New Screenshot should not be added smoothly!");
	}

}
