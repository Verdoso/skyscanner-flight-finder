package org.greeneyed.skyscannerf.service;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data class SkyResponse {
    @JsonProperty(value = "Quotes")
    private List<SkyQuote> quotes;

    @JsonProperty(value = "Carriers")
    private List<SkyCarrier> carriers;

    @JsonProperty(value = "Places")
    private List<SkyPlace> places;
}