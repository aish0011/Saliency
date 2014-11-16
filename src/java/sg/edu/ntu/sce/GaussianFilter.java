package sg.edu.ntu.sce;

public class GaussianFilter {

	public static ImageInfo run(ImageInfo image) { 

		long[][] kernel = new long[][] { { 1, 2, 1 }, { 2, 4, 2 }, { 1, 2, 1 } };

                int MAX_ROW_COUNT = image.MAX_ROW_COUNT;
                int MAX_COL_COUNT = image.MAX_COL_COUNT;

                ImageInfo returnImage = new ImageInfo();
                returnImage.header = image.header;
                returnImage.MAX_COL_COUNT = image.MAX_COL_COUNT;
                returnImage.MAX_ROW_COUNT = image.MAX_ROW_COUNT;

                // for display purposes
                long[][] redChannel = new long[MAX_ROW_COUNT][MAX_COL_COUNT];
                returnImage.red = redChannel;
                long[][] greenChannel = new long[MAX_ROW_COUNT][MAX_COL_COUNT];
                returnImage.green = greenChannel;
                long[][] blueChannel = new long[MAX_ROW_COUNT][MAX_COL_COUNT];
                returnImage.blue = blueChannel;

		for (int rowCounter = MAX_ROW_COUNT - 1; rowCounter >= 0; rowCounter--) {
			for (int colCounter = MAX_COL_COUNT - 1; colCounter >= 0; colCounter--) {

				long sumIntensity = 0;
				long sumRed = 0;
				long sumGreen = 0;
				long sumBlue = 0;

				// locate neighbourhood
                                for (int i = 0; i < kernel.length; i++) {
                                        long[] kernelRow = kernel[i];
                                        for (int j = 0; j < kernelRow.length; j++) {

                                                if (ImageHelper.boundsCheck(returnImage, rowCounter + i, colCounter
                                                                + j)) {
                                                        sumRed += image.red[rowCounter + i][colCounter + j]
                                                                        * kernel[i][j];
                                                        sumGreen += image.green[rowCounter + i][colCounter
                                                                        + j]
                                                                        * kernel[i][j];
                                                        sumBlue += image.blue[rowCounter + i][colCounter
                                                                        + j]
                                                                        * kernel[i][j];
                                                } else {
                                                        sumRed = 16 * image.red[rowCounter][colCounter];
                                                        sumGreen = 16 * image.green[rowCounter][colCounter];
                                                        sumBlue = 16 * image.blue[rowCounter][colCounter];
                                                }
                                        }
                                }

                                // assign values
                                redChannel[rowCounter][colCounter] = sumRed / 16;
                                greenChannel[rowCounter][colCounter] = sumGreen / 16;
                                blueChannel[rowCounter][colCounter] = sumBlue / 16;

			}
		}
		
		// write the image
		return returnImage;
	}

}
