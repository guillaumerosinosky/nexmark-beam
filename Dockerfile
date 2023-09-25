# Build stage
FROM gradle:7.4.1-jdk11 AS build

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src

RUN gradle shadowJar -Pnexmark.runner=":runners:flink:1.16" --no-daemon --info

# List the contents of the JAR file and filter the results to show only the CustomNexmarkMain class
#RUN jar tf /home/gradle/src/build/libs/*.jar | grep CustomNexmarkMain

# Runtime stage
FROM openjdk:11-jdk

COPY --from=build /home/gradle/src/build/libs/nexmark-benchmark.jar /app/nexmark-benchmark.jar

ENTRYPOINT ["java", "-cp", "/app/nexmark-benchmark.jar", "be.uclouvain.gepiciad.benchmark.beam.nexmark.CustomNexmarkMain"]

CMD ["--help"]