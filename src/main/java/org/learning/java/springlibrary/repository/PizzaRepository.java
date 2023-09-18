package org.learning.java.springlibrary.repository;

import org.learning.java.springlibrary.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PizzaRepository extends JpaRepository<Pizza, Integer> {



}
