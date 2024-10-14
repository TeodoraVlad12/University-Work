package model;

import lombok.Data;

@Data
public class Operation {
    int serialNumber;
    int sourceAccountId;
    int destinationAccountId;
    int amount;

    public Operation(int serialNumber, int sourceAccountId, int destinationAccountId, int amount) {
        this.serialNumber = serialNumber;
        this.sourceAccountId = sourceAccountId;
        this.destinationAccountId = destinationAccountId;
        this.amount = amount;
    }
}
