package com.pokemon.controller;

import com.pokemon.content.BattleResult;
import com.pokemon.service.PokemonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PokemonController {

    private static final Logger logger = LoggerFactory.getLogger(PokemonController.class);

    private final PokemonService pokemonService;

    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @GetMapping("/attack")
    public ResponseEntity<BattleResult> attack(@RequestParam String pokemonAName,
                                               @RequestParam String pokemonBName) {
        logger.info("Requested pokemonA: {}, pokemonB: {}", pokemonAName, pokemonBName);

        final var pokemon1 = pokemonService.getPokemonByName(pokemonAName);
        final var pokemon2 = pokemonService.getPokemonByName(pokemonBName);

        final var winner = pokemonService.battle(pokemon1, pokemon2);
        return ResponseEntity.ok(new BattleResult(winner.getName(), winner.getHitPoints()));
    }
}
