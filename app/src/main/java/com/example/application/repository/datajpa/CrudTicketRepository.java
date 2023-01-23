package com.example.application.repository.datajpa;

import com.example.application.core.Material;
import com.example.application.core.Ticket;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CrudTicketRepository extends JpaRepository<Ticket, Integer> {

    @Modifying
    @Query("INSERT INTO Ticket (user_id, name, status) SELECT tt.user_id, tt.name, tt.status FROM TicketTo tt")
    Ticket save(Ticket entity);

    @Modifying
    @Query("DELETE FROM Ticket t WHERE t.id=:id AND t.user.id=:id")
    int delete(@Param("id") int id, @Param("user_id") int user_id);

    @Query("SELECT t FROM Ticket t WHERE t.id=:id")
    Ticket findById(@Param("id") int id);

    @EntityGraph(attributePaths = {"tickets_materials"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT t FROM Ticket t INNER JOIN t.materials m WHERE t.id=:id AND t.user.id=:userId AND t.id=m.ticket.id")
    Ticket getWithMaterial(@Param("id") int id, @Param("userId") int userId, List<Material> materialList);
}
