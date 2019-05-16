package pl.krzysztof.drzazga.exception;

public class WrongDataException extends RuntimeException {
    private String reason;

    public WrongDataException(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String cause) {
        this.reason = cause;
    }
}
