package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.User;
import za.ac.cput.service.UserService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        // Check for null or empty credentials
        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Email and password must be provided.", HttpStatus.BAD_REQUEST);
        }

        User user = userService.getUser(email, password);

        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid email or password.", HttpStatus.UNAUTHORIZED);
        }
    }
//    @GetMapping("/check/{email}")
//    public ResponseEntity<User> checkUserByEmail(@PathVariable String email) {
//        Optional<User> userOptional = userService.findSpecificUserByEmail(email);
//
//        if (userOptional.isPresent()) {
//            return new ResponseEntity<>(userOptional.get(), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }


    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            // Check if a user with the same email already exists
            Optional<User> existingUser = userService.findSpecificUserByEmail(user.getEmail());
            if (existingUser.isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null); // 409 Conflict
            }

            User createdUser = userService.create(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // 400 Bad Request
        }
    }



    @PutMapping("/update")
    public ResponseEntity<User> updateCustomer(@RequestBody User customer){
        User updateCustomer = userService.update(customer);
        return new ResponseEntity<>(updateCustomer, HttpStatus.OK);
    }

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

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable long userId){
        boolean deletedUser = userService.delete(userId);
        return new ResponseEntity<>(deletedUser, HttpStatus.OK);
    }

}
