package repository;

import com.google.inject.Inject;
import domain.Pair;
import domain.Transaction;

import java.util.List;
import java.util.Optional;

public class DatabaseClient {

    private Database database;

    @Inject
    public DatabaseClient(Database database) {
        this.database = database;
    }

    public void addTransaction(Pair pair, Transaction transaction) {
        database.add(pair, transaction);
    }

    public List<Transaction> getListAllTransactions(Pair pair) {
        return database.getAllTransactions(pair);
    }

    public Optional<Transaction> getTransactionById(String tid) {
        return database.getTransaction(tid);
    }
}
