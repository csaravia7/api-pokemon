# Pokemon API Client (Java)

Ejemplo de cliente Java para consumir la API pública de Pokémon (PokeAPI).

## Requisitos
- Java 17+
- Maven

## Ejecutar

1. Abrir terminal en el directorio del proyecto
2. Compilar:
   ```bash
   mvn clean package
   ```
3. Ejecutar:
   ```bash
   java -jar target/pokemon-api-client-1.0.0-shaded.jar
   ```
4. Ingresar nombre o id de Pokémon (ej. `pikachu` o `25`).

## API usada
- https://pokeapi.co/api/v2/pokemon/{name_or_id}
