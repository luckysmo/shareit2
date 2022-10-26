package ru.practicum.shareit.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ru.practicum.shareit.user.UserMapper.mapToUser;
import static ru.practicum.shareit.user.UserMapper.mapToUserDto;

@Transactional(readOnly = true)
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public UserDto addNewUser(UserDto userDto) {
        return mapToUserDto(repository.save(mapToUser(userDto)));
    }

    public Optional<User> getById(long userId) {
        User user = repository.findById(userId).orElseThrow(() -> new NotFoundException("User not found!!!"));
        return Optional.of(user);
    }

    @Transactional
    public UserDto update(long userID, UserDto userDto) {
        User userExisting = repository.findById(userID).orElseThrow(() -> new NotFoundException("User not found!!!"));
        User user = mapToUser(userDto);
        user.setId(userID);
        if (user.getEmail() != null) {
            userExisting.setEmail(user.getEmail());
        }
        if (user.getName() != null) {
            userExisting.setName(user.getName());
        }
        return mapToUserDto(userExisting);
    }

    @Transactional
    public void delete(long userID) {
        if (repository.existsById(userID)) {
            repository.deleteById(userID);
        } else {
            throw new NotFoundException("User not found!!!");

        }
    }

    public List<UserDto> getAll() {
        List<UserDto> result = new ArrayList<>();
        for (User user : repository.findAll()) {
            result.add(mapToUserDto(user));
        }
        return result;
    }
}
