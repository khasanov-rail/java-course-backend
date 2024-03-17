package edu.java.bot.service;

import edu.java.bot.dto.request.UserRegistrationRequest;
import edu.java.bot.dto.request.UserSettingsUpdateRequest;
import edu.java.bot.dto.response.UserRegistrationResponse;
import edu.java.bot.dto.response.UserSettingsUpdateResponse;

public interface UserService {
    UserRegistrationResponse registerUser(UserRegistrationRequest request);

    UserSettingsUpdateResponse updateUserSettings(UserSettingsUpdateRequest request);
}
