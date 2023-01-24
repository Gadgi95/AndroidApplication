package com.example.application.repository.datajpa;

import com.example.application.core.Material;
import com.example.application.repository.MaterialRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

//Этот класс просто реализует интерфейс и в нем прописывается дополнительно то
//что касается запросов (можно добавить нужные сортировки), если CrudMaterialRepository,
//который реализует JpaRepository<User, Integer> не имеет данных методов по умолчанию.
@Repository
public class DataJpaMaterialRepository implements MaterialRepository {

    //Сортировка материалов по имени,
    //тоесть материалы будут выводиться в графический интерфейс в отсортированном порядке
    private static final Sort SORT_NAME = Sort.by(Sort.Direction.ASC, "name");

    CrudMaterialRepository crudMaterialRepository;

    public DataJpaMaterialRepository(CrudMaterialRepository crudMaterialRepository) {
        this.crudMaterialRepository = crudMaterialRepository;
    }

    //Сохранение материала в базу данных
    @Override
    public Material save(Material material) {
        return crudMaterialRepository.save(material);
    }

    //Удаление материала из базы данных
    @Override
    public boolean delete(int id, int ticketId) {
        return crudMaterialRepository.delete(id, ticketId) != 0;
    }

    //Получение материала по имени из базы данных
    @Override
    public Material get(int id, String name) {
        return crudMaterialRepository.findByName(id, name);
    }

    //Получение всего списка материалов в отсортированом порядке по имени
    @Override
    public List<Material> getAll() {
        return crudMaterialRepository.findAll(SORT_NAME);
    }
}
