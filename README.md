[![Build Status](https://travis-ci.org/wagnerjfr/guice-simple-example.svg?branch=master)](https://travis-ci.org/wagnerjfr/guice-simple-example)
[![codecov](https://codecov.io/gh/wagnerjfr/guice-simple-example/branch/master/graph/badge.svg)](https://codecov.io/gh/wagnerjfr/guice-simple-example)

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
abr 17, 2021 6:49:40 PM repository.DatabaseModule configure
INFO: Setting up MySQL of type REAL
abr 17, 2021 6:49:41 PM application.GuiceDemo sampleExecution
INFO: MySQL has 100 offers
abr 17, 2021 6:49:41 PM application.GuiceDemo sampleExecution
INFO: Offer(tid=1, date=2021-04-17T18:49:40.836, amount=186, price=1319, type=SELL)
abr 17, 2021 6:49:41 PM application.GuiceDemo sampleExecution
INFO: Offer(tid=2, date=2021-04-17T18:49:40.841, amount=154, price=1204, type=BUY)
abr 17, 2021 6:49:41 PM application.GuiceDemo sampleExecution
INFO: Offer(tid=3, date=2021-04-17T18:49:40.841, amount=32, price=1351, type=BUY)
abr 17, 2021 6:49:41 PM application.GuiceDemo sampleExecution
INFO: Offer(tid=4, date=2021-04-17T18:49:40.841, amount=81, price=1442, type=SELL)
abr 17, 2021 6:49:41 PM application.GuiceDemo sampleExecution
INFO: Offer(tid=5, date=2021-04-17T18:49:40.841, amount=59, price=1408, type=BUY)
```

## Key points
- pom.xml
```xml
        ...
        <!-- https://mvnrepository.com/artifact/com.google.inject/guice -->
        <dependency>
            <groupId>com.google.inject</groupId>
            <artifactId>guice</artifactId>
            <version>5.0.1</version>
        </dependency>
    </dependencies>
```
- `Database` Interface:
```java
public interface Database {

    void add(Pair pair, Transaction offer);

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
