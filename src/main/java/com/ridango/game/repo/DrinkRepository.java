package com.ridango.game.repo;

import com.ridango.game.model.Drink;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository layer for {@link Drink}. Responsible for interacting with the databse for the Drink table.
 */
@Repository
public interface DrinkRepository extends CrudRepository<Drink, String> {
}
