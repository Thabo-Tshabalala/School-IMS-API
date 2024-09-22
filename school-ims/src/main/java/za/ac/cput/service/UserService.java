package za.ac.cput.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.User;
import za.ac.cput.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IService<User, Long> {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    public User read(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User update(User user) {
        // Check if the user exists by ID
        if (!userRepository.existsById(user.getUserID())) {
            throw new IllegalStateException("User with Id " + user.getUserID() + " does not exist");
        }

        // Update the user and save it
        return userRepository.save(user);
    }


    public User getUser(String email, String password) {
        return userRepository.findUserByEmailAndPassword(email, password);
    }
    public Optional<User> findSpecificUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }
    public boolean existsByEmail(String email) {
        return userRepository.findUserByEmail(email) != null; // Adjust based on your repository method
    }

    @Override
    public boolean delete(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }
}
