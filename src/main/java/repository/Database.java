package repository;

import domain.Offer;
import domain.Pair;

import java.util.List;
import java.util.Optional;

public interface Database {

    void add(Pair pair, Offer offer);

    List<Offer> getAllOffers(Pair pair);

    Optional<Offer> getOffer(String tid);
}
