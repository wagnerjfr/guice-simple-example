[![codecov](https://codecov.io/gh/wagnerjfr/guice-simple-example/branch/master/graph/badge.svg)](https://codecov.io/gh/wagnerjfr/guice-simple-example)

# Simplifying Java Development Through Dependency Injection

This guide introduces Guice, a framework streamlining the Dependency Injection (DI) pattern in Java development. The guide includes a simple example, guiding users through project setup and essential steps. It explains Dependency Injection and how Guice simplifies this pattern.

## Full article
### [Simplifying Java Development Through Dependency Injection](https://levelup.gitconnected.com/simplifying-java-development-through-dependency-injection-6cae567e53fd)
_A Simple Example Using Guice for Dependency Injection_

## Project overview

This is a small Java application that demonstrates **Dependency Injection (DI)** with
Google Guice by wiring a `Database` interface to different implementations:

- `MySQL` (simulated “real” implementation with small latency)
- `FakeMySQL` (fast in-memory implementation for tests)

The app generates random offers and persists/reads them through a DI-injected
`DatabaseClient`.

## Requirements

- Java **17+**
- Maven **3.9+**

## Common commands

Run tests:

```bash
mvn test
```

Build runnable jar:

```bash
mvn clean package
```

Run the demo app:

```bash
java -jar target/guice_simple_example-1.0-SNAPSHOT.jar
```

## DI wiring

Binding is configured in `repository/DatabaseModule.java`:

- `Configuration.REAL` -> `MySQL`
- `Configuration.FAKE` -> `FakeMySQL`

`application/GuiceDemo` currently starts with `REAL`, while tests can use `FAKE`
for deterministic and faster execution.

