package com.bestreads.bookrecommendations.utils;

public class UtilsClassInitialisationException extends IllegalStateException {

  public UtilsClassInitialisationException(Class clazz) {
    super(clazz.getName() + " is a util class and should not be initialised.");
  }
}
