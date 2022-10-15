package com.em.insurtech.domain;

import java.math.BigDecimal;

public class Claim {

    String userName;
    String imageUri;
    String dependentName;
    BigDecimal amount;
    boolean isSubmitted;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getDependentName() {
        return dependentName;
    }

    public void setDependentName(String dependentName) {
        this.dependentName = dependentName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public boolean isSubmitted() {
        return isSubmitted;
    }

    public void setSubmitted(boolean submitted) {
        isSubmitted = submitted;
    }
}
