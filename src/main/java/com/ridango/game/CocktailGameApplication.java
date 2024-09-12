package com.ridango.game;

import com.ridango.game.game.Game;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CocktailGameApplication implements CommandLineRunner {

    public CocktailGameApplication(Game game) {
		this.game = game;
    }

    public static void main(String[] args) {
		SpringApplication.run(CocktailGameApplication.class, args);
	}

	private final Game game;

	@Override public void run(String... args){
		game.play();
	}


}
