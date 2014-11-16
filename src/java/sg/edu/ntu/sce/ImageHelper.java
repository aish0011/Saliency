package sg.edu.ntu.sce;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 
 * A collection of useful methods for processing images
 * 
 * @author Nachiket Kapre
 * @since 16th September 2005
 * @since 22nd September 2005
 * @since 25th July 2013
 */

public class ImageHelper {
	
	/**
	 * read the image bmp
	 * 
	 * @param image
	 * @param fileName
	 */
	public static ImageInfo readImage(String fileName) {

		byte[] header = new byte[14 + 40];

		try {
			FileInputStream fs = new FileInputStream(fileName);
			fs.read(header);

			// extract image size information here
			int lobyte1 = header[18] & 0x00FF;
			int lobyte2 = header[19] & 0x00FF;
			int lobyte3 = header[20] & 0x00FF;
			int lobyte4 = header[21] & 0x00FF;
			int hibyte1 = header[22] & 0x00FF; 
			int hibyte2 = header[23] & 0x00FF;
			int hibyte3 = header[24] & 0x00FF;
			int hibyte4 = header[25] & 0x00FF;

			int MAX_ROW_COUNT = hibyte1 + (hibyte2 << 8) + (hibyte3 << 16)
				+ (hibyte4 << 24);
			int MAX_COL_COUNT = lobyte1 + (lobyte2 << 8) + (lobyte3 << 16)
				+ (lobyte4 << 24);
			System.out.println("Read file " + fileName + " with resolution="
					+ MAX_ROW_COUNT + "," + MAX_COL_COUNT);

			long[][] red = new long[MAX_ROW_COUNT][MAX_COL_COUNT];
			long[][] green = new long[MAX_ROW_COUNT][MAX_COL_COUNT];
			long[][] blue = new long[MAX_ROW_COUNT][MAX_COL_COUNT];

			// now we are free to read in the goddamn image!!
			for (int rowCounter = MAX_ROW_COUNT - 1; rowCounter >= 0; rowCounter--) {
				for (int colCounter = MAX_COL_COUNT - 1; colCounter >= 0; colCounter--) {
					blue[rowCounter][colCounter] = fs.read();
					green[rowCounter][colCounter] = fs.read();
					red[rowCounter][colCounter] = fs.read();
				}
			}

			fs.close();
			
			// encode imageInfo
			ImageInfo image = new ImageInfo();
			image.MAX_ROW_COUNT = MAX_ROW_COUNT;
			image.MAX_COL_COUNT = MAX_COL_COUNT;
			image.header = header;
			image.red = red;
			image.green = green;
			image.blue = blue;

			return image;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * write the image bmp
	 * 
	 * @param image
	 * @param fileName
	 */
	public static void writeImage(ImageInfo image, String fileName) {

		// decode imageInfo
		byte[] header = image.header;
		long[][] red = image.red;
		long[][] green = image.green;
		long[][] blue = image.blue;
		int MAX_ROW_COUNT = image.MAX_ROW_COUNT;
		int MAX_COL_COUNT = image.MAX_COL_COUNT;

		FileOutputStream ofs;
		try {

			ofs = new FileOutputStream(fileName);
			header[18] = (byte) (MAX_COL_COUNT & 0x000000FF);
			header[19] = (byte) ((MAX_COL_COUNT >> 8) & 0x000000FF);
			header[20] = (byte) ((MAX_COL_COUNT >> 16) & 0x000000FF);
			header[21] = (byte) ((MAX_COL_COUNT >> 24) & 0x000000FF);
			header[22] = (byte) (MAX_ROW_COUNT & 0x000000FF);
			header[23] = (byte) ((MAX_ROW_COUNT >> 8) & 0x000000FF);
			header[24] = (byte) ((MAX_ROW_COUNT >> 16) & 0x000000FF);
			header[25] = (byte) ((MAX_ROW_COUNT >> 24) & 0x000000FF);
			ofs.write(header);

			for (int rowCounter = MAX_ROW_COUNT - 1; rowCounter >= 0; rowCounter --) {
				for (int colCounter = MAX_COL_COUNT - 1; colCounter >= 0; colCounter --) {

					// rgb must be written in opposite order
					ofs.write((byte) blue[rowCounter][colCounter]);
					ofs.write((byte) green[rowCounter][colCounter]);
					ofs.write((byte) red[rowCounter][colCounter]);

				}
			}

			ofs.close();

			System.out.println("Wrote file " + fileName
					+ " with resolution=" + MAX_ROW_COUNT + ","
					+ MAX_COL_COUNT);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    /**
     * check to see if the range of pixels being accessed are within the frame
     * boundaries or not
     * @param baseImage
     * @param i
     * @param j
     */
    public static boolean boundsCheck(ImageInfo baseImage, int i, int j) {
        int MAX_ROW_COUNT = baseImage.MAX_ROW_COUNT;
        int MAX_COL_COUNT = baseImage.MAX_COL_COUNT;
        if (i >= 0 && i < MAX_ROW_COUNT && j >= 0 && j < MAX_COL_COUNT) {
            return true;
        }

        return false;
    }

}
