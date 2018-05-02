package dto;

import lombok.Data;

import java.util.Date;

/**
 * Created By SAIF on 12/04/2018
 */
@Data
public class BankAccount {

    String iBAN;

    String keyRIB;

    Date creationDate;

    Float ammount;

    Integer idUser;

    public BankAccount(String iBAN, String keyRIB, Date creationDate, Float ammount) {
        this.iBAN = iBAN;
        this.keyRIB = keyRIB;
        this.creationDate = creationDate;
        this.ammount = ammount;
    }
}
