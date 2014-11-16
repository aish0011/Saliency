package sg.edu.ntu.sce;

public class Saliency {

	// USAGE: Saliency <input_filename> <output_filename> 
	public static void main(String args[]) {

		ImageInfo image = ImageHelper.readImage(args[0]);
                int MAX_ROW_COUNT = image.MAX_ROW_COUNT;
                int MAX_COL_COUNT = image.MAX_COL_COUNT;
		int i, j;

		// Return image

		ImageInfo returnImage = new ImageInfo();
		returnImage.header = image.header;
		returnImage.MAX_COL_COUNT = image.MAX_COL_COUNT;
		returnImage.MAX_ROW_COUNT = image.MAX_ROW_COUNT;

		// TODO: construct the saliency stack here..

		// Calculating depth for pyramid filtering

		int temp0 = image.MAX_ROW_COUNT;
		int temp1 = image.MAX_COL_COUNT;
		int depth = 0;

		while(temp0 % 2 == 0 && temp1 % 2 ==0) {
			depth++;
			temp0 /= 2;
			temp1 /= 2;
		}

		// Declaring required Imageinfo Objects for RG Colormap

		long[][] zerored = new long[MAX_ROW_COUNT][MAX_COL_COUNT];
		long[][] zerogreen = new long[MAX_ROW_COUNT][MAX_COL_COUNT];
		long[][] zeroblue = new long[MAX_ROW_COUNT][MAX_COL_COUNT];

		long[][] zerored1 = new long[MAX_ROW_COUNT][MAX_COL_COUNT];
		long[][] zerogreen1 = new long[MAX_ROW_COUNT][MAX_COL_COUNT];
		long[][] zeroblue1 = new long[MAX_ROW_COUNT][MAX_COL_COUNT];

		ImageInfo[] pyramidRedmap = new ImageInfo[depth+1];
		ImageInfo[] pyramidGreenmap = new ImageInfo[depth+1];		

		// cloning input images for R and G map

		ImageInfo imageclone0 = new ImageInfo();
		imageclone0.header = image.header;
		imageclone0.MAX_COL_COUNT = image.MAX_COL_COUNT;
		imageclone0.MAX_ROW_COUNT = image.MAX_ROW_COUNT;
		imageclone0.red = image.red;
		imageclone0.green = zerogreen;
		imageclone0.blue = zeroblue;

		ImageInfo imageclone1 = new ImageInfo();
		imageclone1.header = image.header;
		imageclone1.MAX_COL_COUNT = image.MAX_COL_COUNT;
		imageclone1.MAX_ROW_COUNT = image.MAX_ROW_COUNT;
		imageclone1.red = zerored1;
		imageclone1.green = image.green;
		imageclone1.blue = zeroblue1;

		// Calculate Rmap and Gmap
		pyramidRedmap = PyramidFilter.run(imageclone0, 0, depth);
		pyramidGreenmap = PyramidFilter.run(imageclone1, 1, depth);

		//X = ((R_0-G_0) - (G_2-R_2)) + ((R_0-G_0) - (G_3-R_3))
		//Y = ((R_1-G_1) - (G_3-R_3)) + ((R_1-G_1) - (G_4-R_4))

		ImageInfo X1 = new ImageInfo();
		ImageInfo X2 = new ImageInfo();
		ImageInfo X3 = new ImageInfo();
		ImageInfo X4 = new ImageInfo();
		ImageInfo X5 = new ImageInfo();

		ImageInfo Y1 = new ImageInfo();
		ImageInfo Y2 = new ImageInfo();
		ImageInfo Y3 = new ImageInfo();
		ImageInfo Y4 = new ImageInfo();


		X1 = CenterSurround.run(pyramidRedmap[0], pyramidGreenmap[0]);
		X2 = CenterSurround.run(pyramidGreenmap[2], pyramidRedmap[2]);
		X3 = CenterSurround.run(pyramidGreenmap[3], pyramidRedmap[3]);

		X4 = CenterSurround.run(pyramidRedmap[1], pyramidGreenmap[1]);
		X5 = CenterSurround.run(pyramidGreenmap[4], pyramidRedmap[4]);

		Y1 = CenterSurround.run(X1, X2);
		Y2 = CenterSurround.run(X1, X3);
		Y3 = CenterSurround.run(X4, X3);
		Y4 = CenterSurround.run(X4, X5);

		ImageInfo X = new ImageInfo();
		X.header = Y1.header;
		X.MAX_COL_COUNT = Y1.MAX_COL_COUNT;
		X.MAX_ROW_COUNT = Y1.MAX_ROW_COUNT;
		X.red = zerored1;
		X.green = zerogreen1;
		X.blue = zeroblue1;

		ImageInfo Y = new ImageInfo();
		Y.header = Y3.header;
		Y.MAX_COL_COUNT = Y3.MAX_COL_COUNT;
		Y.MAX_ROW_COUNT = Y3.MAX_ROW_COUNT;
		Y.red = zerored;
		Y.green = zerogreen;
		Y.blue = zeroblue;

		ImageInfo X_1 = new ImageInfo();

		ImageInfo RG = new ImageInfo();
		RG.header = Y3.header;
		RG.MAX_COL_COUNT = Y3.MAX_COL_COUNT;
		RG.MAX_ROW_COUNT = Y3.MAX_ROW_COUNT;
		RG.red = zerored;
		RG.green = zerogreen;
		RG.blue = zeroblue;

		for(i = 0; i < Y1.MAX_ROW_COUNT; i++) {
			for(j = 0; j < Y1.MAX_COL_COUNT; j++) {
				X.red[i][j] = (Y1.red[i][j] + Y2.red[i][j]) / 2;
				X.green[i][j] = (Y1.green[i][j] + Y2.green[i][j]) / 2;
				X.blue[i][j] = (Y1.blue[i][j] + Y2.blue[i][j]) / 2;
			}
		}
		for(i = 0; i < Y3.MAX_ROW_COUNT; i++) {
			for(j = 0; j < Y3.MAX_COL_COUNT; j++) {
				Y.red[i][j] = (Y3.red[i][j] + Y4.red[i][j]) / 2;
				Y.green[i][j] = (Y3.green[i][j] + Y4.green[i][j]) / 2;
				Y.blue[i][j] = (Y3.blue[i][j] + Y4.blue[i][j]) / 2;
			}
		}

		X_1 = Subsample.run(X);

		for(i = 0; i < Y3.MAX_ROW_COUNT; i++) {
			for(j = 0; j < Y3.MAX_COL_COUNT; j++) {
				RG.red[i][j] = (X_1.red[i][j] + Y.red[i][j]) / 2;
				RG.green[i][j] = RG.red[i][j];
				RG.blue[i][j] = RG.red[i][j];
			}
		}

		// Declaring required Imageinfo Objects for Intensity map

		long[][] zerored3 = new long[MAX_ROW_COUNT][MAX_COL_COUNT];
		long[][] zerogreen3 = new long[MAX_ROW_COUNT][MAX_COL_COUNT];
		long[][] zeroblue3 = new long[MAX_ROW_COUNT][MAX_COL_COUNT];

		long[][] zerored4 = new long[MAX_ROW_COUNT][MAX_COL_COUNT];
		long[][] zerogreen4 = new long[MAX_ROW_COUNT][MAX_COL_COUNT];
		long[][] zeroblue4 = new long[MAX_ROW_COUNT][MAX_COL_COUNT];

		long[][] zerored5 = new long[MAX_ROW_COUNT][MAX_COL_COUNT];
		long[][] zerogreen5 = new long[MAX_ROW_COUNT][MAX_COL_COUNT];
		long[][] zeroblue5 = new long[MAX_ROW_COUNT][MAX_COL_COUNT];

		long[][] zerored6 = new long[MAX_ROW_COUNT][MAX_COL_COUNT];
		long[][] zerogreen6 = new long[MAX_ROW_COUNT][MAX_COL_COUNT];
		long[][] zeroblue6 = new long[MAX_ROW_COUNT][MAX_COL_COUNT];

		long[][] zerored7 = new long[MAX_ROW_COUNT][MAX_COL_COUNT];
		long[][] zerogreen7 = new long[MAX_ROW_COUNT][MAX_COL_COUNT];
		long[][] zeroblue7 = new long[MAX_ROW_COUNT][MAX_COL_COUNT];



		ImageInfo Intensity = new ImageInfo();
		Intensity.header = image.header;
		Intensity.MAX_COL_COUNT = image.MAX_COL_COUNT;
		Intensity.MAX_ROW_COUNT = image.MAX_ROW_COUNT;
		Intensity.red = zerored3;
		Intensity.green = zerogreen3;
		Intensity.blue = zeroblue3;

		for(i = 0; i < image.MAX_ROW_COUNT; i++) {
			for(j = 0; j < image.MAX_COL_COUNT; j++) {
				Intensity.red[i][j] = (image.red[i][j] + image.green[i][j] + image.blue[i][j]) / 3;
			}
		}

		// Calculate Intensity map.

		ImageInfo[] pyramidIntmap = new ImageInfo[depth+1];

		pyramidIntmap = PyramidFilter.run(Intensity, 0, depth);

		//X = (I_0 - I_2) + (I_0 - I_3)
		//Y = (I_1 - I_3) + (I_1 - I_4)

		ImageInfo I1 = new ImageInfo();
		ImageInfo I2 = new ImageInfo();
		ImageInfo I3 = new ImageInfo();
		ImageInfo I4 = new ImageInfo();

		I1 = CenterSurround.run(pyramidIntmap[0], pyramidIntmap[2]);
		I2 = CenterSurround.run(pyramidIntmap[0], pyramidIntmap[3]);
		I3 = CenterSurround.run(pyramidIntmap[1], pyramidIntmap[3]);
		I4 = CenterSurround.run(pyramidIntmap[1], pyramidIntmap[4]);

	
		ImageInfo XI = new ImageInfo();
		XI.header = I1.header;
		XI.MAX_COL_COUNT = I1.MAX_COL_COUNT;
		XI.MAX_ROW_COUNT = I1.MAX_ROW_COUNT;
		XI.red = zerored5;
		XI.green = zerogreen5;
		XI.blue = zeroblue5;

		ImageInfo YI = new ImageInfo();
		YI.header = I3.header;
		YI.MAX_COL_COUNT = I3.MAX_COL_COUNT;
		YI.MAX_ROW_COUNT = I3.MAX_ROW_COUNT;
		YI.red = zerored6;
		YI.green = zerogreen6;
		YI.blue = zeroblue6;

		ImageInfo XI_1 = new ImageInfo();

		ImageInfo I_RG = new ImageInfo();
		I_RG.header = I3.header;
		I_RG.MAX_COL_COUNT = I3.MAX_COL_COUNT;
		I_RG.MAX_ROW_COUNT = I3.MAX_ROW_COUNT;
		I_RG.red = zerored7;
		I_RG.green = zerogreen7;
		I_RG.blue = zeroblue7;

		for(i = 0; i < I1.MAX_ROW_COUNT; i++) {
			for(j = 0; j < I1.MAX_COL_COUNT; j++) {
				XI.red[i][j] = (I1.red[i][j] + I2.red[i][j]) / 2;
				XI.green[i][j] = (I1.green[i][j] + I2.green[i][j]) / 2;
				XI.blue[i][j] = (I1.blue[i][j] + I2.blue[i][j]) / 2;
			}
		}
		for(i = 0; i < I3.MAX_ROW_COUNT; i++) {
			for(j = 0; j < I3.MAX_COL_COUNT; j++) {
				YI.red[i][j] = (I3.red[i][j] + I4.red[i][j]) / 2;
				YI.green[i][j] = (I3.green[i][j] + I4.green[i][j]) / 2;
				YI.blue[i][j] = (I3.blue[i][j] + I4.blue[i][j]) / 2;
			}
		}

		XI_1 = Subsample.run(XI);

		for(i = 0; i < I3.MAX_ROW_COUNT; i++) {
			for(j = 0; j < I3.MAX_COL_COUNT; j++) {
				I_RG.red[i][j] = (XI_1.red[i][j] + YI.red[i][j]) / 2;
				I_RG.green[i][j] = I_RG.red[i][j];
				I_RG.blue[i][j] = I_RG.red[i][j];
			}
		}

		//Normalise Rmap and Intensity map

		ImageInfo NRG = new ImageInfo();
		ImageInfo NI_RG = new ImageInfo();

		NRG  = Normalize.run(RG, 0);
		NI_RG  = Normalize.run(I_RG, 0);

		//Saliency

		ImageInfo Saliency = new ImageInfo();
		Saliency.header = NRG.header;
		Saliency.MAX_COL_COUNT = NRG.MAX_COL_COUNT;
		Saliency.MAX_ROW_COUNT = NRG.MAX_ROW_COUNT;
		Saliency.red = zerored7;
		Saliency.green = zerogreen7;
		Saliency.blue = zeroblue7;

		for(i = 0; i < NRG.MAX_ROW_COUNT; i++) {
			for(j = 0; j < NRG.MAX_COL_COUNT; j++) {
				Saliency.red[i][j] = (NRG.red[i][j] + NI_RG.red[i][j]) / 2;
				Saliency.green[i][j] = (NRG.green[i][j] + NI_RG.green[i][j]) / 2;
				Saliency.blue[i][j] = (NRG.blue[i][j] + NI_RG.blue[i][j]) / 2;
			}
		}
		
		//Aish code Saliency edit

		long[][] zerored8 = new long[MAX_ROW_COUNT][MAX_COL_COUNT];
		long[][] zerogreen8 = new long[MAX_ROW_COUNT][MAX_COL_COUNT];
		long[][] zeroblue8 = new long[MAX_ROW_COUNT][MAX_COL_COUNT];

		long[][] zerored9 = new long[MAX_ROW_COUNT][MAX_COL_COUNT];
		long[][] zerogreen9 = new long[MAX_ROW_COUNT][MAX_COL_COUNT];
		long[][] zeroblue9 = new long[MAX_ROW_COUNT][MAX_COL_COUNT];

		long[][] zerored10 = new long[MAX_ROW_COUNT][MAX_COL_COUNT];
		long[][] zerogreen10 = new long[MAX_ROW_COUNT][MAX_COL_COUNT];
		long[][] zeroblue10 = new long[MAX_ROW_COUNT][MAX_COL_COUNT];


		ImageInfo Saliency_rez = new ImageInfo();
		Saliency_rez.header = image.header;
		Saliency_rez.MAX_COL_COUNT = image.MAX_COL_COUNT;
		Saliency_rez.MAX_ROW_COUNT = image.MAX_ROW_COUNT;
		Saliency_rez.red = zerored8;
		Saliency_rez.green = zerogreen8;
		Saliency_rez.blue =zeroblue8;

		ImageInfo Original_img = new ImageInfo();
		Original_img.header = image.header;
		Original_img.MAX_COL_COUNT = image.MAX_COL_COUNT;
		Original_img.MAX_ROW_COUNT = image.MAX_ROW_COUNT;
		Original_img.red = zerored9;
		Original_img.green = zerogreen9;
		Original_img.blue =zeroblue9;

		ImageInfo Int_p = new ImageInfo();
		Int_p.header = image.header;
		Int_p.MAX_COL_COUNT = image.MAX_COL_COUNT;
		Int_p.MAX_ROW_COUNT = image.MAX_ROW_COUNT;
		Int_p.red = zerored10;
		Int_p.green = zerogreen10;
		Int_p.blue = zeroblue10;

		Saliency_rez = Interpolate.run(Saliency);

		for(i = 0; i < image.MAX_ROW_COUNT; i++) {
			for(j = 0; j < image.MAX_COL_COUNT; j++) {
				Int_p.red[i][j] = (Saliency_rez.red[i][j] + Saliency_rez.green[i][j] + Saliency_rez.blue[i][j]) / 3;
				Int_p.green[i][j] = Int_p.red[i][j];
				Int_p.blue[i][j] = Int_p.red[i][j];
			}
		}

		for(i = 0; i < image.MAX_ROW_COUNT; i++) {
			for(j = 0; j < image.MAX_COL_COUNT; j++) {
				Original_img.red[i][j] = (image.red[i][j] + Int_p.red[i][j]) / 2;
				Original_img.green[i][j] = (image.green[i][j] + Int_p.green[i][j]) / 2;
				Original_img.blue[i][j] = (image.blue[i][j] + Int_p.blue[i][j]) / 2;
			}
		}

		//ImageHelper.writeImage(Saliency, args[1]);

		ImageHelper.writeImage(Saliency_rez, args[2]);

		ImageHelper.writeImage(Original_img, args[1]);

	}
}
