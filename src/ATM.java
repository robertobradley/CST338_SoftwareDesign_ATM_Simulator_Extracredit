/** Title: ATM.java
 *  Abstract: This program is meant to act as a holder of money and transactions, like an atm.
 *  Author: Roberto Bradley
 *  Date: 10/20/19
 **/
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class ATM {
    private Integer serialNumber = 0;
    private String bankName;
    private String location = "UNKNOWN";
    private double balance;

    //counting for all of the transactions
    private int totalWithdrawal = 0;
    private int successWithdrawal = 0;
    private int failWithdrawal = 0;

    private int totalTransfer = 0;
    private int successTranser = 0;
    private int failTransfer = 0;

    private int totalDeposits = 0;
    private int successDeposit = 0;
    private int failDeposit = 0;

    //Queues for displaying in the displayMenu() method.
    private Queue<String> withdrawalLog =  new LinkedList<>();
    private Queue<String> depositLog =  new LinkedList<>();
    private Queue<String> transferLog =  new LinkedList<>();

    private static HashMap<String,Customer> customerHashMap =  new HashMap<>(); //customer Name, Customer object

    public ATM(String bankName){
        setBankName(bankName);
        setBalance(100);

        //if the hashMap isn't full, fill it.
        if(customerHashMap.size() != 10){
            Customer Alice = new Customer();
            Customer Tom = new Customer();
            Customer Monica = new Customer();
            Customer Michael= new Customer();
            Customer John = new Customer();
            Customer Jane = new Customer();
            Customer Robert = new Customer();
            Customer Owen = new Customer();
            Customer Chris = new Customer();
            Customer Rebecca = new Customer();

            //setting all the values
            Alice.setAllCustomerInfo("Alice",1234,5000.00,"OtterUnion");
            Tom.setAllCustomerInfo("Tom",2000, 200.00, "OtterUnion");
            Monica.setAllCustomerInfo("Monica", 3000, 50.00, "OtterUnion");
            Michael.setAllCustomerInfo("Michael", 7777, 0.00, "OtterUnion");
            John.setAllCustomerInfo("John", 8000, 500.00, "OtterUnion");
            Jane.setAllCustomerInfo("Jane", 2222, 500.00, "OtterUnion");
            Robert.setAllCustomerInfo("Robert", 2323, 200.00, "BOA");
            Owen.setAllCustomerInfo("Owen", 4455, 50.00, "BOA");
            Chris.setAllCustomerInfo("Chris", 8787, 10.00, "BOA");
            Rebecca.setAllCustomerInfo("Rebecca", 8080, 555.55, "BOA");

            //finally entering all the information into the hashMap.
            customerHashMap.put(Alice.getCustomerName(),Alice);
            customerHashMap.put(Tom.getCustomerName(),Tom);
            customerHashMap.put(Monica.getCustomerName(),Monica);
            customerHashMap.put(Michael.getCustomerName(),Michael);
            customerHashMap.put(John.getCustomerName(),John);
            customerHashMap.put(Jane.getCustomerName(),Jane);
            customerHashMap.put(Robert.getCustomerName(),Robert);
            customerHashMap.put(Owen.getCustomerName(),Owen);
            customerHashMap.put(Chris.getCustomerName(),Chris);
            customerHashMap.put(Rebecca.getCustomerName(),Rebecca);
        }
    } //done

    public ATM(Integer serialNumber,String bankName,String location){
        this(bankName);
        setATM(serialNumber,location);
    } //done

    public Integer getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setATM(Integer serialNumber,String location){
        setSerialNumber(serialNumber);
        setLocation(location);
    }//done

    public void addFund(Integer  fund){
        double myFund = ((double)fund) + getBalance();
        setBalance(myFund);
    } //done

    public void withdrawal(String customerName, Integer pin, double amount){
        //checks to see if the pin is correct. If it is then try to take out amount. If amount is too high or negative, error.
        //checks if user has enough money and the machine has enough money.
        Customer tempCustomer =  getCustomer(customerName);
        if(!tempCustomer.equals(null)) {
            if (amount > getBalance() || amount > tempCustomer.getCustomerBalance() || amount <= 0 || !tempCustomer.getPinNumber().equals(pin)) {
                totalWithdrawal+=1;
                failWithdrawal+=1;
                withdrawalLog.add("Fail - Withdrawal");
                System.out.println("Fail - Withdrawal");
            }
            else {
                successWithdrawal +=1;
                totalWithdrawal += 1;
                tempCustomer.setCustomerBalance(tempCustomer.getCustomerBalance() - amount);
                setBalance(getBalance() - amount);
                withdrawalLog.add("Success - Withdrawal");
                System.out.println("Success - Withdrawal");
            }
        }
        else {
            totalWithdrawal+=1;
            failWithdrawal+=1;
            withdrawalLog.add("Fail - Withdrawal");
            System.out.println("Fail - Withdrawal");
        }
    }//done

    public void deposit(String customerName, Integer pin, double amount){
        //grabs the object for the right customer and checks are made to insure deposits
        Customer tempCustomer = getCustomer(customerName);
        if(!tempCustomer.equals(null)){
            if(tempCustomer.getPinNumber().equals(pin) && amount >= 0){
                successDeposit +=1;
                totalDeposits += 1;
                tempCustomer.setCustomerBalance(tempCustomer.getCustomerBalance() + amount);
                setBalance(getBalance() + amount );
                depositLog.add("Success - Deposit");
                System.out.println("Success - Deposit");
            }
            else {
                failDeposit +=1;
                totalDeposits +=1;
                depositLog.add("Fail - Deposit");
            }
        }
        else {
            failDeposit +=1;
            totalDeposits +=1;
            depositLog.add("Fail - Deposit");
        }

    }//done-ish

    public boolean transfer(String customerName, Integer customerPin, double amount, String otherCustomer, Integer otherPin){
        Customer customer1 = getCustomer(customerName);
        Customer customer2 = getCustomer(otherCustomer);
        //checking to see if the object is null
        if(!customer1.equals(null) && !customer2.equals(null)){
            //checks to see if both parties have their pins correct, checks to see if the amount is greater or equal to 0 and it's not bigger than the amount the
            //customer1's current amount in the bank.
            if(customer1.getPinNumber().equals(customerPin) && customer2.getPinNumber().equals(otherPin) && amount > 0 && amount <= customer1.getCustomerBalance() && customer1.getBankType().equals(customer2.getBankType())){
                successTranser += 1;
                totalTransfer += 1;
                //deducing the first customer's amount
                customer1.setCustomerBalance(customer1.getCustomerBalance() - amount);
                //increasing the other customer's amount.
                customer2.setCustomerBalance(customer2.getCustomerBalance() + amount);
                transferLog.add("Success - Transfer");
                System.out.println("Success - Transfer");
                return true;
            }
            else {
                failTransfer += 1;
                totalTransfer += 1;
                transferLog.add("Fail - Transfer");
                System.out.println("Fail - Transfer");
                return false;
            }
        }
        else {
            failTransfer += 1;
            totalTransfer += 1;
            transferLog.add("Fail - Transfer");
            System.out.println("Fail - Transfer");
            return false;
        }
    }//done

    public void displayMenu() {
        System.out.print("\t1. Withdrawal\n" +
                "\t2. Deposit\n" +
                "\t3. Transfer\n");
        try {
            for (String message: withdrawalLog){
                System.out.println(message);
            }
            for (String message: depositLog) {
                System.out.println(message);
            }
            for (String message: transferLog) {
                System.out.println(message);
            }
        }
        catch (NullPointerException nullpoint){
            //do nothing
        }
    } //done

    public void status(){
        //prints out success vs failures and the whole bank stats
        System.out.println(toString() + "\n" + (totalTransfer+totalWithdrawal+totalDeposits) + " Transactions so far:" + "\n\tWithdrawal: " + totalWithdrawal + " (" + successWithdrawal + " success, " + failWithdrawal + " fail)" + "\n\tDeposit: " + totalDeposits + " (" + successDeposit + " success, " + failDeposit + " fail)" + "\n\tTransfer: " + totalTransfer + " (" + successTranser + " success, " + failTransfer + " fail)");
    }//done

    public boolean isCustomer(String customerName){
        //takes in a name and finds if it is in the list or hash-map.
        if(customerHashMap.containsKey(customerName)){
            return true;
        }
        return false;
    }//done

    public Customer getCustomer(String customerName){
        //check to see if the name exists in the list or hash-map and then grabs the data and copies it to the current object.
        if(!isCustomer(customerName)){
            return null;
        }
        else {
            Customer newCostumer = customerHashMap.get(customerName);
            return newCostumer;
        }
    }//done

    @Override
    public int hashCode() {
        return super.hashCode();
    }//done

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ATM){
            ATM newATM = (ATM)obj;
            if(newATM.getBankName().equals(this.bankName) && newATM.getLocation().equals(this.location) && newATM.getBalance() == this.balance && newATM.getSerialNumber().equals(this.serialNumber)){
              return true;
            }
        }
        return false;
    } //done

    @Override
    public String toString() {
        return "Serial Number: " + serialNumber + "\nBank Name: " + bankName + "\nLocation: " + location + "\nBalance: " + String.format("%.2f", balance);
    }//done
}
