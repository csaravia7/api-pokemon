# Pokemon API Client (Java)

Microservicio Spring Boot para consumir la API pública de Pokémon (PokeAPI).

## Requisitos
- Java 17+
- Maven
- Docker

## Ejecutar localmente

1. Abrir terminal en el directorio del proyecto
2. Compilar:
   ```bash
   mvn clean package
   ```
3. Ejecutar:
   ```bash
   java -jar target/pokemon-api-client.jar
   ```
4. Probar endpoints:
   ```bash
   curl http://127.0.0.1:8081/health
   curl http://127.0.0.1:8081/pokemon
   curl http://127.0.0.1:8081/pokemon/pikachu
   ```

## Construir imagen Docker

Puedes usar el script de ayuda:

```bash
./scripts/docker-build.sh
```

O ejecutar el comando directamente:

```bash
docker build -t rasec777/pokemon-api-client:1.0.1 .
```

Ese paso es solo para uso local. El `docker build` tradicional genera una imagen de una sola arquitectura, normalmente la de tu máquina.

## Publicar imagen Docker

Para que Kubernetes no falle al descargar la imagen, el tag del YAML debe existir en un registry accesible por el cluster y contener una plataforma compatible con sus nodos.

```bash
docker login
./scripts/docker-push.sh
```

El script de publicación usa `docker buildx build --push` y publica una imagen multi-plataforma por defecto:

```bash
IMAGE_PLATFORMS=linux/amd64,linux/arm64 ./scripts/docker-push.sh
```

Si tu cluster usa solo `amd64`, también puedes publicar únicamente esa plataforma:

```bash
IMAGE_PLATFORMS=linux/amd64 ./scripts/docker-push.sh
```

## Desplegar en Kubernetes

Una vez publicada la imagen:

```bash
kubectl apply -f k8s/api-pokemon.yaml
```

## API usada
- https://pokeapi.co/api/v2/pokemon/{name_or_id}
