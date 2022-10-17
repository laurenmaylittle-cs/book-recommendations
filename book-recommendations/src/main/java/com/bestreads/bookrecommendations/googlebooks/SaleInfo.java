package com.bestreads.bookrecommendations.googlebooks;

class SaleInfo {

    private String country;

    private SaleStatus saleability;

    private boolean isEbook;

    private String buyLink;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public SaleStatus getSaleability() {
        return saleability;
    }

    public void setSaleability(SaleStatus saleability) {
        this.saleability = saleability;
    }

    public boolean isEbook() {
        return isEbook;
    }

    public void setEbook(boolean ebook) {
        isEbook = ebook;
    }

    public String getBuyLink() {
        return buyLink;
    }

    public void setBuyLink(String buyLink) {
        this.buyLink = buyLink;
    }
}
