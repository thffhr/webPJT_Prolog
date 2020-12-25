
package com.web.blog.dao.user;

import java.util.Optional;

import javax.transaction.Transactional;

import com.web.blog.model.user.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileDao extends JpaRepository<Profile, String> {
    Optional<Profile> findByUid(String uid);

    @Transactional
    @Modifying
    @Query(value = "UPDATE user" + " SET pic_byte = :pic_byte " + " where user.uid = :uid", nativeQuery = true)
    void updateUser(@Param("pic_byte") byte[] picByte, @Param("uid") String uid);

    Profile findUserByUid(String uid);
}
