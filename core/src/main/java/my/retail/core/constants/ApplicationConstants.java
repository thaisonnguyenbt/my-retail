package my.retail.core.constants;

/**
 * This class is used to define application level constants.
 * 
 * @author ArgilDX
 */
public final class ApplicationConstants {

	/** The Constant ASSET_UPDATE_PATH */
	public static final String ASSET_UPDATE_PATH = "/content/dam/images/";

	/** The Constant DOWNLOAD_WORKFLOW_PATH */
    public static final String THUMBNAIL_WORKFLOW_PATH = "/var/workflow/models/myretail-thumbnail-workflow";

	/** The Constant JCR_PATH */
	public static final String JCR_PATH = "JCR_PATH";

	/** The constant ORIGINAL_RENDITION_RELATIVE_PATH */
	public static final String ORIGINAL_RENDITION_RELATIVE_PATH = "/jcr:content/renditions/original";

	/**
	 * Instantiates a new application constants.
	 */
	private ApplicationConstants() {
		/* EMPTY CONSTRUCTOR */
	}
}
