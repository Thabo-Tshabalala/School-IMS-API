package za.ac.cput.factory;

import za.ac.cput.domain.User;
import za.ac.cput.util.UserHelper;

public class Userfactory {

public static User buildUser(String firstName, String lastName, String email, String phoneNumber, String password){

    if(UserHelper.isNullOrEmpty(firstName)|| UserHelper.isNullOrEmpty(lastName)|| UserHelper.isvalidEmail(email)
    || UserHelper.isValidNumber(phoneNumber)|| UserHelper.isNullOrEmpty(password) ){

        return null;
    }

    return new User.Builder().setFirstName(firstName).setLastName(lastName).setEmail(email).setPhoneNumber(phoneNumber).setPassword(password)
            .build();


}
}
