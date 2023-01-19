package com.bestreads.bookrecommendations.utils;

class UtilsClassInitialisationException extends IllegalStateException {

    UtilsClassInitialisationException(Class clazz) {
        super(clazz.getName() + " is a util class and should not be initialised.");
    }
}
