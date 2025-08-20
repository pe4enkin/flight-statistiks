package com.github.pe4enkin.flightstatistics.service;

import com.github.pe4enkin.flightstatistics.model.Ticket;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    public static BigDecimal calculateAveragePrice(List<Ticket> tickets, String origin, String destination) {
        List<Integer> prices = getPricesForRoute(tickets, origin, destination);
        if (prices.isEmpty()) {
            return BigDecimal.ZERO;
        }
        int sum = prices.stream()
                .mapToInt(Integer::intValue)
                .sum();
        BigDecimal result = BigDecimal.valueOf(sum)
                .divide(BigDecimal.valueOf(prices.size()), 2, RoundingMode.HALF_UP);
        return result;
    }

    public static BigDecimal calculateMedianPrice(List<Ticket> tickets, String origin, String destination) {
        List<Integer> sortPrices = getPricesForRoute(tickets, origin, destination).stream()
                .sorted()
                .toList();
        if (sortPrices.isEmpty()) {
            return BigDecimal.ZERO;
        }
        int size = sortPrices.size();
        if (size % 2 == 1) {
            return BigDecimal.valueOf(sortPrices.get(size / 2));
        } else {
            return BigDecimal.valueOf(sortPrices.get(size / 2 - 1))
                    .add(BigDecimal.valueOf(sortPrices.get(size / 2)))
                    .divide(BigDecimal.valueOf(2), 2, RoundingMode.HALF_UP);
        }
    }

    private static Duration calculateFlightDuration(Ticket ticket) {
        return Duration.between(LocalDateTime.of(ticket.getDepartureDate(), ticket.getDepartureTime()),
                LocalDateTime.of(ticket.getArrivalDate(), ticket.getArrivalTime()));
    }

    private static List<Integer> getPricesForRoute(List<Ticket> tickets, String origin, String destination) {
        return tickets.stream()
                .filter(ticket -> ticket.getOrigin().equals(origin) && ticket.getDestination().equals(destination))
                .map(Ticket::getPrice)
                .toList();
    }
}