package com.example.userapp.dto;

import jakarta.validation.constraints.NotBlank;

public class AddressDto {

    @NotBlank
    private String type;

    @NotBlank
    private String addressText;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddressText() {
        return addressText;
    }

    public void setAddressText(String addressText) {
        this.addressText = addressText;
    }
}
