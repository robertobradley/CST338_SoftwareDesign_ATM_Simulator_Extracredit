/** Title: Customer.java
 *  Abstract: This program is meant to hold customer information for the ATM java class.
 *  Author: Roberto Bradley
 *  Date: 10/20/19
 **/
public class Customer {
    private String customerName;
    private Integer pinNumber;
    private double customerBalance;
    private String bankType;

    public void setAllCustomerInfo(String customerName, Integer pinNumber, double customerBalance, String bankType){
        setCustomerName(customerName);
        setPinNumber(pinNumber);
        setCustomerBalance(customerBalance);
        setBankType(bankType);
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getPinNumber() {
        return pinNumber;
    }

    public void setPinNumber(Integer pinNumber) {
        this.pinNumber = pinNumber;
    }

    public double getCustomerBalance() {
        return customerBalance;
    }

    public void setCustomerBalance(double customerBalance) {
        this.customerBalance = customerBalance;
    }

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    @Override
    public String toString() {
        return customerName + ": " + "$" + String.format("%.2f",customerBalance);
    }
}
