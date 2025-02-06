package com.JOExamStudi.JOExamStudi.dto;

public class OfferSalesDTO {
    private Long offerId;
    private String offerType;
    private Long salesCount;

    public OfferSalesDTO(Long offerId, String offerType, Long salesCount) {
        this.offerId = offerId;
        this.offerType = offerType;
        this.salesCount = salesCount;
    }

    // Getters and Setters
    public Long getOfferId() {
        return offerId;
    }
    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }
    public String getOfferType() {
        return offerType;
    }
    public void setOfferType(String offerType) {
        this.offerType = offerType;
    }
    public Long getSalesCount() {
        return salesCount;
    }
    public void setSalesCount(Long salesCount) {
        this.salesCount = salesCount;
    }
}

