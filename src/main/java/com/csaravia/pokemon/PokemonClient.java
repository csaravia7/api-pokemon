package com.csaravia.pokemon;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@SpringBootApplication
public class PokemonClient {
    public static void main(String[] args) {
        SpringApplication.run(PokemonClient.class, args);
    }

    @RestController
    @RequestMapping("/pokemon")
    public static class PokemonControllerv1 {

        private static final Logger logger = LogManager.getLogger(PokemonControllerv1.class);

        private static final String API_BASE = "https://pokeapi.co/api/v2/pokemon/";
        private final HttpClient httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        private final ObjectMapper objectMapper = new ObjectMapper();

        @GetMapping("/{nameOrId}")
        public ResponseEntity<String> getPokemon(@PathVariable("nameOrId") String nameOrId) {
            logger.info("Recibida solicitud para Pokémon: {}", nameOrId);
            try {
                String json = fetchPokemon(nameOrId);
                logger.info("Pokémon {} obtenido exitosamente", nameOrId);
                return ResponseEntity.ok(json);
            } catch (IOException e) {
                logger.error("Error al llamar API externa para Pokémon {}: {}", nameOrId, e.getMessage(), e);
                return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Error al llamar API externa: " + e.getMessage());
            } catch (InterruptedException e) {
                logger.warn("Solicitud interrumpida para Pokémon {}", nameOrId, e);
                Thread.currentThread().interrupt();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Solicitud interrumpida");
            }
        }

        private String fetchPokemon(String nameOrId) throws IOException, InterruptedException {
            String url = API_BASE + nameOrId.toLowerCase().trim();
            logger.debug("URL construida para API: {}", url);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(10))
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            logger.debug("Enviando solicitud HTTP a {}", url);
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            logger.debug("Respuesta HTTP recibida: status {}", response.statusCode());
            if (response.statusCode() != 200) {
                logger.warn("Respuesta no exitosa de API: status {}, body: {}", response.statusCode(), response.body());
                throw new IOException("HTTP " + response.statusCode() + " - " + response.body());
            }
            JsonNode json = objectMapper.readTree(response.body());
            logger.debug("JSON parseado exitosamente para {}", nameOrId);
            String jsonString = json.toString();
            logger.info("Retornando JSON de {} caracteres para {}", jsonString.length(), nameOrId);
            // Optional: map/filtrar campos si necesitas solo subset
            return jsonString;
        }
    }
}
