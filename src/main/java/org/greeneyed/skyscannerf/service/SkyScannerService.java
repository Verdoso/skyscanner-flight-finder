package org.greeneyed.skyscannerf.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.greeneyed.skyscannerf.configuration.CommunicationsConfiguration;
import org.greeneyed.skyscannerf.mappers.MapperService;
import org.greeneyed.skyscannerf.model.api.APIAvailableFlight;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Service
public class SkyScannerService {

    private final MapperService mapperService;

    @Resource(name = CommunicationsConfiguration.SKYSCANNER_API_REST_TEMPLATE)
    private RestTemplate restTemplate;

    public List<APIAvailableFlight> getAvailability(LocalDate outboundDate, LocalDate inboundDate, String country,
            String originId, String destinationId, String currency, String locale) {
        List<APIAvailableFlight> availableFlights = new ArrayList<>();
        try {
            SkyResponse skyResponse = restTemplate.getForEntity(
                    "/apiservices/browsequotes/v1.0/{country}/{currency}/{locale}/{originId}/{destinationId}/{outboundDate}/{inboundDate}",
                    SkyResponse.class, country, currency, locale, originId, destinationId,
                    outboundDate.format(DateTimeFormatter.ISO_LOCAL_DATE),
                    inboundDate.format(DateTimeFormatter.ISO_LOCAL_DATE)).getBody();
            if (skyResponse != null) {
                Map<String, String> carriers = skyResponse.getCarriers()
                        .stream()
                        .collect(Collectors.toMap(SkyCarrier::getCarrierId, SkyCarrier::getName));
                Map<String, SkyPlace> places = skyResponse.getPlaces()
                        .stream()
                        .collect(Collectors.toMap(SkyPlace::getPlaceId, Function.identity()));
                for (SkyQuote quote : skyResponse.getQuotes()) {
                    SkyPlace destinationPlace = places.get(quote.getOutboundLeg().getDestinationId());
                    String destination = String.format("(%s) %s - %s", destinationPlace.getIataCode(),
                            destinationPlace.getCityName(), destinationPlace.getCountryName());
                    SkyPlace originPlace = places.get(quote.getInboundLeg().getDestinationId());
                    String origin = String.format("(%s) %s - %s", originPlace.getIataCode(), originPlace.getCityName(),
                            originPlace.getCountryName());
                    String departureCarrier = quote.getOutboundLeg()
                            .getCarrierIds()
                            .stream()
                            .map(carriers::get)
                            .collect(Collectors.joining(", "));
                    String returnCarrier = quote.getInboundLeg()
                            .getCarrierIds()
                            .stream()
                            .map(carriers::get)
                            .collect(Collectors.joining(", "));
                    availableFlights.add(APIAvailableFlight.builder()
                            .id(quote.getQuoteId())
                            .minPrice(new BigDecimal(quote.getMinPrice()))
                            .direct(quote.isDirect())
                            .departureCarrier(departureCarrier)
                            .returnCarrier(returnCarrier)
                            .departureDate(quote.getOutboundLeg().getDate())
                            .returnDate(quote.getInboundLeg().getDate())
                            .destination(destination)
                            .origin(origin)
                            .build());
                }
            }
            log.debug("skyResponse: {}", skyResponse);
        } catch (ResourceAccessException e) {
            log.error("Error reading Hotel availability response: {}", e.getMessage());
        } catch (HttpClientErrorException e) {
            log.error("Http Error Hotel availability response {}: {}", e.getStatusCode(), e.getStatusText());
        }
        return availableFlights;
    }
}
