package cameras;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class CameraSet {

	private final Camera frontCam;
	private final Camera sideCam;
	private final Camera topCam;

	private CameraSet(Camera frontCam, Camera sideCam, Camera topCam) {
		this.frontCam = frontCam;
		this.sideCam = sideCam;
		this.topCam = topCam;
	}

	public Camera getFrontCam() {
		return frontCam;
	}

	public Camera getSideCam() {
		return sideCam;
	}

	public Camera getTopCam() {
		return topCam;
	}

	public void addData(ScreenShot shot1, ScreenShot shot2, ScreenShot shot3) {
		try {
			getSideCam().addData(shot1);
			getFrontCam().addData(shot2);
			getTopCam().addData(shot3);
		} catch (Camera.ChangeDetectedException e) {
			throw new IllegalArgumentException(e);
		}

	}

	public static Builder getBuilder() {
		return new Builder();
	}

	public static final class Builder {

		private int[][] packages;
		private int height;

		public Builder setHeight(int height) {
			this.height = height;
			return this;
		}

		public Builder setPackages(int[][] packages) {
			this.packages = packages;
			return this;
		}

		private CameraSet validate(CameraSet camSet) {
			// Why need validation smh QA
			// Validation added
			assert (height > 0);
			camSet.frontCam.getData().forEach(sc->sc.validate());
			camSet.sideCam.getData().forEach(sc->sc.validate());
			camSet.topCam.getData().forEach(sc->sc.validate());
//			Arrays.stream(packages).forEach(col -> Arrays.stream(col).forEach(box -> {
//				if (box > height || box < 0) {
//					System.out.println(box);
//					throw new IllegalArgumentException();
//				}
//			}));
			return camSet;
		}

		public CameraSet build() {

			// Minor changes for front. i should be < count and not <= because indexing starts at 0.
			// IntStream.range should be inclusive of packages.length.
			ScreenShot front = ScreenShot.of(IntStream.range(0, packages.length + 1)
				.map(i -> Arrays.stream(packages)
					.mapToInt(row -> row[i])
					.max()
					.orElse(0))
				.mapToObj(count -> IntStream.range(0, height).mapToObj(i -> i < count).toArray(Boolean[]::new))
				.toArray(Boolean[][]::new));

			// Minor changes for side. i should be < count and not <= because indexing starts at 0.
			ScreenShot side = ScreenShot.of((Arrays.stream(packages)
				.mapToInt(arr -> Arrays.stream(arr).max().orElse(0)))
				.mapToObj(count -> IntStream.range(0, height).mapToObj(i -> i < count).toArray(Boolean[]::new))
				.toArray(Boolean[][]::new));

			ScreenShot top = ScreenShot.of(Arrays.stream(packages)
				.map(row -> Arrays.stream(row).mapToObj(i -> i > 0).toArray(Boolean[]::new))
				.toArray(Boolean[][]::new));

			// side and top wer flipped
			return validate(new CameraSet(Camera.getBuilder().setScreenShot(front).setSide(true).build(),
				Camera.getBuilder().setScreenShot(side).setSide(true).build(),
				Camera.getBuilder().setScreenShot(top).setSide(false).build()));
		}
	}

}
