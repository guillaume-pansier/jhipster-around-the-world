package com.panroets.aroundtheworld.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Country entity.
 */
public class CountryDTO implements Serializable {

    private String id;

    private String countryName;

    private String isoCode;

    private String pathSvgFormat;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

    public String getPathSvgFormat() {
        return pathSvgFormat;
    }

    public void setPathSvgFormat(String pathSvgFormat) {
        this.pathSvgFormat = pathSvgFormat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CountryDTO countryDTO = (CountryDTO) o;
        if(countryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), countryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CountryDTO{" +
            "id=" + getId() +
            ", countryName='" + getCountryName() + "'" +
            ", isoCode='" + getIsoCode() + "'" +
            ", pathSvgFormat='" + getPathSvgFormat() + "'" +
            "}";
    }
}
