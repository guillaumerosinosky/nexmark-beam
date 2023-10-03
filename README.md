# nexmark-beam

Containerized Nexmark Beam with some additional customization.

The goal of this package is to be able to launch instances of Nexmark Beam in containerized environments such as Kubernetes or Docker Swarm/Compose, for repeatable benchmarking purposes.

Additional features are planned such as the possibility to set custom parallelism for each group of operators.

## Deployment

Using docker-compose, deploy Kafka, Kafka UI and Apache Flink:

```bash
docker-compose up
```

Flink listens to `localhost:8081`, Kafka UI to `localhost:8082`.

## Debug

With vscode, launch one of the debug roles assuming everything has been deployed using Docker Compose. Tested with OpenJDK 17.0.8.1.

FIXME: In debug mode, the directory of the jar job should be created (for instance `/tmp/Query5` or `/tmp/SqlQuery3`) depending on the `--query` and `--queryLanguage` arguments.
