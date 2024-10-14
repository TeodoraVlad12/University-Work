package model;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@Data
public class Account {
    int id;
    int amount;
    int initialBalance;
    ReentrantLock mutex = new ReentrantLock();
    List<Operation> log = new ArrayList<>();

    public Account(int id, int amount) {
        this.id = id;
        this.amount = amount;
        this.initialBalance = amount;
    }
}
