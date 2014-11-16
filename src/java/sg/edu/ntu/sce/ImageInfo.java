package sg.edu.ntu.sce;

/**
 * Class to contain information about an image object for processing and file
 * handling
 * 
 * @author Nachiket Kapre
 * @since 16th September 2005
 * @since 22nd September 2005
 * @since 25th July 2013
 */
public class ImageInfo {

    public int MAX_ROW_COUNT;
    public int MAX_COL_COUNT;
    public byte[] header;

    public long[][] red;
    public long[][] green;
    public long[][] blue;

    /**
     * dummy constructor
     */
    public ImageInfo() {

    }

}

