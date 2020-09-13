package se.lexicon.huiyi.booklender.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    private static LibraryUserDto getLibraryUserDto(LibraryUser libraryUser) {
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
    private static List<LibraryUserDto> getLibraryUserDtos(List<LibraryUser> foundItems) {
        List<LibraryUserDto> results = new ArrayList<>();
        for (LibraryUser l : foundItems){
            LibraryUserDto libraryUserDto = getLibraryUserDto(l);
            results.add(libraryUserDto);
        } return results;
    }
    @Override
    public LibraryUserDto findById(int userId) {
        LibraryUser libraryUser = libraryUserRepository.findById(userId).get();
        return getLibraryUserDto(libraryUser);
    }
    @Override
    public LibraryUserDto findByEmail(String email) {
        LibraryUser libraryUser = libraryUserRepository.findByEmailIgnoreCase(email);
        return getLibraryUserDto(libraryUser);
    }

    @Override
    public List<LibraryUserDto> findAll() {
        List<LibraryUser> foundItems = libraryUserRepository.findAll();
        return getLibraryUserDtos(foundItems);
    }


    @Override
    public LibraryUserDto create(LibraryUserDto libraryUserDto) {
        if (libraryUserRepository.existsById(libraryUserDto.getUserId()))
            throw new RuntimeException("Library user already exists");
        LibraryUser toCreate = new LibraryUser(libraryUserDto.getRegDate(), libraryUserDto.getName(), libraryUserDto.getEmail());
        return getLibraryUserDto(libraryUserRepository.save(toCreate));
    }

    @Override
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
        return getLibraryUserDto(user);
    }

    @Override
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
