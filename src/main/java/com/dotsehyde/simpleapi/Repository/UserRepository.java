package com.dotsehyde.simpleapi.Repository;

import com.dotsehyde.simpleapi.Models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.name = ?1 WHERE u.id = ?2")
    int updateUser(String name, Long id);

    @Query("SELECT u FROM User u WHERE u.name = ?1")
    Page<User> findByName(String name, Pageable pageable);
}
