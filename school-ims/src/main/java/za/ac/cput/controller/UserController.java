package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.User;
import za.ac.cput.service.UserService;

import java.util.List;
import java.util.Map;

// Build objects using Factory to utilize Helper classes;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        User user = userService.getUser(email, password);

        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            User createdUser = userService.create(user);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

//    @GetMapping("/update/{mobileNumber}")
//    public ResponseEntity<User> getUserByMobileNumber(@PathVariable String mobileNumber){
//        User user = userService.getUserByMobileNumber(mobileNumber);
//        return new ResponseEntity<>(user, HttpStatus.OK);
//    }

//    @GetMapping("/login/{email}/{password}")
//    public ResponseEntity<Boolean> login(@PathVariable String email,
//                                         @PathVariable String password){
//        boolean verifyLogin = userService.verifyLogin(email, password);
//        return new ResponseEntity<>(verifyLogin, HttpStatus.OK);
//    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> userList = userService.getAll();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user){
        User updatedUser = userService.update(user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable long userId){
        boolean deletedUser = userService.delete(userId);
        return new ResponseEntity<>(deletedUser, HttpStatus.OK);
    }

}
