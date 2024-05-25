package com.dotsehyde.simpleapi.Controller;

import com.dotsehyde.simpleapi.Models.ErrorModel;
import com.dotsehyde.simpleapi.Models.User;
import com.dotsehyde.simpleapi.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Optional;

/**
 * UserController is a REST controller that handles HTTP requests related to User.
 */
@RestController
public class UserController {

    private final UserService userService;

    /**
     * Constructor for UserController.
     * @param userService The service to be used for user-related operations.
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Endpoint to get a User by their ID.
     *
     * @param id The ID of the User to retrieve.
     * @return The User with the given ID, or null if no such User exists.
     */
   @GetMapping("/user/{id}")
public ResponseEntity<?> getUserById(@PathVariable Integer id){
    try{
        Optional<User> optUser = userService.getUserById(id);
        if(optUser.isPresent()){
            return ResponseEntity.status(200).body(optUser.get());
        }
        var error = new ErrorModel(
                400,
                "User not found");
        error.addDetail("time", new Date().toString()); // Add custom detail here
        return ResponseEntity.status(400).body(error);
    } catch (Exception e){
       var error = new ErrorModel(
                 500,
                 "An error occurred while processing your request"
          );
       error.addDetail("message", e.getMessage()); // Add custom detail here
       return ResponseEntity.status(500).body(error);
    }
}
    @GetMapping("/user/list")
    public ResponseEntity<?> getAllUsers(@RequestParam Optional<Integer> page, @RequestParam Optional<Integer> limit){
       try {
           var res = userService.getAllUsers(page, limit);
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
       }catch (Exception e){
           var error = new ErrorModel(
                   500,
                   "An error occurred while processing your request"
           );
           error.addDetail("message", e.getMessage()); // Add custom detail here
           return ResponseEntity.status(500).body(error);
       }
    }


}