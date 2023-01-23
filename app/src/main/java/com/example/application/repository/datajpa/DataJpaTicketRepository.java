package com.example.application.repository.datajpa;

import com.example.application.core.Material;
import com.example.application.core.Ticket;
import com.example.application.repository.TicketRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DataJpaTicketRepository implements TicketRepository {

    private static final Sort SORT_CREATION_DATE = Sort.by(Sort.Direction.ASC, "creationDate");

    private final CrudTicketRepository crudTicketRepository;

    public DataJpaTicketRepository(CrudTicketRepository crudTicketRepository) {
        this.crudTicketRepository = crudTicketRepository;
    }

    @Override
    public Ticket save(Ticket ticket) {
        if (!ticket.isNew()) {
            return null;
        }
        return crudTicketRepository.save(ticket);
    }

    @Override
    public boolean delete(int id, int userId) {
        return crudTicketRepository.delete(id, userId) != 0;
    }

    @Override
    public Ticket get(int id, int userId) {
        if (crudTicketRepository.findById(id) == null) {
            return null;
        }
        return crudTicketRepository.findById(id);
    }

    @Override
    public List<Ticket> getAll(int userId) {
        return crudTicketRepository.findAll(SORT_CREATION_DATE);
    }

    @Override
    public Ticket getWithMaterial(int id, int userId, List<Material> materialList) {
        return crudTicketRepository.getWithMaterial(id, userId, materialList);
    }
}
