package edu.java.bot.service;

import edu.java.bot.dto.request.UserRegistrationRequest;
import edu.java.bot.dto.request.UserSettingsUpdateRequest;
import edu.java.bot.dto.response.UserRegistrationResponse;
import edu.java.bot.dto.response.UserSettingsUpdateResponse;
import edu.java.bot.exception.customExceptions.UserAlreadyExistsException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public UserRegistrationResponse registerUser(UserRegistrationRequest request) {
        // Заглушка для проверки существует ли пользователь
        if ("existingUser".equals(request.getUsername())) {
            throw new UserAlreadyExistsException("Пользователь с таким именем уже существует.");
        }
        // Здесь реализация логики регистрации пользователя
        // Например, сохранение данных пользователя в базу данных
        return new UserRegistrationResponse("Пользователь успешно зарегистрирован с именем: " + request.getUsername());
    }

    @Override
    public UserSettingsUpdateResponse updateUserSettings(UserSettingsUpdateRequest request) {
        // Здесь реализация логики обновления настроек пользователя
        // Например, обновление информации в базе данных
        return new UserSettingsUpdateResponse("Настройки пользователя успешно обновлены.");
    }
}
