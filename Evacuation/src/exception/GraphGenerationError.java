package exception;

public class GraphGenerationError extends Exception { 
	private static final long serialVersionUID = 1L;
	public GraphGenerationError(String errorMessage) {
        super(errorMessage);
    }
}
