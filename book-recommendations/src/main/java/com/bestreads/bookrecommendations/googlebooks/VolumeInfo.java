package com.bestreads.bookrecommendations.googlebooks;

import java.util.ArrayList;

public record VolumeInfo(String title,
                         ArrayList<String> authors,
                         String publisher,
                         String publishedDate,
                         String description,
                         ArrayList<IndustryIdentifier> industryIdentifiers,
                         ReadingModes readingModes,
                         int pageCount,
                         String printType,
                         ArrayList<String> categories,
                         String maturityRating,
                         boolean allowAnonLogging,
                         String contentVersion,
                         ImageLinks imageLinks,
                         String language,
                         String previewLink,
                         String infoLink,
                         String canonicalVolumeLink,
                         int averageRating,
                         int ratingsCount){

}
