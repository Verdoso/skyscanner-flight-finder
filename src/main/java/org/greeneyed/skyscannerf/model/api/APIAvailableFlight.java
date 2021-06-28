package org.greeneyed.skyscannerf.model.api;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class APIAvailableFlight {
    private String id;
    private BigDecimal minPrice;
    private boolean direct;
    private String origin;
    private String departureCarrier;
    private LocalDateTime departureDate;
    private String destination;
    private String returnCarrier;
    private LocalDateTime returnDate;
}