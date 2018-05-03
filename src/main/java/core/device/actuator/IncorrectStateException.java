package core.device.actuator;

class IncorrectStateException extends Throwable {

    IncorrectStateException(String wantedStated, String deviceState) {
        super("The actuator doesn't have the wanted state || " +
                "WANTED :  " + wantedStated + " || " +
                "GOT " + deviceState);
    }

}
