# fizzbuzz-service

```sh
./mvnw spring-boot:run
```

```sh
FIZZBUZZ_FIXEDVALUE=fizz SERVER_PORT=8180 ./mvnw spring-boot:run
```

```sh
FIZZBUZZ_FIXEDVALUE=buzz SERVER_PORT=8280 ./mvnw spring-boot:run
```

```sh
for i in {1..15}; do curl -s localhost:8080/$i; echo ""; done
```

## Native Image

```sh
./mvnw spring-boot:build-image
```

```sh
docker run fizzbuzz:0.0.1-SNAPSHOT
```

```sh
./mvnw -Pnative -DskipTests package
```

```sh
./target/fizzbuzz-service
```

