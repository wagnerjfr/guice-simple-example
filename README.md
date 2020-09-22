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
set 19, 2020 1:30:57 PM repository.DatabaseModule configure
INFO: Setting up MySQL of type REAL
set 19, 2020 1:30:59 PM application.GuiceDemo sampleExecution
INFO: MySQL has 100 offers
set 19, 2020 1:30:59 PM application.GuiceDemo sampleExecution
INFO: Offer(tid=1, date=2020-09-19T13:30:57.991, amount=56, price=1044, type=SELL)
set 19, 2020 1:30:59 PM application.GuiceDemo sampleExecution
INFO: Offer(tid=2, date=2020-09-19T13:30:58.010, amount=99, price=1228, type=SELL)
set 19, 2020 1:30:59 PM application.GuiceDemo sampleExecution
INFO: Offer(tid=3, date=2020-09-19T13:30:58.010, amount=136, price=1132, type=BUY)
set 19, 2020 1:30:59 PM application.GuiceDemo sampleExecution
INFO: Offer(tid=4, date=2020-09-19T13:30:58.010, amount=187, price=1195, type=BUY)
set 19, 2020 1:30:59 PM application.GuiceDemo sampleExecution
INFO: Offer(tid=5, date=2020-09-19T13:30:58.010, amount=158, price=1060, type=SELL)
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
