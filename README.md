# guice-simple-example
Simple example using [Guice](https://github.com/google/guice) for Dependency Injection.

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
