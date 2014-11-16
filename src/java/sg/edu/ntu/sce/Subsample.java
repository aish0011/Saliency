package sg.edu.ntu.sce;

import java.lang.Math;
import java.util.Arrays;

public class Subsample {

	public static ImageInfo run(ImageInfo image) { 

		// read the image
		int ratio = 2; 

                int MAX_ROW_COUNT = image.MAX_ROW_COUNT;
                int MAX_COL_COUNT = image.MAX_COL_COUNT;

		// new image data
                int newRowCount = (int) Math.ceil(MAX_ROW_COUNT / (double) ratio);
                int newColCount = (int) Math.ceil(MAX_COL_COUNT / (double) ratio);
                long[][] subsampledRed = new long[newRowCount][newColCount];
                long[][] subsampledGreen = new long[newRowCount][newColCount];
                long[][] subsampledBlue = new long[newRowCount][newColCount];

		// modify the header of the 
                ImageInfo returnImage = new ImageInfo();
		returnImage.MAX_ROW_COUNT = newRowCount;
		returnImage.MAX_COL_COUNT = newColCount;
		returnImage.header = Arrays.copyOf(image.header,image.header.length);
                returnImage.red = subsampledRed;
                returnImage.green = subsampledGreen;
                returnImage.blue = subsampledBlue;

                byte[] header = returnImage.header;
                header[18] = (byte) (newColCount & 0x000000FF);
                header[19] = (byte) ((newColCount >> 8) & 0x000000FF);
                header[20] = (byte) ((newColCount >> 16) & 0x000000FF);
                header[21] = (byte) ((newColCount >> 24) & 0x000000FF);
                header[22] = (byte) (newRowCount & 0x000000FF);
                header[23] = (byte) ((newRowCount >> 8) & 0x000000FF);
                header[24] = (byte) ((newRowCount >> 16) & 0x000000FF);
                header[25] = (byte) ((newRowCount >> 24) & 0x000000FF);

		//for (int rowCounter = MAX_ROW_COUNT - 1; rowCounter >= 0; rowCounter -= ratio) {
                  //      for (int colCounter = MAX_COL_COUNT - 1; colCounter >= 0; colCounter -= ratio) {
		for (int rowCounter = 0; rowCounter < MAX_ROW_COUNT; rowCounter += ratio) {
                        for (int colCounter = 0; colCounter < MAX_COL_COUNT; colCounter += ratio) {

                                int rowIdx = rowCounter / ratio;
                                int colIdx = colCounter / ratio;
                                subsampledRed[rowIdx][colIdx] = image.red[rowCounter][colCounter];
                                subsampledGreen[rowIdx][colIdx] = image.green[rowCounter][colCounter];
                                subsampledBlue[rowIdx][colIdx] = image.blue[rowCounter][colCounter];
                        }
                }

		// write the image
		return returnImage;
	}

}
