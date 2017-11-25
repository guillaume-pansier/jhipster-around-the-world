package com.panroets.aroundtheworld.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the CountryPath entity.
 */
public class CountryPathDTO implements Serializable {

    private String id;

    private String isoCode;

    private String interestPoints;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

    public String getInterestPoints() {
        return interestPoints;
    }

    public void setInterestPoints(String interestPoints) {
        this.interestPoints = interestPoints;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CountryPathDTO countryPathDTO = (CountryPathDTO) o;
        if(countryPathDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), countryPathDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CountryPathDTO{" +
            "id=" + getId() +
            ", isoCode='" + getIsoCode() + "'" +
            ", interestPoints='" + getInterestPoints() + "'" +
            "}";
    }
}
