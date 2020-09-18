package se.lexicon.huiyi.booklender.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.huiyi.booklender.data.LibraryUserRepository;
import se.lexicon.huiyi.booklender.dto.LibraryUserDto;
import se.lexicon.huiyi.booklender.entity.LibraryUser;
import se.lexicon.huiyi.booklender.exception.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Service
@Configurable
public class LibraryUserServiceImpl implements LibraryUserService {

    LibraryUserRepository libraryUserRepository;

    @Autowired
    public LibraryUserServiceImpl(LibraryUserRepository libraryUserRepository) {
        this.libraryUserRepository = libraryUserRepository;
    }

    /**
     * Method for converting LibraryUser to LibraryUserDto
     *
     * @param libraryUser LibraryUser
     * @return LibraryUserDto
     */
    protected LibraryUserDto getLibraryUserDto(LibraryUser libraryUser) {
        LibraryUserDto libraryUserDto = new LibraryUserDto();
        libraryUserDto.setUserId(libraryUser.getUserId());
        libraryUserDto.setRegDate(libraryUser.getRegDate());
        libraryUserDto.setName(libraryUser.getName());
        libraryUserDto.setEmail(libraryUser.getEmail());
        return libraryUserDto;
    }

    /**
     * Method for converting List<LibraryUser> to List<LibraryUserDto>
     *
     * @param users List<LibraryUser> that need to be converted to
     * @return List<LibraryUserDto>
     */
    protected List<LibraryUserDto> getLibraryUserDtos(List<LibraryUser> users) {
        List<LibraryUserDto> results = new ArrayList<>();
        for (LibraryUser l : users){
            LibraryUserDto libraryUserDto = getLibraryUserDto(l);
            results.add(libraryUserDto);
        } return results;
    }

    /**
     * Method for converting LibraryUserDto to LibraryUser
     *
     * @param libraryUserDto LibraryUserDto
     * @return LibraryUser
     */
    protected LibraryUser getLibraryUser(LibraryUserDto libraryUserDto){

        return new LibraryUser(libraryUserDto.getRegDate(), libraryUserDto.getName(), libraryUserDto.getEmail());
    }


    /**
     * Method for find a libraryUserDto with given library user id
     *
     * @param userId the id of library user
     * @return the found libraryUserDto
     * @throws ResourceNotFoundException if cannot find any library user with given id
     */
    @Override
    public LibraryUserDto findById(int userId) throws ResourceNotFoundException {
        if (!libraryUserRepository.existsById(userId))
            throw new ResourceNotFoundException("Cannot find any library user with id: " + userId);
        LibraryUser libraryUser = libraryUserRepository.findByUserId(userId);
        return getLibraryUserDto(libraryUser);
    }

    /**
     * Method for finding a libraryUserDto with given email
     *
     * @param email String email
     * @return found libraryUserDto
     * @throws IllegalArgumentException if email is empty
     * @throws ResourceNotFoundException if cannot find any library user with given email
     */

    @Override
    public LibraryUserDto findByEmail(String email) throws IllegalArgumentException, ResourceNotFoundException{
        if (email == null)
            throw new IllegalArgumentException("Email should not be empty");
        if (libraryUserRepository.findByEmailIgnoreCase(email) == null)
            throw new ResourceNotFoundException("Did not find a library user with email: " + email);
        LibraryUser libraryUser = libraryUserRepository.findByEmailIgnoreCase(email);
        return getLibraryUserDto(libraryUser);
    }

    /**
     * Method for finding all libraryUserDto
     *
     * @return List<LibraryUserDto>
     */

    @Override
    public List<LibraryUserDto> findAll() {
        List<LibraryUser> foundItems = libraryUserRepository.findAll();
        return getLibraryUserDtos(foundItems);
    }

    /**
     * Method for creating and saving a libraryUserDto
     *
     * @param libraryUserDto the new libraryUserDto that needs to be created
     * @return the created libraryUserDto
     * @throws RuntimeException if the user already is existed, should update it if want to change any properties of the library user
     */
    @Override
    @Transactional
    public LibraryUserDto create(LibraryUserDto libraryUserDto) throws RuntimeException {
        if (libraryUserRepository.existsById(libraryUserDto.getUserId()))
            throw new RuntimeException("Library user already exists, please update");
        LibraryUser toCreate = new LibraryUser(libraryUserDto.getRegDate(), libraryUserDto.getName(), libraryUserDto.getEmail());
        return getLibraryUserDto(libraryUserRepository.save(toCreate));
    }

    /**
     * Method for updating a libraryUserDto
     *
     * @param libraryUserDto the libraryUserDto that needs to be updated
     * @return the updated libraryUserDto
     * @throws RuntimeException if the library user do not exist, should consider creating it first
     * @throws ResourceNotFoundException if cannot find the library user
     */
    @Override
    @Transactional
    public LibraryUserDto update(LibraryUserDto libraryUserDto) throws RuntimeException, ResourceNotFoundException {
        if (!libraryUserRepository.existsById(libraryUserDto.getUserId()))
            throw new RuntimeException("Library user does not exist, please create first");
        LibraryUser user = libraryUserRepository.findById(libraryUserDto.getUserId()).orElseThrow(
                () -> new ResourceNotFoundException("Cannot find the user.")
        );
        if (user.getRegDate() != libraryUserDto.getRegDate())
            user.setRegDate(libraryUserDto.getRegDate());
        if (!user.getName().equals(libraryUserDto.getName()))
            user.setName(libraryUserDto.getName());
        if (!user.getEmail().equalsIgnoreCase(libraryUserDto.getEmail()))
            user.setEmail(libraryUserDto.getEmail());
        return getLibraryUserDto(libraryUserRepository.save(user));
    }

    /**
     * Method for deleting a libraryUserDto with given id
     *
     * @param userId the libraryUserDtos id
     * @return true: successfully deleted, false: cannot find any library user with given id
     * @throws ResourceNotFoundException
     */

    @Override
    @Transactional
    public boolean delete(int userId) throws ResourceNotFoundException{
        if (!libraryUserRepository.findById(userId).isPresent())
            throw new ResourceNotFoundException("Can not find the library user with id: " + userId);
        boolean deleted = false;
        if (libraryUserRepository.existsById(userId)){
            libraryUserRepository.delete(libraryUserRepository.findById(userId).get());
            deleted = true;
        }
        return deleted;
    }
}
