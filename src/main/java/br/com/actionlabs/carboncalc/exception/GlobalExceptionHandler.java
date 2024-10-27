package br.com.actionlabs.carboncalc.exception;



import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(JsonMappingException.class)
    public ResponseEntity<ErrorResponse> handleJsonMappingException(JsonMappingException ex) {
        String userFriendlyMessage = "There was an error processing your request. Please check the data and try again.";
        List<String> errorDetails = new ArrayList<>();

        for (JsonMappingException.Reference ref : ex.getPath()) {
            String fieldName = ref.getFieldName();
            String errorMessage = ex.getOriginalMessage();

            String typeExpected = extractExpectedType(errorMessage);
            if (!typeExpected.isEmpty()) {
                errorDetails.add(fieldName + ": " + " - Expected type: " + typeExpected);
            } else {
                errorDetails.add(fieldName + ": " + " - " + errorMessage);
            }
        }

        ErrorResponse errorResponse = new ErrorResponse(userFriendlyMessage, errorDetails);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    private String extractExpectedType(String errorMessage) {
        if (errorMessage.contains("Cannot deserialize value of type")) {
            String[] parts = errorMessage.split("from");
            if (parts.length > 0) {
                String typePart = parts[0];
                String[] typeDetails = typePart.split("`");
                if (typeDetails.length > 1) {
                    return typeDetails[1].trim();
                }
            }
        }
        return ""; 
    }
}
