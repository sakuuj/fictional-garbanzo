package by.sakuuj.agsr.exceptions;

import by.sakuuj.agsr.enums.ErrorCode;
import lombok.Getter;

@Getter
public class AbstractServiceException extends RuntimeException {

    private final ErrorCode errorCode;

    public AbstractServiceException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public AbstractServiceException(String message, Throwable throwable, ErrorCode errorCode) {
        super(message, throwable);
        this.errorCode = errorCode;
    }
}
