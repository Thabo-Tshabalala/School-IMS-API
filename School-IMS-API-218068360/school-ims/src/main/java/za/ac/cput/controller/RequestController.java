package za.ac.cput.controller;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Request;
import za.ac.cput.service.RequestService;

import java.util.List;

@RestController
@RequestMapping("/requests")
public class RequestController {
    private static final Logger logger = LoggerFactory.getLogger(RequestController.class);

    private final RequestService requestService;

    @Autowired
    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createRequest(@RequestBody Request request) {
        try {
            Request createdRequest = requestService.create(request);
            return new ResponseEntity<>(createdRequest, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {

            System.err.println("Error creating request: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {

            System.err.println("Unexpected error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred.");
        }
    }


    @GetMapping("/read/{id}")
    public ResponseEntity<Request> getRequest(@PathVariable long id) {
        try {
            Request request = requestService.read(id);
            return new ResponseEntity<>(request, HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/update")
    public ResponseEntity<Request> updateRequest(@RequestBody Request request) {
        try {
            Request updatedRequest = requestService.update(request);
            return new ResponseEntity<>(updatedRequest, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRequest(@PathVariable long id) {
        try {
            requestService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error deleting request with ID: " + id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/getAll")
    public ResponseEntity<List<Request>> getAllRequests() {
        try {
            List<Request> requests = requestService.getAll();
            return new ResponseEntity<>(requests, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Request>> getRequestsByStatus(@PathVariable String status) {
        try {
            List<Request> requests = requestService.getRequestsByStatus(status);
            return new ResponseEntity<>(requests, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/notificationCount")
    public ResponseEntity<Long> getNotificationCount() {
        long count = requestService.getPendingRequestCount();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

}
