package com.pokemon.service;

import com.opencsv.exceptions.CsvException;
import com.pokemon.content.Pokemon;
import com.pokemon.utils.PokemonParser;
import com.pokemon.content.PokemonType;
import com.pokemon.exceptions.PokemonExceptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.Random;

@Service
public class PokemonService {

    private static final Logger logger = LoggerFactory.getLogger(PokemonService.class);

    private List<Pokemon> pokemonList;
    private final Random random = new Random();

    @PostConstruct
    public void init() {
        try {
            this.pokemonList = PokemonParser.parseCSV("data/pokemon.csv");
            logger.debug("Successfully loaded {} Pokémon from CSV.", pokemonList.size());
        } catch (IOException | CsvException e) {
            logger.error("Failed to load Pokemon data.", e);
        }
    }

    public Pokemon getPokemonByName(String name) {
        return pokemonList.stream()
                          .filter(p -> p.getName().equalsIgnoreCase(name))
                          .findFirst()
                          .orElseThrow(
                                  () -> new PokemonExceptions("Pokemon with name '" + name + "' not found."));
    }

    public Pokemon battle(Pokemon pokemonA, Pokemon pokemonB) {
        Pokemon first;
        Pokemon second;

        if (pokemonA.getSpeed() != pokemonB.getSpeed()) {
            first = pokemonA.getSpeed() > pokemonB.getSpeed() ? pokemonA : pokemonB;
        } else {
            // Randomly decide who goes first if speeds are equal
            first = random.nextBoolean() ? pokemonA : pokemonB;
        }
        second = first == pokemonA ? pokemonB : pokemonA;

        logger.debug("Battle starts between {} and {}.", pokemonA.getName(), pokemonB.getName());

        // Battle loop
        while (pokemonA.getHitPoints() > 0 && pokemonB.getHitPoints() > 0) {
            attack(first, second);
            if (second.getHitPoints() > 0) {
                attack(second, first);
            }
        }

        Pokemon winner = pokemonA.getHitPoints() > 0 ? pokemonA : pokemonB;
        logger.debug("The battle is over. The winner is {}.", winner.getName());
        return winner;
    }

    private void attack(Pokemon attacker, Pokemon defender) {
        double effectiveness = getEffectiveness(attacker.getType(), defender.getType());
        double attackDefenseRatio = attacker.getAttack() / (double) defender.getDefense();
        int damage = (int) (50 * attackDefenseRatio * effectiveness);
        defender.setHitPoints(defender.getHitPoints() - damage);

        logger.debug("{} attacks {} causing {} damage. {} now has {} HP.",
                     attacker.getName(), defender.getName(), damage, defender.getName(), defender.getHitPoints());
    }

    public double getEffectiveness(PokemonType attacker, PokemonType defender) {
        if (attacker == defender) {
            return 1.0; // Neutral effectiveness if types are the same
        }

        switch (attacker) {
            case FIRE:
                if (defender == PokemonType.GRASS) {
                    return 2.0;
                }
                if (defender == PokemonType.WATER) {
                    return 0.5;
                }
                if (defender == PokemonType.ELECTRIC) {
                    return 1.0;
                }

            case WATER:
                if (defender == PokemonType.FIRE) {
                    return 2.0;
                }
                if (defender == PokemonType.ELECTRIC) {
                    return 0.5;
                }
                if (defender == PokemonType.GRASS) {
                    return 1.0;
                }

            case GRASS:
                if (defender == PokemonType.ELECTRIC) {
                    return 2.0;
                }
                if (defender == PokemonType.FIRE) {
                    return 0.5;
                }
                if (defender == PokemonType.WATER) {
                    return 1.0;
                }

            case ELECTRIC:
                if (defender == PokemonType.WATER) {
                    return 2.0;
                }
                if (defender == PokemonType.GRASS) {
                    return 0.5;
                }
                if (defender == PokemonType.FIRE) {
                    return 1.0;
                }

            default:
                return 1.0; // Default to neutral if types don't match any case
        }
    }
}
