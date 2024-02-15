package exceptions;

import java.io.Serial;

public class MovementException extends GameActionException {

    /**
     *
     */
    @Serial
    private static final long serialVersionUID = 5769191860801098264L;

    public MovementException() {
        this("Can't Move");
    }

    public MovementException(String message) {
        super(message);
    }
}
