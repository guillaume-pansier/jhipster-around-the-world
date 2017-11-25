package com.panroets.aroundtheworld.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CountryPath.
 */
@Document(collection = "country_path")
public class CountryPath implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;

    @Field("iso_code")
    private String isoCode;

    @Field("interest_points")
    private String interestPoints;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public CountryPath isoCode(String isoCode) {
        this.isoCode = isoCode;
        return this;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

    public String getInterestPoints() {
        return interestPoints;
    }

    public CountryPath interestPoints(String interestPoints) {
        this.interestPoints = interestPoints;
        return this;
    }

    public void setInterestPoints(String interestPoints) {
        this.interestPoints = interestPoints;
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
        CountryPath countryPath = (CountryPath) o;
        if (countryPath.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), countryPath.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CountryPath{" +
            "id=" + getId() +
            ", isoCode='" + getIsoCode() + "'" +
            ", interestPoints='" + getInterestPoints() + "'" +
            "}";
    }
}
