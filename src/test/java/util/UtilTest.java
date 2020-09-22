package util;

import domain.Offer;
import application.Util;
import exception.NumberTransactionException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UtilTest {

    @Test
    void transactionsGeneratorTest() {
        final int number = 100;
        List<Offer> list = Util.getOfferList(number);
        assertEquals(number, list.size());
    }

    @Test
    void transactionsGeneratorExceptionTest() {
        for (int number : Arrays.asList(0, -1)) {
            assertThrows(NumberTransactionException.class, () -> Util.getOfferList(number));
        }
    }

    @Nested
    class Amount {
        @Test
        void getRandomAmountTest() {
            final int min = 1;
            final int max = 100;
            int value = Util.getRandAmount(min, max);
            assertTrue(value >= min && value <= max);
        }

        @Test
        void getRandomAmountInvalidTest() {
            final int min = -1;
            final int max = 0;
            assertThrows(NumberTransactionException.class, () -> Util.getRandAmount(min, max));
        }

        @Test
        void getRandomAmountMaxSmallerTest() {
            final int min = 2;
            final int max = 1;
            assertThrows(NumberTransactionException.class, () -> Util.getRandAmount(min, max));
        }
    }

    @Nested
    class Price {
        @Test
        void getRandomPriceTest() {
            final int min = 1;
            final int max = 100;
            BigDecimal value = Util.getRandPrice(min, max);
            assertTrue(value.intValue() >= min && value.intValue() <= max);
        }

        @Test
        void getRandomPriceInvalidTest() {
            final int min = -1;
            final int max = 0;
            assertThrows(NumberTransactionException.class, () -> Util.getRandPrice(min, max));
        }

        @Test
        void getRandomPriceMaxSmallerTest() {
            final int min = 2;
            final int max = 1;
            assertThrows(NumberTransactionException.class, () -> Util.getRandPrice(min, max));
        }
    }
}
