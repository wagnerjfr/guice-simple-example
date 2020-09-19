package domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@ToString
@RequiredArgsConstructor
public class Offer {

    private String tid;
    private String date;
    private int amount;
    private BigDecimal price;
    private Type type;

    public Offer(String tid, String date, int amount, BigDecimal price, Type type) {
        this.tid = tid;
        this.date = date;
        this.amount = amount;
        this.price = price;
        this.type = type;
    }

    @Getter
    public enum Type {
        BUY, SELL;
    }
}
