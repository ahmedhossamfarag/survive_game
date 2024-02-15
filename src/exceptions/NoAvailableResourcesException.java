package exceptions;

import java.io.Serial;

public class NoAvailableResourcesException extends GameActionException {

    /**
     *
     */
    @Serial
    private static final long serialVersionUID = -5856546994995996015L;

    public NoAvailableResourcesException() {
        this("No Available Sources");
    }

    public NoAvailableResourcesException(String message) {
        super(message);
    }

}
