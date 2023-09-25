package org.learning.java.springlibrary.repository;

import org.learning.java.springlibrary.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PizzaRepository extends JpaRepository<Pizza, Integer> {

    List<Pizza> findByName(String query);
}
