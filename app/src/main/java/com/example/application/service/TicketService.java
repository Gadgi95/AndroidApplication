package com.example.application.service;

import static com.example.application.util.validation.ValidationUtil.checkNotFoundWithId;

import com.example.application.core.Ticket;
import com.example.application.repository.TicketRepository;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

//В этом классе прописывается более детально логика выполнения запросов в базу данных касаемо заявок,
// именно этот класс нужен для связи с графическим интерфейсом, здесь в каждом методе происходят проверки,
//// чтобы избежать ошибки попадания не валидных данных в базу данных.
@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    //Получение заявки пользователя из базы данных с проверкой существует ли такой ID заявки в базе
    public Ticket get(int id, int userId) {
        return checkNotFoundWithId(ticketRepository.get(id, userId), id);
    }

    //Удаление заявки пользователя из базы данных с проверкой существует ли такой ID заявки в базе
    public void delete(int id, int userId) {
        checkNotFoundWithId(ticketRepository.delete(id, userId), id);
    }

    //Получение всего списка заявок пользователя
    public List<Ticket> getAll(int userId) {
        return ticketRepository.getAll(userId);
    }

    //Редактирование заявки, проверка что она не равена нулю и проверка что такой ID заявки в базе существует
    public void update(Ticket ticket) {
        Assert.notNull(ticket, "ticket must not be null");
        checkNotFoundWithId(ticketRepository.save(ticket), ticket.id());
    }

    //Создание заявки и проверка что она не равена нулю
    public Ticket create(Ticket ticket) {
        Assert.notNull(ticket, "ticket must not be null");
        return ticketRepository.save(ticket);
    }
}
