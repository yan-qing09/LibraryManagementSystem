public class BookFineTransaction {
    private String transactionID;
    private double amount;
    private String payDate;

    public BookFineTransaction(String transactionID, double amount, String payDate) {
      this.transactionID = transactionID;
      this.amount = amount;
      this.payDate = payDate;
    }

    public String getPayDate() {
      return payDate;
    }

    public double getAmount() {
      return amount;
    }

    public String getTransactionID() {
      return transactionID;
    }

    public void setPayDate(String payDate) {
      this.payDate = payDate;
    }

    public void setAmount(double amount) {
      this.amount = amount;
    }

    public void setTransactionID(String transactionID) {
      this.transactionID = transactionID;
    }
}