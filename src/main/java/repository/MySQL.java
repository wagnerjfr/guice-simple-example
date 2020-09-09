package repository;

import domain.Pair;
import domain.Transaction;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class MySQL implements Database, Sleepable {

    private static final int SLEEP_MILLI_SECONDS = 10;

    private EnumMap<Pair, List<Transaction>> data = new EnumMap<>(Pair.class);

    private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private final Lock read = rwl.readLock();
    private final Lock write = rwl.writeLock();

    @Override
    public void add(Pair pair, Transaction transaction) {
        write.lock();
        try {
            if (!data.containsKey(pair)) {
                data.put(pair, new ArrayList<>());
            }
            data.get(pair).add(transaction);
            sleep(SLEEP_MILLI_SECONDS);
        } finally {
            write.unlock();
        }
    }

    @Override
    public List<Transaction> getAllTransactions(Pair pair) {
        read.lock();
        try {
            sleep(SLEEP_MILLI_SECONDS);
            return new ArrayList<>(data.get(pair));
        } finally {
            read.unlock();
        }
    }

    @Override
    public Optional<Transaction> getTransaction(String tid) {
        read.lock();
        try {
            sleep(SLEEP_MILLI_SECONDS);
            return data.values().stream()
                .flatMap(List::stream)
                .filter(transaction -> transaction.getTid().equals(tid))
                .findFirst();
        } finally {
            read.unlock();
        }
    }
}
