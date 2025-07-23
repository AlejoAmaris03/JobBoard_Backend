package com.springboot.job_platform.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.springboot.job_platform.models.User;

@Repository

public interface UserRepository extends JpaRepository<User, Integer> {
    public User findUserByEmail(String email);

    @Query(
    """
        SELECT u
        FROM User u
        WHERE (u.id != ?1) AND (u.email = ?2)        
    """)
    public User findUserByEmailExcluding(int id, String email);

    @Query(
    """
        SELECT u
        FROM User u
        WHERE u.id != ?1     
        ORDER BY u.id ASC   
    """)
    public List<User> findAllUsersExcept(int userLoggedInId);

    @Modifying
    @Query(
    """
        UPDATE User SET password = ?2 WHERE id = ?1
    """)
    public void updatePassword(int userId, String newPassword);
}
