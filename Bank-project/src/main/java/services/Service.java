package services;

import dto.BankAccount;
import dto.BankProjectConstants;
import dto.User;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Created By SAIF on 12/04/2018
 */
@Data
public class Service {

    private static final Logger LOGGER = Logger.getLogger(Service.class.getName());

    public Map<Integer, User> usersList = new HashMap<>();

    public Map<Integer, BankAccount> bankAccountList = new HashMap<>();


    public void addUser(User user) {
        Integer nextId = 1;
        if (!usersList.isEmpty())
            nextId = usersList.size() + 1;
        usersList.put(nextId, user);
    }

    public void deleteUser(Integer idUser) {
        usersList.remove(idUser);
    }

    public User updateUser(Integer idUser, User userUpdated) {
        return usersList.put(idUser, userUpdated);
    }

    public User readUser(Integer idUser) {
        return usersList.get(idUser);
    }

    public void addBankAccount(BankAccount bankAccount) {
        Integer nextId = 1;
        if (!bankAccountList.isEmpty())
            nextId = bankAccountList.size() + 1;
        bankAccountList.put(nextId, bankAccount);
    }

    public void deleteBankAccount(Integer idBankAccount) {
        bankAccountList.remove(idBankAccount);
    }

    public BankAccount updateBankAccount(Integer idBankAccount, BankAccount bankAccountUpdated) {
        return bankAccountList.put(idBankAccount, bankAccountUpdated);
    }

    public BankAccount readBankAccount(Integer idBankAccount) {
        return bankAccountList.get(idBankAccount);
    }

    public BankAccount transferMoney(Integer idBankAccount, Float amountToDepose, String typeTransaction) {
        BankAccount bankAccount = bankAccountList.get(idBankAccount);
        if (bankAccount != null) {
            switch (typeTransaction) {
                case BankProjectConstants.DEPOSIT:
                    bankAccount.setAmmount(bankAccount.getAmmount() + amountToDepose);
                    LOGGER.info("Deposit of Money is succesful for the account : " + idBankAccount);
                    break;
                case BankProjectConstants.WITHDRAW:
                    bankAccount.setAmmount(bankAccount.getAmmount() - amountToDepose);
                    LOGGER.info("Withdraw of Money is succesful for the account : " + idBankAccount);
                    break;
                default:
                    try {
                        LOGGER.info("Undefined type");
                        throw new Exception();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        }
        return bankAccount;
    }

    public void setBankAccountToUser(Integer idBankAccount, Integer idUser) {
        if (usersList.keySet().contains(idUser))
            bankAccountList.get(idBankAccount).setIdUser(idUser);
        else
            LOGGER.warning("ID user not valid");
    }

    public List<BankAccount> getBankAccountsOfUser(Integer idUser){
        List<BankAccount> bankAccounts = bankAccountList.values().stream().filter(
                (bankAccount) -> bankAccount.getIdUser() == idUser).collect(Collectors.toList());
        return bankAccounts;
    }

    public Float sumOfBalancesOfUser(Integer idUser){
        Float[] balanceSum = {0F};
        List<BankAccount> bankAccounts = getBankAccountsOfUser(idUser);
        if(bankAccounts != null && !bankAccounts.isEmpty()) {
            bankAccounts.forEach(bankAccount -> {
                balanceSum[0] += bankAccount.getAmmount();
            });
            return balanceSum[0];
        }
       return 0F;
    }
}
