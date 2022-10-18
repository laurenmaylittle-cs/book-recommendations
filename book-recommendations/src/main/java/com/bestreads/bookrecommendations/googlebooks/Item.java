package com.bestreads.bookrecommendations.googlebooks;

public record Item(String kind,
                   String id,
                   String etag,
                   String selfLink,
                   VolumeInfo volumeInfo,
                   SaleInfo saleInfo,
                   AccessInfo accessInfo,
                   SearchInfo searchInfo) {

}
