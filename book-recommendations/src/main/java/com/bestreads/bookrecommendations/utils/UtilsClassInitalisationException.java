package com.bestreads.bookrecommendations.utils;

public class UtilsClassInitalisationException extends IllegalStateException {

    public UtilsClassInitalisationException(Class clazz) {
        super(clazz.getName());
    }
}
