import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ATMTest {

    @Test
    void addFund() {
        ATM tempATM = new ATM("BANK");
        tempATM.addFund(300);
        tempATM.addFund(200);
        tempATM.addFund(100);

        assertEquals(700,tempATM.getBalance());
    }

    @Test
    void withdrawal() {
        ATM tempATM = new ATM("BANK");

        tempATM.withdrawal("Alice",1234,10);
        assertEquals(4990,tempATM.getCustomer("Alice").getCustomerBalance());
    }

    @Test
    void deposit() {
        ATM tempATM = new ATM("BANK");
        tempATM.deposit("Alice",1234,200);
        assertEquals(5200,tempATM.getCustomer("Alice").getCustomerBalance());
    }

    @Test
    void transfer() {
        ATM tempATM = new ATM("BANK");
        tempATM.transfer("Alice",1234,200,"Robert",2323);
        tempATM.deposit("Alice",1234,200);

        assertEquals(5200,tempATM.getCustomer("Alice").getCustomerBalance());
        assertEquals(200,tempATM.getCustomer("Robert").getCustomerBalance());
    }


    @Test
    void isCustomer() {
        ATM tempATM = new ATM("BANK");
        assertTrue(tempATM.isCustomer("Alice"));

        assertFalse(tempATM.isCustomer("Cody"));
    }

    @Test
    void getCustomer() {
    }
}