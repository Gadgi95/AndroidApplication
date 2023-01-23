package com.example.application.service;

import static com.example.application.util.validation.ValidationUtil.checkNotFoundWithId;

import com.example.application.core.Material;
import com.example.application.repository.MaterialRepository;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class MaterialService {

    private final MaterialRepository materialRepository;

    public MaterialService(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    public Material get(int id, String name) {
        return checkNotFoundWithId(materialRepository.get(id, name), id);
    }

    public void delete(int id, int ticketId) {
        checkNotFoundWithId(materialRepository.delete(id, ticketId), id);
    }

    public List<Material> getAll() {
        return materialRepository.getAll();
    }

    public void update(Material material) {
        Assert.notNull(material, "meal must not be null");
        checkNotFoundWithId(materialRepository.save(material), material.id());
    }

    public Material create(Material material) {
        Assert.notNull(material, "meal must not be null");
        return materialRepository.save(material);
    }
}
