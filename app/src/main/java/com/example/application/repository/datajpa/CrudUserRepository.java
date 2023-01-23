package com.example.application.repository.datajpa;

import com.example.application.model.User;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface CrudUserRepository extends JpaRepository<User, Integer> {

    @Modifying
    @Query("INSERT INTO User (name, email, password) SELECT ut.name, ut.email, ut.password FROM UserTo ut")
    User save(User entity);

    @Modifying
    @Query("DELETE FROM User u WHERE u.id=:id")
    int delete(@Param("id") int id);

    @Query("SELECT u FROM User u WHERE u.id=:id")
    User findById(@Param("id") int id);

    User getByEmail(String email);

    @EntityGraph(attributePaths = {"tickets"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT u FROM User u WHERE u.id=?1")
    User getWithTickets(int id);
}
