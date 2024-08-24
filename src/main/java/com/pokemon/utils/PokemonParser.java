package com.pokemon.utils;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.pokemon.content.Pokemon;
import com.pokemon.content.PokemonType;
import org.springframework.core.io.ClassPathResource;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PokemonParser {

    public static List<Pokemon> parseCSV(String resourcePath) throws IOException, CsvException {
        List<Pokemon> pokemonList = new ArrayList<>();

        final var file = new ClassPathResource(resourcePath).getFile();

        try (final var reader = new CSVReader(new FileReader(file))) {
            List<String[]> records = reader.readAll();
            for (int i = 1; i < records.size(); i++) { // Start from 1 to skip header
                String[] fields = records.get(i);

                int id = Integer.parseInt(fields[0].trim());
                String name = fields[1].trim();
                final var type = PokemonType.valueOf(fields[2].trim().toUpperCase());
                int total = Integer.parseInt(fields[3].trim());
                int hitPoints = Integer.parseInt(fields[4].trim());
                int attack = Integer.parseInt(fields[5].trim());
                int defense = Integer.parseInt(fields[6].trim());
                int specialAttack = Integer.parseInt(fields[7].trim());
                int specialDefense = Integer.parseInt(fields[8].trim());
                int speed = Integer.parseInt(fields[9].trim());
                int generation = Integer.parseInt(fields[10].trim());
                boolean legendary = Boolean.parseBoolean(fields[11].trim());

                final var pokemon = new Pokemon(id, name, type, total, hitPoints, attack, defense,
                                                specialAttack, specialDefense, speed, generation, legendary);
                pokemonList.add(pokemon);
            }
        }
        return pokemonList;
    }
}
