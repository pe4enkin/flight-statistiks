package com.github.pe4enkin.flightstatistics.service;

import com.github.pe4enkin.flightstatistics.model.Ticket;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TicketService {

    public static Map<String, Duration> calculateMinFlightDurationByCarrier(List<Ticket> tickets,String origin, String destination) {
        Map<String, Duration> durationMap =new HashMap<>();
        for (Ticket ticket : tickets) {
            if (ticket.getOrigin().equals(origin) && ticket.getDestination().equals(destination)) {
                String carrier = ticket.getCarrier();
                Duration flightDuration = calculateFlightDuration(ticket);
                if (!durationMap.containsKey(carrier) || flightDuration.compareTo(durationMap.get(carrier)) < 0) {
                    durationMap.put(carrier, flightDuration);
                }
            }
        }
        return durationMap;
    }

    private static Duration calculateFlightDuration(Ticket ticket) {
        return Duration.between(LocalDateTime.of(ticket.getDepartureDate(), ticket.getDepartureTime()),
                LocalDateTime.of(ticket.getArrivalDate(), ticket.getArrivalTime()));
    }
}
