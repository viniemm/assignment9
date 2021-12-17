/*
 * An example to demonstrate the testing of private methods by means of an inner class.
 */
package cameras;

/* import javax.annotation.Generated;
 * does not work with Cobertura due to annotation argument.
 * Use CoverageIgnore instead.
 */

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * A class under test
 */
public class ScreenShot {

	/*
	Define the rows in this 2D array as the pixels of given by the camera.
	Oh boy really hope I didn't mix row with col
	 */
	private final Boolean[][] pixels;

	private ScreenShot(Boolean[][] pixels) {
		this.pixels = pixels;
	}

	public Boolean[][] getPixels() {
		return pixels;
	}

	public static ScreenShot removeFloat(ScreenShot s) {
		Boolean[][] newPixels = Arrays.stream(s.getPixels())
				.map(col -> {
			long countTrue = Arrays.stream(col).takeWhile(pix -> pix).count();
			return IntStream.range(0, col.length).mapToObj(i -> i > countTrue).collect(Collectors.toList())
					.toArray(Boolean[]::new);
		})
				.collect(Collectors.toList()).toArray(Boolean[][]::new);
		return of(newPixels);
	}

	public static boolean isShifted(ScreenShot before, ScreenShot after) {
		int rowCount = before.getPixels().length;
		int colCount = before.getPixels()[0].length;

		for(int i = -colCount+1; i < colCount; i++) {
			for(int j = -rowCount+1; j < rowCount; j++) {
				if(after == (shiftColBy(shiftRowBy(before, i), j))) {
					return true;
				}
			}
		}
		return false;
	}

	public static ScreenShot shiftRowBy(ScreenShot screenShot, int k) {
		return of(IntStream.range(0, screenShot.getPixels().length)
				.mapToObj(i -> {
					if(i - k > 0 && i - k < screenShot.getPixels().length) {
						return Arrays.copyOf(screenShot.getPixels()[i - k], screenShot.getPixels()[i - k].length);
					}
					else {
						return IntStream.range(0, screenShot.getPixels()[0].length)
								.mapToObj(j -> false)
								.toArray(Boolean[]::new);
					}
				}).toArray(Boolean[][]::new));
	}

	public static ScreenShot shiftColBy(ScreenShot screenShot, int k) {
		return of(
				Arrays.stream(screenShot.getPixels())
						.map(row -> IntStream.range(0, row.length)
								.mapToObj(i -> {
									if(i - k > 0 && i - k < row.length) {
										return row[i - k];
									}
									else return false;
								}).toArray(Boolean[]::new))
						.toArray(Boolean[][]::new));
	}

	private static void validate(ScreenShot s){
		Boolean[][] pixels = s.pixels;

		Objects.requireNonNull(pixels);

		Arrays.stream(pixels).forEach(col -> Objects.requireNonNull(col, "Null col found in array!"));

		int rowLength = Arrays.stream(pixels).findAny().orElse(new Boolean[]{}).length;

		assert Arrays.stream(pixels).allMatch(col -> col.length == rowLength) : "Cols are not of same length!";
	}

	public static ScreenShot of(Boolean[][] pixels) {
		ScreenShot newScreenShot = new ScreenShot(pixels);
		try {
			validate(newScreenShot);
			return newScreenShot;
		} catch(Exception e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	@Override
	public boolean equals(Object other) {
		if(other instanceof ScreenShot) {
			return Arrays.deepEquals(this.getPixels(), ((ScreenShot) other).getPixels());
		}
		else return false;
	}
}
