package org.greeneyed.skyscannerf.controller;

import java.time.LocalDate;
import java.util.List;

import org.greeneyed.skyscannerf.model.api.APIAvailableFlight;
import org.greeneyed.skyscannerf.service.SkyScannerService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@RestController
public class FlightAvailabilityController {

    private final SkyScannerService skyScannerService;

    @GetMapping(value = "/availability/{country}/{currency}/{locale}/{originId}/{destinationId}/{outboundDate}/{inboundDate}")
    public ResponseEntity<List<APIAvailableFlight>> availability(
            @PathVariable(value = "outboundDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate outboundDate,
            @PathVariable(value = "inboundDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inboundDate,
            @PathVariable(value = "country") String country,
            @PathVariable(value = "originId") String originId,
            @PathVariable(value = "destinationId") String destinationId,
            @PathVariable(value = "currency") String currency,
            @PathVariable(value = "locale") String locale) {
        final List<APIAvailableFlight> availability = skyScannerService.getAvailability(outboundDate, inboundDate, country, originId, destinationId, currency, locale);
        return ResponseEntity
                .ok(availability);
    }
}
