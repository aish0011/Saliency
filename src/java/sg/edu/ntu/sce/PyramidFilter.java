package sg.edu.ntu.sce;

public class PyramidFilter {

	// USAGE: PyramigFilter <input_filename> <output_filename_prefix> <max_depth> <channel>
	public static ImageInfo[] run(ImageInfo image, int channel, int maxDepth) {


                int MAX_ROW_COUNT = image.MAX_ROW_COUNT;
                int MAX_COL_COUNT = image.MAX_COL_COUNT;

		ImageInfo[] pyramidImages = new ImageInfo[maxDepth+1];

		// for display purposes
                long[][] zeroChannel = new long[MAX_ROW_COUNT][MAX_COL_COUNT];

                // select your channel
                if(channel==0) {
                        image.green = zeroChannel;
                        image.blue = zeroChannel;
                } else if (channel==1) {
                        //image.red = image.green;
			image.red = zeroChannel;
                        //image.green = image.green;
                        image.blue = zeroChannel;
                } else {
                        image.red = zeroChannel;
                        image.green = zeroChannel;
                }

		pyramidImages[0] = image;

		for (int i = 0; i < maxDepth; i++) {

			// one step in the pyramid filter
			ImageInfo filter = GaussianFilter.run(pyramidImages[i]);
			ImageInfo subsample = Subsample.run(filter);
			pyramidImages[i+1] = subsample;

			// write the filtered image at a given depth
			//ImageHelper.writeImage(pyramidImages[i+1], args[1] + "_" + i + "_out.bmp");
		}
		return pyramidImages;

	}
}
