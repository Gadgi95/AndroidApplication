package com.example.application.repository.datajpa;

import com.example.application.core.Material;
import com.example.application.core.Ticket;
import com.example.application.repository.TicketRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

//Этот класс просто реализует интерфейс и в нем прописывается дополнительно то
//что касается запросов (можно добавить нужные сортировки), если CrudTicketRepository,
//который реализует JpaRepository<User, Integer> не имеет данных методов по умолчанию.
@Repository
public class DataJpaTicketRepository implements TicketRepository {

    //Сортировка материалов по дате создания,
    //тоесть заявки будут выводиться в графический интерфейс в отсортированном порядке
    private static final Sort SORT_CREATION_DATE = Sort.by(Sort.Direction.ASC, "creationDate");

    private final CrudTicketRepository crudTicketRepository;

    public DataJpaTicketRepository(CrudTicketRepository crudTicketRepository) {
        this.crudTicketRepository = crudTicketRepository;
    }

    //Сохранение заявки в базу данных
    @Override
    public Ticket save(Ticket ticket) {
        if (!ticket.isNew()) {
            return null;
        }
        return crudTicketRepository.save(ticket);
    }

    //Удаление заявки из базы данных
    @Override
    public boolean delete(int id, int userId) {
        return crudTicketRepository.delete(id, userId) != 0;
    }

    //Получение заявки из базы данных
    @Override
    public Ticket get(int id, int userId) {
        if (crudTicketRepository.findById(id) == null) {
            return null;
        }
        return crudTicketRepository.findById(id);
    }

    //Получение всего списка заявок в отсортированом порядке по дате создания
    @Override
    public List<Ticket> getAll(int userId) {
        return crudTicketRepository.findAll(SORT_CREATION_DATE);
    }

    //Получение заявки с материалами внутри нее
    @Override
    public Ticket getWithMaterial(int id, int userId, List<Material> materialList) {
        return crudTicketRepository.getWithMaterial(id, userId, materialList);
    }
}
