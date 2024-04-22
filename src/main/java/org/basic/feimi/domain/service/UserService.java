package org.basic.feimi.domain.service;
import org.basic.feimi.domain.specification.UserSpecifications;
import org.basic.feimi.infrastructure.entity.User;
import org.basic.feimi.infrastructure.repository.UserRepository;
import org.basic.feimi.util.UserUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    @Autowired
    protected ModelMapper modelMapper;

    @Autowired
    protected UserRepository userRepository;

    public User getLoggedInUser() {
        return UserUtils.isLoggedIn() ? findUserByGeneratedId(UserUtils.getLoggedInGeneratedId()) : null;
    }
    public User findUserByGeneratedId(String generatedId) {
        return userRepository.findOne(UserSpecifications.byGeneratedId(generatedId)).orElse(null);
    }
    public User findUserByEmail(String email) {
        return userRepository.findOne(UserSpecifications.byEmail(email)).orElse(null);
    }

}
