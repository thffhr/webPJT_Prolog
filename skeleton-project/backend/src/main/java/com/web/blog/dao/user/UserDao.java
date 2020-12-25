
package com.web.blog.dao.user;

import java.util.Optional;

import com.web.blog.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, String> {
    User getUserByEmail(String email);

    Optional<User> findUserByEmailAndPassword(String email, String password);

    Optional<User> findUserByUidAndPassword(String uid, String password);

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByUid(String uid);

    Optional<User> findUserByNickname(String nickname);

    User getUserByUid(String uid);

    void deleteByUid(String uid);

}
