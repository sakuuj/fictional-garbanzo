package by.sakuuj.agsr.exceptions;

import by.sakuuj.agsr.enums.ErrorCode;

public class EntityNotFoundException extends AbstractServiceException {

    private static final ErrorCode ERROR_CODE = ErrorCode.ENTITY_NOT_FOUND;
    private static final String ERROR_MESSAGE = "Entity is not found";

    public EntityNotFoundException() {
        super(ERROR_MESSAGE, ERROR_CODE);
    }
}
