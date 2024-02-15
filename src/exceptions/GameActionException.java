package exceptions;

import java.io.Serial;

public abstract class GameActionException extends Exception {

    /**
     *
     */
    @Serial
    private static final long serialVersionUID = -4592394600548544361L;

    public GameActionException(String message) {
        super(message);
    }

}
