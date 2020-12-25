
package com.web.blog.dao.mail;
import com.web.blog.model.mail.Mail;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailDao extends JpaRepository<Mail, Long> {

   Optional<Mail> findMailByEmail(String email);

   Optional<Mail> findMailById(String Id);

   Optional<Mail> findMailByEmailAndCode(String email, String code);

}
