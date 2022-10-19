package com.bestreads.bookrecommendations.googlebooks;

record AccessInfo(String country,
                  String viewability,
                  boolean embeddable,
                  boolean publicDomain,
                  String textToSpeechPermission,
                  Epub epub,
                  Pdf pdf,
                  String webReaderLink,
                  String accessViewStatus,
                  boolean quoteSharingAllowed) {
}
