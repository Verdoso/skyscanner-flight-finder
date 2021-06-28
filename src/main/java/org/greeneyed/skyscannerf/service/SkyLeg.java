package org.greeneyed.skyscannerf.service;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data class SkyLeg {
    @JsonProperty(value = "CarrierIds")
    private List<String> carrierIds;

    @JsonProperty(value = "DepartureDate")
    private LocalDateTime date;

    @JsonProperty(value = "DestinationId")
    private String destinationId;
}