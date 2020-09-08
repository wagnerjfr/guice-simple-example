package repository;

import com.google.inject.AbstractModule;
import lombok.extern.java.Log;

@Log
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

    public enum Configuration {
        REAL, FAKE
    }
}