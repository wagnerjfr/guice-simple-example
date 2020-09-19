package application;

import com.google.inject.Guice;
import com.google.inject.Injector;
import domain.Offer;
import domain.Pair;
import lombok.Getter;
import lombok.extern.java.Log;
import repository.DatabaseClient;
import repository.DatabaseModule;

import java.util.List;

import static repository.DatabaseModule.Configuration.REAL;

@Log
@Getter
public class GuiceDemo {

    private final int numberOfOffers = 100;
    private final Pair pair = Pair.BTC_USD;
    private DatabaseClient client;

    public GuiceDemo() {
        Injector injector = Guice.createInjector(new DatabaseModule(REAL));
        client = injector.getInstance(DatabaseClient.class);
    }

    public static void main(String[] args) {
        new GuiceDemo().sampleExecution();
    }

    public void sampleExecution() {
        Util.getOfferList(numberOfOffers)
            .forEach(transaction -> client.addOffer(pair, transaction));

        List<Offer> offers = client.getListAllOffers(pair);
        log.info(String.format("MySQL has %d offers", offers.size()));

        for (int i = 0; i < 5; i++) {
            log.info(offers.get(i).toString());
        }
    }
}
