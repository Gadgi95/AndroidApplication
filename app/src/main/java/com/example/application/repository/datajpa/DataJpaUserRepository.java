package com.example.application.repository.datajpa;

import com.example.application.model.User;
import com.example.application.repository.UserRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

//Этот класс просто реализует интерфейс и в нем прописывается дополнительно то
//что касается запросов (можно добавить нужные сортировки), если CrudUserRepository,
//который реализует JpaRepository<User, Integer> не имеет данных методов по умолчанию.
@Repository
public class DataJpaUserRepository implements UserRepository {

    //Сортировка пользователей по имени и мылу,
    //тоесть заявки будут выводиться в графический интерфейс в отсортированном порядке
    private static final Sort SORT_NAME_EMAIL = Sort.by(Sort.Direction.ASC, "name", "email");

    private final CrudUserRepository crudRepository;

    public DataJpaUserRepository(CrudUserRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    //Сохранение пользователя в базу данных
    @Override
    public User save(User user) {
        return crudRepository.save(user);
    }

    //Удаление пользователя из базы данных
    @Override
    public boolean delete(int id) {
        return crudRepository.delete(id) != 0;
    }

    //Получение пользователя из базы данных
    @Override
    public User get(int id) {
        if (crudRepository.findById(id) == null) {
            return null;
        }
        return crudRepository.findById(id);
    }

    //Получение пользователя по мылу из базы данных
    @Override
    public User getByEmail(String email) {
        return crudRepository.getByEmail(email);
    }

    //Получение всего списка пользователей в отсортированом порядке по имени и мылу
    @Override
    public List<User> getAll() {
        return crudRepository.findAll(SORT_NAME_EMAIL);
    }

    //Получение пользователя с заявками внутри него
    @Override
    public User getWithTickets(int id) {
        return crudRepository.getWithTickets(id);
    }
}
