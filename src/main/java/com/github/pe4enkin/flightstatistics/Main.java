package com.github.pe4enkin.flightstatistics;

import com.github.pe4enkin.flightstatistics.model.Ticket;
import com.github.pe4enkin.flightstatistics.service.TicketService;
import com.github.pe4enkin.flightstatistics.util.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        List<Ticket> tickets;

        try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("tickets.json")) {
            if (inputStream == null) {
                throw new RuntimeException("Файл tickets.json не найден в resources.");
        }
            tickets = JsonReader.readTickets(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Неожиданная ошибка чтения.", e);
        }

        Map<String, Duration> durationMap = TicketService.calculateMinFlightDurationByCarrier(tickets, "VVO", "TLV");
        System.out.printf("%-10s %-15s %-15s %-10s\n", "Перевозчик", "Отправление", "Назначение", "Длительность");
        durationMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(entry -> {
                    String flightTime = entry.getValue().toHours() + "ч. " + entry.getValue().toMinutesPart() + "м.";
                    System.out.printf("%-10s %-15s %-15s %-10s\n", entry.getKey(), "Владивосток", "Тель-Авив", flightTime);
                });
    }
}