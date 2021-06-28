package org.greeneyed.skyscannerf.service;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data class SkyPlace {
    @JsonProperty(value = "Name")
    private String name;

    @JsonProperty(value = "PlaceId")
    private String placeId;

    @JsonProperty(value = "IataCode")
    private String iataCode;

    @JsonProperty(value = "SkyscannerCode")
    private String skyscannerCode;

    @JsonProperty(value = "CityName")
    private String cityName;

    @JsonProperty(value = "CityId")
    private String cityId;

    @JsonProperty(value = "CountryName")
    private String countryName;
}