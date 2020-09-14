package se.lexicon.huiyi.booklender.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.huiyi.booklender.data.LibraryUserRepository;
import se.lexicon.huiyi.booklender.dto.LibraryUserDto;
import se.lexicon.huiyi.booklender.entity.LibraryUser;

import java.util.ArrayList;
import java.util.List;

@Service
public class LibraryUserServiceImpl implements LibraryUserService {

    LibraryUserRepository libraryUserRepository;

    @Autowired
    public LibraryUserServiceImpl(LibraryUserRepository libraryUserRepository) {
        this.libraryUserRepository = libraryUserRepository;
    }

    /**
     * convert LibraryUser to LibraryUserDto
     * */
    protected LibraryUserDto getLibraryUserDto(LibraryUser libraryUser) {
        LibraryUserDto libraryUserDto = new LibraryUserDto();

        libraryUserDto.setUserId(libraryUser.getUserId());
        libraryUserDto.setRegDate(libraryUser.getRegDate());
        libraryUserDto.setName(libraryUser.getName());
        libraryUserDto.setEmail(libraryUser.getEmail());
        return libraryUserDto;
    }

    /**
     * convert List<LibraryUser> to List<LibraryUserDto>
     * */
    protected List<LibraryUserDto> getLibraryUserDtos(List<LibraryUser> foundItems) {
        List<LibraryUserDto> results = new ArrayList<>();
        for (LibraryUser l : foundItems){
            LibraryUserDto libraryUserDto = getLibraryUserDto(l);
            results.add(libraryUserDto);
        } return results;
    }

    /**
     * convert LibraryUserDto to LibraryUser
     * */
    protected LibraryUser getLibraryUser(LibraryUserDto libraryUserDto){
        LibraryUser libraryUser = new LibraryUser(
                libraryUserDto.getRegDate(), libraryUserDto.getName(), libraryUserDto.getEmail()
        );
        return libraryUser;
    }


    @Override
    public LibraryUserDto findById(int userId) {
        if (!libraryUserRepository.existsById(userId))
            throw new RuntimeException("Id " + userId + " does not exist");
        LibraryUser libraryUser = libraryUserRepository.findByUserId(userId);
        return getLibraryUserDto(libraryUser);
    }

    @Override
    public LibraryUserDto findByEmail(String email) {
        if (libraryUserRepository.findByEmailIgnoreCase(email) == null)
            throw new RuntimeException("Did not find a library user with email: " + email);
        LibraryUser libraryUser = libraryUserRepository.findByEmailIgnoreCase(email);
        return getLibraryUserDto(libraryUser);
    }

    @Override
    public List<LibraryUserDto> findAll() {
        List<LibraryUser> foundItems = libraryUserRepository.findAll();
        return getLibraryUserDtos(foundItems);
    }


    @Override
    @Transactional
    public LibraryUserDto create(LibraryUserDto libraryUserDto) {
        if (libraryUserRepository.existsById(libraryUserDto.getUserId()))
            throw new RuntimeException("Library user already exists");
        LibraryUser toCreate = new LibraryUser(libraryUserDto.getRegDate(), libraryUserDto.getName(), libraryUserDto.getEmail());
        return getLibraryUserDto(libraryUserRepository.save(toCreate));
    }

    @Override
    @Transactional
    public LibraryUserDto update(LibraryUserDto libraryUserDto) {
        if (!libraryUserRepository.existsById(libraryUserDto.getUserId()))
            throw new RuntimeException("Library user does not exist");
        LibraryUser user = libraryUserRepository.findById(libraryUserDto.getUserId()).get();
        if (user.getRegDate() != libraryUserDto.getRegDate())
            user.setRegDate(libraryUserDto.getRegDate());
        if (!user.getName().equals(libraryUserDto.getName()))
            user.setName(libraryUserDto.getName());
        if (!user.getEmail().equalsIgnoreCase(libraryUserDto.getEmail()))
            user.setEmail(libraryUserDto.getEmail());
        return getLibraryUserDto(libraryUserRepository.save(user));
    }

    @Override
    @Transactional
    public boolean delete(int userId) {
        boolean deleted = false;
        if (libraryUserRepository.existsById(userId)){
            libraryUserRepository.delete(libraryUserRepository.findById(userId).get());
            deleted = true;
        }else{
            throw new RuntimeException("Library user does not exist");
        }
        return deleted;
    }
}
