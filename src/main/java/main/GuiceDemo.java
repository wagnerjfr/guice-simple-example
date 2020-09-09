package main;

import com.google.inject.Guice;
import com.google.inject.Injector;
import domain.Pair;
import domain.Transaction;
import lombok.extern.java.Log;
import repository.DatabaseClient;
import repository.DatabaseModule;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static repository.DatabaseModule.Configuration.REAL;

@Log
public class GuiceDemo {

    public static void main(String[] args) {
        new GuiceDemo().run();
    }

    private void run() {
        Injector injector = Guice.createInjector(new DatabaseModule(REAL));
        DatabaseClient client = injector.getInstance(DatabaseClient.class);

        Pair pair = Pair.BTC_USD;
        getTransactionsList().forEach(transaction -> client.addTransaction(pair, transaction));

        log.info(String.format("MySQL has %d transactions", client.getListAllTransactions(Pair.BTC_USD).size()));
    }

    private List<Transaction> getTransactionsList() {
        List<Transaction> list = new ArrayList<>();
        for (int i = 1; i <= 1000; i++) {
            list.add(new Transaction(String.valueOf(i), LocalDateTime.now().toString(), getRandAmount(), getRandPrice(), getType()));
        }
        return list;
    }

    private BigDecimal getRandAmount() {
        return new BigDecimal(ThreadLocalRandom.current().nextInt(1, 200));
    }

    private BigDecimal getRandPrice() {
        return new BigDecimal(ThreadLocalRandom.current().nextInt(1000, 1500));
    }

    private int getType() {
        return ThreadLocalRandom.current().nextInt(0, 2);
    }
}
