package com.panroets.aroundtheworld.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Country.
 */
@Document(collection = "country")
public class Country implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;

    @Field("country_name")
    private String countryName;

    @Field("iso_code")
    private String isoCode;

    @Field("path_svg_format")
    private String pathSvgFormat;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public Country countryName(String countryName) {
        this.countryName = countryName;
        return this;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public Country isoCode(String isoCode) {
        this.isoCode = isoCode;
        return this;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

    public String getPathSvgFormat() {
        return pathSvgFormat;
    }

    public Country pathSvgFormat(String pathSvgFormat) {
        this.pathSvgFormat = pathSvgFormat;
        return this;
    }

    public void setPathSvgFormat(String pathSvgFormat) {
        this.pathSvgFormat = pathSvgFormat;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Country country = (Country) o;
        if (country.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), country.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Country{" +
            "id=" + getId() +
            ", countryName='" + getCountryName() + "'" +
            ", isoCode='" + getIsoCode() + "'" +
            ", pathSvgFormat='" + getPathSvgFormat() + "'" +
            "}";
    }
}
