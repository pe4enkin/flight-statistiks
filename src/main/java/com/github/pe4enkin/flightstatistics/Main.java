package com.github.pe4enkin.flightstatistics;

import com.github.pe4enkin.flightstatistics.model.Ticket;
import com.github.pe4enkin.flightstatistics.util.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("tickets.json");
        if (inputStream == null) {
            throw new RuntimeException("Файл tickets.json не найден в resources");
        }
        List<Ticket> tickets = new ArrayList<>();
        try {
            tickets= JsonReader.readTickets(inputStream);
        } catch (IOException e) {
            System.out.println("Неожиданная ошибка чтения: " + e.getMessage() + e);
        }
        for (Ticket ticket : tickets) {
            System.out.println(ticket);
        }
    }
}