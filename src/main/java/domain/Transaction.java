package domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
public class Transaction {

    private String tid;
    private String date;
    private BigDecimal amount;
    private BigDecimal price;
    private int type;

    public Transaction(String tid, String date, BigDecimal amount, BigDecimal price, int type) {
        this.tid = tid;
        this.date = date;
        this.amount = amount;
        this.price = price;
        this.type = type;
    }
}
