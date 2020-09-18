package se.lexicon.huiyi.booklender.exception;

import java.time.LocalDateTime;
import java.util.List;

public class ValidationErrorResponse extends MyExceptionResponse {
    private final List<Violation> violations;

    public ValidationErrorResponse(LocalDateTime timestamp, Integer status, String error, String message, String path, List<Violation> violations) {
        super(timestamp, status, error, message, path);
        this.violations = violations;
    }

    public List<Violation> getViolations() {
        return violations;
    }
}
