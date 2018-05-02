package dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Created By SAIF on 12/04/2018
 */
@Data
public class User {

    String firstName;

    String lastName;

    Integer age;

    String phoneNumber;

    public User(String firstName, String lastName, Integer age, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.phoneNumber = phoneNumber;
    }
}
