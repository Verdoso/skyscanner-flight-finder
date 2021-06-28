package org.greeneyed.skyscannerf.service;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data class SkyCarrier {
    @JsonProperty(value = "CarrierId")
    private String carrierId;

    @JsonProperty(value = "Name")
    private String name;
}