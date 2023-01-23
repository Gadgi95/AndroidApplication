package com.example.application.repository.datajpa;

import com.example.application.core.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CrudMaterialRepository extends JpaRepository<Material, Integer> {

    @Modifying
    @Query("INSERT INTO Material (name, quantity, characteristics) SELECT mt.name, mt.quantity, mt.characteristics FROM MaterialTo mt")
    Material save(Material entity);

    @Modifying
    @Query("DELETE FROM Material m WHERE m.id=:id AND m.ticket.id=:ticketId")
    int delete(@Param("id") int id, @Param("ticketId") int ticketId);

    @Query("SELECT t.name as 'Название заявки', t.creationDate as 'Дата создания', t.status as 'Статус', m.name as 'Название материала', m.quantity as 'Количество материала'\n" +
            "FROM materials m RIGHT JOIN tickets t ON m.ticket_id = t.id WHERE m.id=:id AND m.name=:name")
    Material findByName(@Param("id") int id, @Param("name") String name);
}
