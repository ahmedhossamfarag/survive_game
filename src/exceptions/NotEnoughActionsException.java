package exceptions;

import java.io.Serial;

public class NotEnoughActionsException extends GameActionException {

    /**
     *
     */
    @Serial
    private static final long serialVersionUID = -3421344947429705898L;

    public NotEnoughActionsException() {
        this("No Enough Actions");
    }

    public NotEnoughActionsException(String message) {
        super(message);
    }

}
