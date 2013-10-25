package exceptions;

public class UniqueTitleViolationException extends Exception {

	private static final long serialVersionUID = 8834644856843766803L;

	public UniqueTitleViolationException(String m) {
		super(m);
	}
}
