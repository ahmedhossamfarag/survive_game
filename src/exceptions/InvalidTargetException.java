package exceptions;

import java.io.Serial;

public class InvalidTargetException extends GameActionException {

    /**
     *
     */
    @Serial
    private static final long serialVersionUID = 7663369755334419843L;

    public InvalidTargetException() {
        this("Invalid Target");
    }

    public InvalidTargetException(String message) {
        super(message);
    }
}
