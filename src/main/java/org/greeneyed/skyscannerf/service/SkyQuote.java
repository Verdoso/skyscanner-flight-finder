package org.greeneyed.skyscannerf.service;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data class SkyQuote {
    @JsonProperty(value = "QuoteId")
    private String quoteId;

    @JsonProperty(value = "MinPrice")
    private String minPrice;

    @JsonProperty(value = "Direct")
    private boolean direct;

    @JsonProperty(value = "QuoteDateTime")
    private LocalDateTime date;

    @JsonProperty(value = "OutboundLeg")
    private SkyLeg outboundLeg;

    @JsonProperty(value = "InboundLeg")
    private SkyLeg inboundLeg;
}