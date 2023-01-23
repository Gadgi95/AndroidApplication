package com.example.application.service;

import static com.example.application.util.validation.ValidationUtil.checkNotFoundWithId;

import com.example.application.core.Ticket;
import com.example.application.repository.TicketRepository;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Ticket get(int id, int userId) {
        return checkNotFoundWithId(ticketRepository.get(id, userId), id);
    }

    public void delete(int id, int userId) {
        checkNotFoundWithId(ticketRepository.delete(id, userId), id);
    }

    public List<Ticket> getAll(int userId) {
        return ticketRepository.getAll(userId);
    }

    public void update(Ticket ticket) {
        Assert.notNull(ticket, "meal must not be null");
        checkNotFoundWithId(ticketRepository.save(ticket), ticket.id());
    }

    public Ticket create(Ticket ticket) {
        Assert.notNull(ticket, "meal must not be null");
        return ticketRepository.save(ticket);
    }
}
