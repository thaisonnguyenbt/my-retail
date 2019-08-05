package my.retail.core.constants;

/**
 * This class will hold all the constants related to Exception Handling.
 *
 * @author ArgilDX
 *
 */
public final class ExceptionConstants {

	/** The Constant ERROR_EXEC_WORKFLOW. */
	public static final String ERROR_EXEC_WORKFLOW = "Error while executing the workflow: ";

	/** The constant ERROR_LOGIN */
	public static final String ERROR_LOGIN = "Error while logging in to the repository ";

	/** The constant ERROR_REPOSITORY */
	public static final String ERROR_REPOSITORY = "Error while accessing the repository ";

	/**
	 * Constructor to hide the implicit public static members.
	 */
	private ExceptionConstants() {
		throw new IllegalAccessError("Constant class. No need to instantiate this class");
	}

}
