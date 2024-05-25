package com.dotsehyde.simpleapi.Services;

import com.dotsehyde.simpleapi.Models.User;
import com.dotsehyde.simpleapi.Repository.UserRepository;
import com.dotsehyde.simpleapi.Utils.AppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    UserService (UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(Long id){
        try{
            var user=   userRepository.findById(id);
          if ( user.isPresent()){
              return user.get();
          }else{
              throw new AppException(HttpStatusCode.valueOf(400),"User not found");
          }
        } catch (AppException e) {
            throw new AppException(e.getStatusCode(),e.getMessage());
        }catch (Exception e){
            throw new AppException(HttpStatusCode.valueOf(500),e.getMessage());
        }
    }

    @Transactional
    public User updateUser(Long id,User user){
        try {
           //throw when email want to be changed
            if(user.getEmail() != null){
                throw new AppException(HttpStatusCode.valueOf(400),"Email can't be changed");
            }
            //check if user exists
            if(!userRepository.existsById(id)){
                throw new AppException(HttpStatusCode.valueOf(400),"User does not exist");
            }
           var row= userRepository.updateUser(user.getName(),id);
              if(row == 0){
                  throw new AppException(HttpStatusCode.valueOf(500),"User could not be updated");
              }else{
                return userRepository.findById(id).get();
              }
        }catch (AppException e) {
            throw new AppException(e.getStatusCode(),e.getMessage());
        }catch (Exception e){
            throw new AppException(HttpStatusCode.valueOf(500),e.getMessage());
        }

    }

    public Page<User> getUsersByName(String name, Optional<Integer> page, Optional<Integer> limit) {
        try {
            return userRepository.findByName(name, PageRequest.of(page.orElse(0), limit.orElse(10)));
        }catch (AppException e) {
            throw new AppException(e.getStatusCode(),e.getMessage());
        }catch (Exception e){
            throw new AppException(HttpStatusCode.valueOf(500),e.getMessage());
        }
    }

    public Page<User> getAllUsers(Optional<Integer> page, Optional<Integer> limit, Optional<String> sortBy) {
        try {
            Optional<String> prop = Optional.empty();
            Optional<String> dir = Optional.empty();
            if (sortBy.isPresent()) {
                prop = Optional.of(sortBy.get().split(",")[0]);
                dir = Optional.of(sortBy.get().split(",")[1]);
            }

            return userRepository.findAll(
                    PageRequest.of(
                            page.orElse(0),
                            limit.orElse(10),
                            Sort.by(dir.orElse("asc").equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC,
                                    prop.orElse("id"))
                    ));
        }catch (AppException e) {
            throw new AppException(e.getStatusCode(),e.getMessage());
        }catch (Exception e){
            throw new AppException(HttpStatusCode.valueOf(500),e.getMessage());
        }
    }

    public String deleteUser(Long id) {
        try {
            //check if user exists
            if(!userRepository.existsById(id)){
                throw new AppException(HttpStatusCode.valueOf(400),"User does not exist");
            }
            userRepository.deleteById(id);
            return "User deleted successfully";
        }catch (AppException e) {
            throw new AppException(e.getStatusCode(),e.getMessage());
        }catch (Exception e){
            throw new AppException(HttpStatusCode.valueOf(500),e.getMessage());
        }
    }

    public User createUser(User user) {
        try {
            return userRepository.save(user);
        }catch (AppException e) {
            throw new AppException(e.getStatusCode(),e.getMessage());
        }catch (Exception e){
            throw new AppException(HttpStatusCode.valueOf(500),e.getMessage());
        }

    }
}
