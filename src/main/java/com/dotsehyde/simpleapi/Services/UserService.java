package com.dotsehyde.simpleapi.Services;

import com.dotsehyde.simpleapi.Models.User;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    private final LinkedList<User> users = new LinkedList<>();
    UserService(){
      users.addAll(
        new LinkedList<User>(){{
          add(new User(1, "John", "Doe"));
          add(new User(2, "Jane", "Doe"));
          add(new User(3, "Gerald", "Smith"));
          add(new User(4, "Jane", "Smith"));
        }}
      );
    }

    public Optional<User> getUserById(Integer id){
        Optional<User> optional = Optional.empty();
        for (User tmp : users) {
            if (Objects.equals(tmp.getId(), id)) {
                optional = Optional.of(tmp);
                return optional;
            }
        }
       return optional;

    }

    public LinkedList<User> getAllUsers(Optional<Integer> page,Optional<Integer> limit) {
        if(page.isPresent() && limit.isPresent()){
            int startIndex = page.get();
            int endIndex = limit.get();
            if(startIndex < 0 || endIndex > users.size() || startIndex > endIndex) {
                throw new IllegalArgumentException("Invalid page or limit values");
            }
            return new LinkedList<>(users.subList(startIndex, endIndex));
        }
        return users;
    }
}
