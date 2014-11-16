package sg.edu.ntu.sce;

import java.util.Arrays;

public class Interpolate {

	public static ImageInfo run(ImageInfo image) { 

		// read the image
                int ratio = 2;

                int MAX_ROW_COUNT = image.MAX_ROW_COUNT;
                int MAX_COL_COUNT = image.MAX_COL_COUNT;

		// new image data
		int newRowCount = MAX_ROW_COUNT * ratio;
		int newColCount = MAX_COL_COUNT * ratio;

		long[][] interpolatedRed = new long[newRowCount][newColCount];
                long[][] interpolatedGreen = new long[newRowCount][newColCount];
                long[][] interpolatedBlue = new long[newRowCount][newColCount];

		// modify the header of the 
                ImageInfo returnImage = new ImageInfo();
		returnImage.MAX_ROW_COUNT = newRowCount;
		returnImage.MAX_COL_COUNT = newColCount;
                returnImage.header = Arrays.copyOf(image.header,image.header.length);
                returnImage.red = interpolatedRed;
                returnImage.green = interpolatedGreen;
                returnImage.blue = interpolatedBlue;

                byte[] header = returnImage.header;
                header[18] = (byte) (newColCount & 0x000000FF);
                header[19] = (byte) ((newColCount >> 8) & 0x000000FF);
                header[20] = (byte) ((newColCount >> 16) & 0x000000FF);
                header[21] = (byte) ((newColCount >> 24) & 0x000000FF);
                header[22] = (byte) (newRowCount & 0x000000FF);
                header[23] = (byte) ((newRowCount >> 8) & 0x000000FF);
                header[24] = (byte) ((newRowCount >> 16) & 0x000000FF);
                header[25] = (byte) ((newRowCount >> 24) & 0x000000FF);

		for (int rowCounter = MAX_ROW_COUNT - 1; rowCounter >= 0; rowCounter -= 1) {
			for (int colCounter = MAX_COL_COUNT - 1; colCounter >= 0; colCounter -= 1) {

				long redSample, greenSample, blueSample, intensitySample, yellowSample;
				redSample = image.red[rowCounter][colCounter];
				greenSample = image.green[rowCounter][colCounter];
				blueSample = image.blue[rowCounter][colCounter];

				// interpolated along the row and column
				for (int rowInterpolate = 0; rowInterpolate < ratio; rowInterpolate++) {
					for (int colInterpolate = 0; colInterpolate < ratio; colInterpolate++) {
						interpolatedRed[rowCounter * ratio + rowInterpolate][colCounter
							* ratio + colInterpolate] = redSample;
						interpolatedGreen[rowCounter * ratio + rowInterpolate][colCounter
							* ratio + colInterpolate] = greenSample;
						interpolatedBlue[rowCounter * ratio + rowInterpolate][colCounter
							* ratio + colInterpolate] = blueSample;
					}
				}

			}
		}


		// write the image
		return returnImage;
	}

}
