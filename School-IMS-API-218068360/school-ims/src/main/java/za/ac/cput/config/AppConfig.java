package za.ac.cput.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import za.ac.cput.domain.Admin;
import za.ac.cput.service.AdminService;

@Configuration
public class AppConfig {

    private final AdminService adminService;

    public AppConfig(AdminService adminService) {
        this.adminService = adminService;
    }

    @Bean
    public CommandLineRunner initDefaultAdmin() {
        return args -> {
            // Check if the default admin exists
            if (adminService.getAdminByEmail("admin@mis.com").isEmpty()) {
                Admin defaultAdmin = new Admin.Builder()
                        .setEmail("admin@mis.com")
                        .setPassword("admin") //I need to hash this password
                        .setRole("ADMIN")
                        .build();
                adminService.create(defaultAdmin);
            }
        };
    }
}