package com.ridango.game.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Drink {

    @Id
    private String idDrink;
    private String strDrink;
    private String strInstructions;


    public Drink() {

    }

    public Drink(String idDrink, String strDrink, String strInstructions) {
        this.idDrink = idDrink;
        this.strDrink = strDrink;
        this.strInstructions = strInstructions;
    }
}