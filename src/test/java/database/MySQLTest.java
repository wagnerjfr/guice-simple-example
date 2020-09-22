package database;

import com.google.inject.Guice;
import com.google.inject.Injector;
import domain.Offer;
import domain.Pair;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import repository.DatabaseClient;
import repository.DatabaseModule;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static repository.DatabaseModule.Configuration.FAKE;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MySQLTest {

    private static final String TID = "1";

    private DatabaseClient client;

    @BeforeAll
    void beforeAll() {
        Injector injector = Guice.createInjector(new DatabaseModule(FAKE));
        client = injector.getInstance(DatabaseClient.class);
    }

    @Test
    @Order(1)
    void addTransactionTest() {
        Pair pair = Pair.BTC_USD;
        Offer t1 = new Offer(TID, "12334", 10, BigDecimal.ONE, Offer.Type.BUY);
        client.addOffer(pair, t1);

        assertEquals(1, client.getListAllOffers(pair).size());
    }

    @Test
    @Order(2)
    void getTransactionByIdTest() {
        Optional<Offer> optionalTransaction = client.getOfferById(TID);
        if (optionalTransaction.isPresent()) {
            assertEquals(TID, optionalTransaction.get().getTid());
        } else {
            fail("Offer is expected to exist.");
        }
    }
}
