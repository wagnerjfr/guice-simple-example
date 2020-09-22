package database;

import application.Util;
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

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static repository.DatabaseModule.Configuration.REAL;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ConcurrentTest {

    private final static int ITERATIONS = 10;
    private DatabaseClient client;

    @BeforeAll
    void beforeAll() {
        Injector injector = Guice.createInjector(new DatabaseModule(REAL));
        client = injector.getInstance(DatabaseClient.class);
    }

    @Test
    @Order(1)
    void parallelOffersTest() throws InterruptedException {
        final int numWritters = 4;

        for (int i = 0; i < numWritters; i++) {
            new Thread(new Writter(client, Pair.BTC_USD)).start();
        }

        for (int i = 0; i < numWritters; i++) {
            new Thread(new Writter(client, Pair.XRP_USD)).start();
        }

        Thread.sleep(Duration.ofSeconds(2).toMillis());

        final int total = ITERATIONS * numWritters;
        assertTrue(total == client.getListAllOffers(Pair.BTC_USD).size());
        assertTrue(total == client.getListAllOffers(Pair.XRP_USD).size());

        Pair pair = Pair.BTC_USD;
        Optional<Offer> optionalOffer = client.getOfferById("1");
        if (optionalOffer.isPresent()) {
            Offer offer = optionalOffer.get();
            assertTrue(Integer.parseInt(offer.getTid()) > 0);
            assertTrue(offer.getAmount() > 0);
            assertTrue(offer.getPrice().intValue() > 0);
        } else {
            fail("Offer is expected to exist.");
        }
    }

    private void assertEquals(List<Offer> listAllOffers) {
    }

    private abstract class Worker implements Runnable {

        DatabaseClient client;
        Pair pair;

        Worker(DatabaseClient client, Pair pair) {
            this.client = client;
            this.pair = pair;
        }
    }

    private class Writter extends Worker {

        Writter(DatabaseClient client, Pair pair) {
            super(client, pair);
        }

        @Override
        public void run() {
            for (int i = 0; i < ITERATIONS; i++) {
                Offer offer = Util.generateOffer();
                client.addOffer(pair, offer);
            }
        }
    }
}
