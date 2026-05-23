import java.io.Serializable;

public class Payment implements Serializable {

    private static final long serialVersionUID = 1L;

    private String transactionId;
    private double amount;
    private String method;
    private boolean paid;

    public Payment(double amount, String method) {
        this.amount = amount;
        this.method = method;
        this.paid = false;
    }

    public boolean process() {
        this.transactionId = "TXN-" + (int) (Math.random() * 900000 + 100000);
        this.paid = true;
        return true;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public double getAmount() {
        return amount;
    }

    public String getMethod() {
        return method;
    }

    public boolean isPaid() {
        return paid;
    }

    @Override
    public String toString() {
        return String.format("Payment: $%.2f via %s | Status: %s | Ref: %s",
                amount, method, (paid ? "PAID" : "UNPAID"),
                (transactionId == null ? "N/A" : transactionId));
    }
}
