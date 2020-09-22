package application;

import domain.Offer;
import domain.Offer.Type;
import exception.NumberTransactionException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public final class Util {

    private static int TID = 0;
    private final static int AMOUNT_MIN = 1;
    private final static int AMOUNT_MAX = 200;
    private final static int PRICE_MIN = 1000;
    private final static int PRICE_MAX = 1500;

    public static synchronized Offer generateOffer() {
        return new Offer(String.valueOf(++TID), LocalDateTime.now().toString(),
            getRandAmount(AMOUNT_MIN, AMOUNT_MAX),
            getRandPrice(PRICE_MIN, PRICE_MAX),
            getOfferType());
    }

    public static List<Offer> getOfferList(int size) {
        if (size <= 0) {
            throw new NumberTransactionException("Number of offers must be more than 0");
        }

        List<Offer> list = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            list.add(generateOffer());
        }
        return list;
    }

    public static int getRandAmount(int min, int max) {
        checkConstraints(min, max);
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    public static BigDecimal getRandPrice(int min, int max) {
        checkConstraints(min, max);
        return new BigDecimal(ThreadLocalRandom.current().nextInt(min, max));
    }

    public static Type getOfferType() {
        int value = ThreadLocalRandom.current().nextInt(0, Type.values().length);
        return value == 0 ? Type.BUY : Type.SELL;
    }

    private static void checkConstraints(int min, int max) {
        if (min <= 0 || max <= 0) {
            throw new NumberTransactionException("Min or Max values can't be lesser or equal than 0");
        }
        if (min > max) {
            throw new NumberTransactionException("Min value can't be greater than Max value");
        }
    }
}
