package sg.edu.ntu.sce;

import java.util.Arrays;

public class Compare {

	public static boolean compareImage(String file1, String file2) {
		ImageInfo image1 = ImageHelper.readImage(file1);
		ImageInfo image2 = ImageHelper.readImage(file2);

		if(Arrays.deepEquals(image1.red,image2.red) && Arrays.deepEquals(image1.green,image2.green) && Arrays.deepEquals(image1.blue,image2.blue)) {
			return true;
		}

		else {
			return false;
		}
	}
}		
