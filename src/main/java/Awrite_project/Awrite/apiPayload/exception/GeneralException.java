package Awrite_project.Awrite.apiPayload.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import Awrite_project.Awrite.apiPayload.code.BaseErrorCode;
import Awrite_project.Awrite.apiPayload.code.ErrorReasonDTO;

@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException {

    private BaseErrorCode code;

    public ErrorReasonDTO getErrorReason() {
        return this.code.getReason();
    }

    public ErrorReasonDTO getErrorReasonHttpStatus(){
        return this.code.getReasonHttpStatus();
    }
}