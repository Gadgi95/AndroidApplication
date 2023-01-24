package com.example.application.service;

import static com.example.application.util.UserUtil.prepareToSave;
import static com.example.application.util.validation.ValidationUtil.checkNotFound;
import static com.example.application.util.validation.ValidationUtil.checkNotFoundWithId;

import com.example.application.AuthorizedUser;
import com.example.application.model.User;
import com.example.application.repository.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

//В этом классе прописывается более детально логика выполнения запросов в базу данных касаемо пользователей,
// именно этот класс нужен для связи с графическим интерфейсом, здесь в каждом методе происходят проверки,
//// чтобы избежать ошибки попадания не валидных данных в базу данных.
@Service
public class UserService implements UserDetailsService {

    private final UserRepository repository;
    //Кодировщик паролей
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    //Создание пользователя и проверка что он не равен нулю
    public User create(User user) {
        Assert.notNull(user, "user must not be null");
        return prepareAndSave(user);
    }

    //Удаление пользователя из базы данных с проверкой существует ли такой ID пользователя в базе
    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    //Получение пользователя из базы данных с проверкой существует ли такой ID пользователя в базе
    public User get(int id) {
        return checkNotFoundWithId(repository.get(id), id);
    }

    //Получение пользователя по мылу из базы данных с проверкой на ноль и
    // с проверкой существует ли такой пользователь по мылу в базе
    public User getByEmail(String email) {
        Assert.notNull(email, "email must not be null");
        return checkNotFound(repository.getByEmail(email), "email=" + email);
    }

    //Получение всего списка пользователей
    public List<User> getAll() {
        return repository.getAll();
    }

    //Редактирование пользователя, проверка что он не равен нулю и кодировка измененного пароля
    public void update(User user) {
        Assert.notNull(user, "user must not be null");
        prepareAndSave(user);
    }

    //Получение пользователя с его заявками из базы данных с проверкой существует ли такой ID пользователя в базе
    public User getWithTickets(int id) {
        return checkNotFoundWithId(repository.getWithTickets(id), id);
    }

    //Кодирует пароль и сохраняет пользователя
    private User prepareAndSave(User user) {
        return repository.save(prepareToSave(user, passwordEncoder));
    }

    //Метод #loadUserByUserName должен возвратить класс - данные аутентифицированного
    // пользователя - который имплементирует Spring Security интерфейс UserDetails.
    // Вместо самостоятельной реализации всех методов интерфейса UserDetails проще всего сделать класс
    // (AuthorizedUser), отнаследовав его от стандартной Spring Security имплементации этого интерфейса
    // org.springframework.security.core.userdetails.User и в конструкторе передав ему все необходимые данные.
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.getByEmail(email.toLowerCase());
        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " is not found");
        }
        return new AuthorizedUser(user);
    }
}
