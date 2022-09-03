package com.fvthree.eshop.exceptions;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 5814495079139327982L;
	
	private String resourceName;
    private String fieldName;
    private UUID fieldValue;
    
    public ResourceNotFoundException(String resourceName, String fieldName, UUID id) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, id));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = id;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public UUID getFieldValue() {
        return fieldValue;
    }
}
