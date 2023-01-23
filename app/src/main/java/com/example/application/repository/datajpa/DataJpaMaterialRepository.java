package com.example.application.repository.datajpa;

import com.example.application.core.Material;
import com.example.application.repository.MaterialRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DataJpaMaterialRepository implements MaterialRepository {

    private static final Sort SORT_NAME = Sort.by(Sort.Direction.ASC, "name");

    CrudMaterialRepository crudMaterialRepository;

    public DataJpaMaterialRepository(CrudMaterialRepository crudMaterialRepository) {
        this.crudMaterialRepository = crudMaterialRepository;
    }

    @Override
    public Material save(Material material) {
        return crudMaterialRepository.save(material);
    }

    @Override
    public boolean delete(int id, int ticketId) {
        return crudMaterialRepository.delete(id, ticketId) != 0;
    }

    @Override
    public Material get(int id, String name) {
        return crudMaterialRepository.findByName(id, name);
    }

    @Override
    public List<Material> getAll() {
        return crudMaterialRepository.findAll(SORT_NAME);
    }
}
