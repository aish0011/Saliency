package sg.edu.ntu.sce;

public class Normalize {

	// USAGE: Normalize <input_filename> <output_filename> <channel: 0=R, 1=G, 2=B>
	public static ImageInfo run(ImageInfo image, int channel) { 

		// read the image

                int MAX_ROW_COUNT = image.MAX_ROW_COUNT;
                int MAX_COL_COUNT = image.MAX_COL_COUNT;

                ImageInfo returnImage = new ImageInfo();
                returnImage.header = image.header;
                returnImage.MAX_COL_COUNT = image.MAX_COL_COUNT;
                returnImage.MAX_ROW_COUNT = image.MAX_ROW_COUNT;

                // for display purposes
                long[][] zeroChannel = new long[MAX_ROW_COUNT][MAX_COL_COUNT];
                long[][] newChannel = new long[MAX_ROW_COUNT][MAX_COL_COUNT];
                long[][] selectedChannel;

		// select your channel
		if(channel==0) {
			selectedChannel = image.red;
                	returnImage.red = newChannel;
                	returnImage.green = zeroChannel;
                	returnImage.blue = zeroChannel;
		} else if (channel==1) {
			selectedChannel = image.green;
                	returnImage.red = zeroChannel;
                	returnImage.green = newChannel;
                	returnImage.blue = zeroChannel;
		} else {
			selectedChannel = image.blue;
                	returnImage.red = zeroChannel;
                	returnImage.green = zeroChannel;
                	returnImage.blue = newChannel;
		}

		// extract the max
                long max = 0;
                for (int rowCounter = 0; rowCounter < MAX_ROW_COUNT; rowCounter++) {
                        for (int colCounter = 0; colCounter < MAX_COL_COUNT; colCounter++) {
                                if (max <= selectedChannel[rowCounter][colCounter]) {
                                        max = selectedChannel[rowCounter][colCounter];
                                }
                        }
                }

                // normalize the data
                for (int rowCounter = 0; rowCounter < MAX_ROW_COUNT; rowCounter++) {
                        for (int colCounter = 0; colCounter < MAX_COL_COUNT; colCounter++) {
                                if (max != 0) {
                                        newChannel[rowCounter][colCounter] = (255 * selectedChannel[rowCounter][colCounter] / max);
                			returnImage.green[rowCounter][colCounter] = newChannel[rowCounter][colCounter];
		                	returnImage.blue[rowCounter][colCounter] = newChannel[rowCounter][colCounter];

                                } else {
                                        newChannel[rowCounter][colCounter] = 0;
                			returnImage.green[rowCounter][colCounter] = newChannel[rowCounter][colCounter];
		                	returnImage.blue[rowCounter][colCounter] = newChannel[rowCounter][colCounter];
                                }
                        }
                }
		
		// write the image
		return returnImage;
	}

}
