package Awrite_project.Awrite.apiPayload.exception;

import Awrite_project.Awrite.apiPayload.code.BaseErrorCode;

public class TempHandler extends GeneralException {

    public TempHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}