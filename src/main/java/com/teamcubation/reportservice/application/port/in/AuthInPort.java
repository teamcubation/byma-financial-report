package com.teamcubation.reportservice.application.port.in;

import com.teamcubation.reportservice.domain.model.user.User;

public interface AuthInPort {

    String login(User user);

    User register(User user);

}
