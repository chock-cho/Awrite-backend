package Awrite_project.Awrite.apiPayload.exception;

public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String message){
        super(message);
    }
}
