package com.teamcubation.reportservice.application.port.out;

import com.teamcubation.reportservice.domain.model.user.User;

public interface AuthOutPort {

    String login(User user);

    User register(User user);
}
