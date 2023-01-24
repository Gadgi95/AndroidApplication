package com.example.application.service;

import static com.example.application.util.validation.ValidationUtil.checkNotFoundWithId;

import com.example.application.core.Material;
import com.example.application.repository.MaterialRepository;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

//В этом классе прописывается более детально логика выполнения запросов в базу данных касаемо материалов,
// именно этот класс нужен для связи с графическим интерфейсом, здесь в каждом методе происходят проверки,
// чтобы избежать ошибки попадания не валидных данных в базу данных.
@Service
public class MaterialService {

    private final MaterialRepository materialRepository;

    public MaterialService(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    //Получение материала по имени из базы данных с проверкой существует ли такой ID материала в базе
    public Material get(int id, String name) {
        return checkNotFoundWithId(materialRepository.get(id, name), id);
    }

    //Удаление материала из базы данных с проверкой существует ли такой ID материала в базе
    public void delete(int id, int ticketId) {
        checkNotFoundWithId(materialRepository.delete(id, ticketId), id);
    }

    //Получение всего списка материалов
    public List<Material> getAll() {
        return materialRepository.getAll();
    }

    //Редактирование материала, проверка что он не равен нулю и проверка что такой ID материала в базе существует
    public void update(Material material) {
        Assert.notNull(material, "material must not be null");
        checkNotFoundWithId(materialRepository.save(material), material.id());
    }

    //Создание материала и проверка что он не равен нулю
    public Material create(Material material) {
        Assert.notNull(material, "material must not be null");
        return materialRepository.save(material);
    }
}
