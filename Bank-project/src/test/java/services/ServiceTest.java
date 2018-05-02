package services;

import dto.BankAccount;
import dto.BankProjectConstants;
import dto.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.List;

/**
 * Created By SAIF on 02/05/2018
 */
public class ServiceTest {

    Service service = new Service();

    User user1 = new User("FirstName", "lastName", 26, "0600000001");
    User user2 = new User("FirstName2", "lastName2", 27, "0600000002");

    BankAccount bankAccount1 = new BankAccount("FR125566636996","14478566xxA",new Date(),1500F);
    BankAccount bankAccount2 = new BankAccount("EN125566636996","xXcv8566xxA",new Date(),2670F);
    BankAccount bankAccount3 = new BankAccount("FR023333333339","FFA63xAqs",new Date(),100F);

    @Test
    public final void addUser_Test() {
        service.addUser(user1);
        service.addUser(user2);

        Assert.assertEquals(service.getUsersList().size(), 2);
        Assert.assertEquals(service.getUsersList().get(1), user1);
        Assert.assertEquals(service.getUsersList().get(2), user2);
    }

    @Test
    public final void updateUser_Test() {
        user1.setAge(30);
        user1.setFirstName("firstNameUpdated");
        user1.setLastName("lastNameUpdated");
        service.updateUser(1,user1);

        Assert.assertEquals(service.getUsersList().get(1), user1);
        Assert.assertEquals(service.getUsersList().get(1).getAge().intValue(), 30);
        Assert.assertEquals(service.getUsersList().get(1).getFirstName(), "firstNameUpdated");
        Assert.assertEquals(service.getUsersList().get(1).getLastName(), "lastNameUpdated");
    }

    @Test
    public final void readUser_Test() {
        User userToRead = service.readUser(1);
        Assert.assertEquals(service.getUsersList().get(1), userToRead);
    }

    @Test
    public final void deleteUser_Test() {
        User user3 = new User("FirstName3", "lastName3", 35, "0600000003");
        service.addUser(user1);
        service.addUser(user2);
        service.addUser(user3);
        Assert.assertEquals(service.getUsersList().size(), 3);

        service.deleteUser(3);

        Assert.assertEquals(service.getUsersList().size(), 2);
    }

    @Test
    public final void addBankAccount_Test() {
        service.addBankAccount(bankAccount1);
        service.addBankAccount(bankAccount2);
        service.addBankAccount(bankAccount3);

        Assert.assertEquals(service.getBankAccountList().size(), 3);
        Assert.assertEquals(service.getBankAccountList().get(1), bankAccount1);
        Assert.assertEquals(service.getBankAccountList().get(2), bankAccount2);
        Assert.assertEquals(service.getBankAccountList().get(3), bankAccount3);
    }

    @Test
    public final void updateBankAccount_Test() {
        service.addBankAccount(bankAccount1);
        bankAccount1.setAmmount(5600F);
        bankAccount1.setKeyRIB("xA1");
        service.updateBankAccount(1,bankAccount1);

        Assert.assertEquals(service.getBankAccountList().get(1), bankAccount1);
        Assert.assertEquals(service.getBankAccountList().get(1).getAmmount(), bankAccount1.getAmmount());
        Assert.assertEquals(service.getBankAccountList().get(1).getKeyRIB(), bankAccount1.getKeyRIB());
    }

    @Test
    public final void readBankAccount_Test() {
        BankAccount bankAccountToRead = service.readBankAccount(1);
        Assert.assertEquals(service.getBankAccountList().get(1), bankAccountToRead);
    }

    @Test
    public final void deleteBankAccount_Test() {
        service.addBankAccount(bankAccount1);
        service.addBankAccount(bankAccount2);
        service.addBankAccount(bankAccount3);

        BankAccount bankAccount4 = new BankAccount("FR111","AAA",new Date(),1990F);
        service.addBankAccount(bankAccount4);
        Assert.assertEquals(service.getBankAccountList().size(), 4);

        service.deleteBankAccount(4);

        Assert.assertEquals(service.getBankAccountList().size(), 3);
    }

    @Test
    public final void transferMoney_Deposit_Failed_Test() {
        BankAccount bankAccountToRead = service.transferMoney(10, 523F, BankProjectConstants.DEPOSIT);
        Assert.assertEquals(bankAccountToRead, null);
    }

    @Test
    public final void transferMoney_Deposit_Test() {
        service.addBankAccount(bankAccount1);
        BankAccount bankAccountToRead = service.transferMoney(1, 523F, BankProjectConstants.DEPOSIT);
        Assert.assertEquals(bankAccountToRead.getAmmount().floatValue(), 2023F,0.01);
    }

    @Test
    public final void transferMoney_Withdraw_Test() {
        service.addBankAccount(bankAccount1);
        BankAccount bankAccountToRead = service.transferMoney(1, 500F, BankProjectConstants.WITHDRAW);
        Assert.assertEquals(bankAccountToRead.getAmmount().floatValue(), 1000F,0.01);
    }

    @Test (expected = Exception.class)
    public final void transferMoney_Withdraw_Failed_Test() {
        service.addBankAccount(bankAccount1);
        service.transferMoney(1, 523F, null);
    }
    @Test
    public final void setBankAccountToUser_Test() {
        service.addUser(user1);
        service.addBankAccount(bankAccount1);
        service.setBankAccountToUser(1, 1);

        Assert.assertEquals(bankAccount1.getIdUser().intValue(), 1);
    }

    @Test
    public final void getBankAccountsOfUser_Test() {
        service.addUser(user1);
        service.addBankAccount(bankAccount1);
        service.addBankAccount(bankAccount2);
        service.setBankAccountToUser(1, 1);
        service.setBankAccountToUser(2, 1);

        List<BankAccount> bankAccountList = service.getBankAccountsOfUser(1);
        Assert.assertEquals(bankAccountList.size(), 2);
        Assert.assertEquals(bankAccountList.get(0), bankAccount1);
        Assert.assertEquals(bankAccountList.get(1), bankAccount2);
    }

    @Test
    public final void sumOfBalancesOfUser_Test() {
        service.addUser(user1);
        service.addBankAccount(bankAccount1);
        service.addBankAccount(bankAccount2);
        service.setBankAccountToUser(1, 1);
        service.setBankAccountToUser(2, 1);

        Float sumOfBalancesOfUser = service.sumOfBalancesOfUser(1);
        Assert.assertEquals(sumOfBalancesOfUser.floatValue(), bankAccount1.getAmmount()+bankAccount2.getAmmount(),0.01);
    }

    @Test
    public final void sumOfBalancesOfUser_Failed_Test() {
        service.addBankAccount(bankAccount1);
        service.addBankAccount(bankAccount2);
        service.setBankAccountToUser(1, 10);
        service.setBankAccountToUser(2, 10);

        Float sumOfBalancesOfUser = service.sumOfBalancesOfUser(10);
        Assert.assertEquals(sumOfBalancesOfUser.floatValue(), 0,0.01);
    }
}
