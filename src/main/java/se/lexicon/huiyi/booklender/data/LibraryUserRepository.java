package se.lexicon.huiyi.booklender.data;

import org.springframework.data.repository.CrudRepository;
import se.lexicon.huiyi.booklender.entity.LibraryUser;

public interface LibraryUserRepository extends CrudRepository<LibraryUser, Integer> {
    LibraryUser findByEmailIgnoreCase(String email);

}
