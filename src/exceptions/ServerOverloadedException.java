package exceptions;

public class ServerOverloadedException extends Exception{

	private static final long serialVersionUID = -8777716093169406722L;

	public ServerOverloadedException(String m) {
		super(m);
	}
}
