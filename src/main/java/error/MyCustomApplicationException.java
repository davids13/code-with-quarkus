package error;

public class MyCustomApplicationException extends RuntimeException {

    public MyCustomApplicationException(String message) {
        super(message);
    }
}
