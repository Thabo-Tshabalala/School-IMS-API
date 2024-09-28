package za.ac.cput.api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import za.ac.cput.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthenticationApi {

    private final AdminService adminService;
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationApi.class);

    @Autowired
    public AuthenticationApi(AdminService adminService, UserService userService) {
        this.adminService = adminService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {
        logger.info("Received login request with email: {} and password: {}", email, password);

        if (adminService.verifyLogin(email, password)) {
            logger.info("Admin login successful for email: {}", email);
            return new ResponseEntity<>("Admin", HttpStatus.OK);
        } else if (userService.verifyLogin(email, password)) {
            logger.info("User login successful for email: {}", email);
            return new ResponseEntity<>("User", HttpStatus.OK);
        } else {
            logger.warn("Failed login attempt for email: {}", email);
            return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }
    }
}