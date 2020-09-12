<!--[![Build Status](https://travis-ci.org/wagnerjfr/guice-simple-example.svg?branch=travis-ci)](https://travis-ci.org/wagnerjfr/guice-simple-example)
[![codecov](https://codecov.io/gh/wagnerjfr/guice-simple-example/branch/travis-ci/graph/badge.svg?token=D3IQCDGQHS)](https://codecov.io/gh/wagnerjfr/guice-simple-example)-->

# guice-simple-example
Simple example using [Guice](https://github.com/google/guice) for Dependency Injection.

## Download
Clone:
```
$ git clone https://github.com/wagnerjfr/guice-simple-example.git

$ cd guice-simple-example
```
Build:
```
$ mvn clean package
```
Run:
```
$ java -jar target/guice_simple_example-1.0-SNAPSHOT.jar
```
Output:
```
set 12, 2020 5:31:44 PM repository.DatabaseModule configure
INFO: Setting up MySQL of type REAL
set 12, 2020 5:31:55 PM main.GuiceDemo run
INFO: MySQL has 1000 transactions
```

## Key points
- pom.xml
```xml
        ...
        <!-- https://mvnrepository.com/artifact/com.google.inject/guice -->
        <dependency>
            <groupId>com.google.inject</groupId>
            <artifactId>guice</artifactId>
            <version>4.2.0</version>
        </dependency>
    </dependencies>
```
- `Database` Interface:
```java
public interface Database {

    void add(Pair pair, Transaction transaction);

    List<Transaction> getAllTransactions(Pair pair);

    Optional<Transaction> getTransaction(String tid);
}
```
- `MySQL` class as `Database` implementation:
```java
class MySQL implements Database
...
```
- Binding Module:
```java
public class DatabaseModule extends AbstractModule {

    private Configuration configuration;

    public DatabaseModule(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    protected void configure() {
        log.info(String.format("Setting up MySQL of type %s", configuration));
        if (configuration.equals(Configuration.REAL)) {
            bind(Database.class).to(MySQL.class);
        } else {
            bind(Database.class).to(FakeMySQL.class);
        }
    }
    ...
```
- `DatabaseClient` which has `@Inject` Database
```java
public class DatabaseClient {

    private Database database;

    @Inject
    public DatabaseClient(Database database) {
        this.database = database;
    }
    ...
```
- Create Injector in `GuiceDemo`
```java
        ...
        Injector injector = Guice.createInjector(new DatabaseModule(REAL));
        DatabaseClient client = injector.getInstance(DatabaseClient.class);
        ...
```
- Create Injector in `MySQLTest`
```java
        ...
        Injector injector = Guice.createInjector(new DatabaseModule(FAKE));
        client = injector.getInstance(DatabaseClient.class);
        ...
```
