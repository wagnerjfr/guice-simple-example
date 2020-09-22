package demo;

import application.GuiceDemo;
import domain.Pair;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class GuiceDemoTest {

    @Test
    void guiceDemoTest() {
        GuiceDemo main = new GuiceDemo();
        main.sampleExecution();

        assertNotEquals(main.getNumberOfOffers(), main.getClient().getListAllOffers(Pair.XRP_USD).size());
        assertEquals(main.getNumberOfOffers(), main.getClient().getListAllOffers(main.getPair()).size());
    }
}
