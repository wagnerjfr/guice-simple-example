package repository;

import com.google.inject.Inject;
import domain.Offer;
import domain.Pair;

import java.util.List;
import java.util.Optional;

public class DatabaseClient {

    private Database database;

    @Inject
    public DatabaseClient(Database database) {
        this.database = database;
    }

    public void addOffer(Pair pair, Offer offer) {
        database.add(pair, offer);
    }

    public List<Offer> getListAllOffers(Pair pair) {
        return database.getAllOffers(pair);
    }

    public Optional<Offer> getOfferById(String tid) {
        return database.getOffer(tid);
    }
}
