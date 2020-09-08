package repository;

import domain.Pair;
import domain.Transaction;

import java.util.List;
import java.util.Optional;

public interface Database {

    void add(Pair pair, Transaction transaction);

    List<Transaction> getAllTransactions(Pair pair);

    Optional<Transaction> getTransaction(String tid);
}
