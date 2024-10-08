package com.ridango.game.service;

import com.ridango.game.constants.AppConstants;
import com.ridango.game.model.Drink;
import com.ridango.game.repo.DrinkRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class DrinkService {

    private final RestTemplate restTemplate;
    private final DrinkRepository drinkRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(DrinkService.class);


    public DrinkService(RestTemplate restTemplate, DrinkRepository drinkRepository) {
        this.restTemplate = restTemplate;
        this.drinkRepository = drinkRepository;
    }

    /**
     * Returns a new instance of {@link Drink} but only after verifying if the drink is not already in the Drink table in the database.
     * This method is called at the start of the application or whenever the player want to continue playing.
     *
     * @return a new {@link Drink}
     */
    public Drink getNewDrink() {
        String idDrink = "";
        String strDrink = "";
        String strInstructions = "";
        while (idDrink.isEmpty()) {
            LinkedHashMap<String, String> drinksJson = (LinkedHashMap<String, String>) ((Map<String, List<Object>>) restTemplate.getForObject(AppConstants.RANDOM_COCKTAIL_URI, Object.class)).get("drinks").get(0);
            String newIdDrink = drinksJson.get("idDrink");
            if (drinkRepository.findById(newIdDrink).isEmpty()) {
                idDrink = newIdDrink;
                strDrink = drinksJson.get("strDrink");
                strInstructions = drinksJson.get("strInstructions");
                LOGGER.info("New drink found!!");
            }else{
                LOGGER.info("Drink already exists. Trying to get another drink.");
            }
        }
        Drink newDrink = new Drink(idDrink, strDrink, strInstructions);
        boolean drinkSaved = false;
        while(!drinkSaved){
            try{
                drinkRepository.save(newDrink);
                LOGGER.info("New drink created and saved to db");
                drinkSaved = true;
            } catch (IllegalArgumentException e) {
                LOGGER.error("Could not save new drink", e);
            }
        }

        return newDrink;
    }

}
