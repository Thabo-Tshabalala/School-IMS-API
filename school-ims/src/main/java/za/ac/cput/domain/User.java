package za.ac.cput.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class User {
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private long userID;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String password;

protected User (){
}
private User(Builder builder){

    this.userID = builder.userID;
    this.firstName = builder.firstName;
    this.lastName= builder.lastName;
    this.phoneNumber = builder.phoneNumber;
    this.email = builder.email;
    this.password = builder.password;
}

    public long getUserID() {
        return userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userID == user.userID && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(phoneNumber, user.phoneNumber) && Objects.equals(email, user.email) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, firstName, lastName, phoneNumber, email, password);
    }

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public static class Builder {
    private long userID;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String password;

    public Builder setUserID(long userID) {
        this.userID = userID;
        return this;
    }

    public Builder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public Builder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Builder setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public Builder setEmail(String email) {
        this.email = email;
        return this;
    }

    public Builder setPassword(String password) {
        this.password = password;
        return this;
    }

   public Builder copy(User user){
        this.userID = user.userID;
        this.firstName = user.firstName;
        this.lastName = user.lastName;
        this.email = user.email;
        this.phoneNumber = user.phoneNumber;
        this.password = user.password;
        return  this;
   }

public User build(){
        return new User(this);
    }

}

}


