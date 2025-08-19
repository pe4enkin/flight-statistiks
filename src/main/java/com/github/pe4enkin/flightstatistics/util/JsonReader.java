package com.github.pe4enkin.flightstatistics.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.pe4enkin.flightstatistics.model.Ticket;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class JsonReader {

    private static class TicketsWrapper {
        private List<Ticket> tickets;

        public List<Ticket> getTickets() {
            return tickets;
        }

        public void setTickets(List<Ticket> tickets) {
            this.tickets = tickets;
        }
    }

    public static List<Ticket> readTickets(InputStream inputStream) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        TicketsWrapper wrapper = mapper.readValue(inputStream, TicketsWrapper.class);
        return wrapper.getTickets();
    }
}