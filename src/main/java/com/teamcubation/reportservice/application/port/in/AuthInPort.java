package com.teamcubation.reportservice.application.port.in;

import com.teamcubation.reportservice.domain.model.user.User;

public interface AuthInPort {

    String login(User user) throws Exception;

    String register(User user);

}
