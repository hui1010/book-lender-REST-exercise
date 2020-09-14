package se.lexicon.huiyi.booklender.data;

import org.springframework.data.repository.CrudRepository;
import se.lexicon.huiyi.booklender.entity.LibraryUser;

import java.util.List;

public interface LibraryUserRepository extends CrudRepository<LibraryUser, Integer> {
    List<LibraryUser> findAll();

    LibraryUser findByEmailIgnoreCase(String email);
    LibraryUser findByUserId(int userId);

}
