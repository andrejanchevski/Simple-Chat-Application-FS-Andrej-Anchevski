package com.fs.chat.application.chatapplication.exceptions;

import lombok.Data;

@Data
public class ResourceNotFoundException extends RuntimeException {
    private final Long id;
    private final Class<?> type;

    public ResourceNotFoundException(Long id, Class<?> type){
        super(String.format("The following %s with id: %s could not be found", id, type.getSimpleName()));
        this.id = id;
        this.type = type;
    }
}
