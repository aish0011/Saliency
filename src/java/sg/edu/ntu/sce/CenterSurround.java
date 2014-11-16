package sg.edu.ntu.sce;

public class CenterSurround {

	// USAGE: CenterSurround <input_filename0> <input_filename1> <output_filename>
	// file0 > file1
	public static ImageInfo run(ImageInfo image0, ImageInfo image1) {


                int MAX_ROW_COUNT0 = image0.MAX_ROW_COUNT;
                int MAX_COL_COUNT0 = image0.MAX_COL_COUNT;
                int MAX_ROW_COUNT1 = image1.MAX_ROW_COUNT;
                int MAX_COL_COUNT1 = image1.MAX_COL_COUNT;

		ImageInfo returnImage = new ImageInfo();
		returnImage.header = image0.header;
		returnImage.MAX_COL_COUNT = image0.MAX_COL_COUNT;
		returnImage.MAX_ROW_COUNT = image0.MAX_ROW_COUNT;

		long[][] red = new long[MAX_ROW_COUNT0][MAX_COL_COUNT0];
		long[][] green = new long[MAX_ROW_COUNT0][MAX_COL_COUNT0];
		long[][] blue = new long[MAX_ROW_COUNT0][MAX_COL_COUNT0];
		returnImage.red = red;
		returnImage.green = green;
		returnImage.blue = blue;

		int maxDepth = (int)Math.ceil(Math.log(MAX_ROW_COUNT0/MAX_ROW_COUNT1)/(double)Math.log(2));
		ImageInfo[] unrollImages = new ImageInfo[maxDepth+1];
		unrollImages[0]=image1;
		
		// TODO. Write center-surround code here..			
		for(int i=0;i<maxDepth;i++) {
			unrollImages[i+1] = Interpolate.run(unrollImages[i]);
			//ImageHelper.writeImage(unrollImages[i], args[2]+"_"+i+".bmp");
		}
	
		for(int rowCounter=0; rowCounter<MAX_ROW_COUNT0; rowCounter++) {
			for(int colCounter=0; colCounter<MAX_COL_COUNT0; colCounter++) {
				returnImage.red[rowCounter][colCounter] = Math.max(0,image0.red[rowCounter][colCounter] - unrollImages[maxDepth].red[rowCounter][colCounter]);
				returnImage.green[rowCounter][colCounter] = Math.max(0,image0.green[rowCounter][colCounter] - unrollImages[maxDepth].green[rowCounter][colCounter]);
				returnImage.blue[rowCounter][colCounter] = Math.max(0,image0.blue[rowCounter][colCounter] - unrollImages[maxDepth].blue[rowCounter][colCounter]);
			}
		}
		return returnImage;

	}
}
