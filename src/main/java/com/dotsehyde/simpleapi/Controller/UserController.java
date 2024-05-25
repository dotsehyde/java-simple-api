package com.dotsehyde.simpleapi.Controller;

import com.dotsehyde.simpleapi.Models.ErrorModel;
import com.dotsehyde.simpleapi.Models.User;
import com.dotsehyde.simpleapi.Services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

/**
 * UserController is a REST controller that handles HTTP requests related to User.
 */
@ControllerAdvice
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private final UserService userService;

    /**
     * Constructor for UserController.
     *
     * @param userService The service to be used for user-related operations.
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Endpoint to get a User by their ID.
     *
     * @param id The ID of the User to retrieve.
     * @return The User with the given ID, or an ErrorModel if no such User exists or an error occurs.
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
          User optUser = userService.getUserById(id.longValue());
            return ResponseEntity.status(200).body(optUser);
    }

    /**
     * Endpoint to get all Users.
     *
     * @param page  The page number for pagination.
     * @param limit The limit of users per page for pagination.
     * @return A list of Users, or an ErrorModel if no Users are found or an error occurs.
     */
    @GetMapping("/list")
    public ResponseEntity<?> getAllUsers(@RequestParam Optional<Integer> page,
                                         @RequestParam Optional<Integer> limit,
                                         @RequestParam Optional<String> sort) {
            var res = userService.getAllUsers(page, limit, sort);
            if (res.isEmpty()) {
                var error = new ErrorModel(
                        400,
                        "No users found");
                error.addDetail("time", new Date().toString()); // Add custom detail here
                error.addDetail("page", page.orElse(-1));
                error.addDetail("limit", limit.orElse(-1));
                return ResponseEntity.status(400).body(error);
            }

            return ResponseEntity.status(200).body(res);

    }

    /**
     * Endpoint to create a User.
     *
     * @param user The User to be created.
     * @return The created User, or an ErrorModel if an error occurs.
     */
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody @Valid User user) {
            var res = userService.createUser(user);
            return ResponseEntity.status(201).body(res);
    }

    /**
     * Endpoint to delete a User by their ID.
     *
     * @param id The ID of the User to delete.
     * @return A message indicating the User was deleted, or an ErrorModel if an error occurs.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
            var res = userService.deleteUser(id);
            return ResponseEntity.status(200).body(res);

    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> getUserByName(@PathVariable String name,
                                           @RequestParam Optional<Integer> page,
                                           @RequestParam Optional<Integer> limit) {
            var res = userService.getUsersByName(name,page,limit);
           return ResponseEntity.ok(res);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User userData){
            var user = userService.updateUser(id,userData);
            return ResponseEntity.ok(user);
    }

}