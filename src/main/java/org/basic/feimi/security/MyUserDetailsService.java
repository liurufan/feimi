package org.basic.feimi.security;

import org.basic.feimi.domain.common.Constants;
import org.basic.feimi.domain.service.UserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.Resource;
import java.util.Collections;

public class MyUserDetailsService implements UserDetailsService {

    public MyUserDetailsService() {}

    @Resource
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        org.basic.feimi.infrastructure.entity.User userEntity = userService.findUserByEmail(username);

        if (userEntity == null ) {
            throw new UsernameNotFoundException("username not exist");
        }else{
             if(Constants.USER_STOP_STATUS.STOP.equals(userEntity.getStopStatus())){
                 throw new UsernameNotFoundException("username not exist");
             } else if(!Constants.RegistrationStatus.REGISTER_COMPLETE.equals(userEntity.getRegistrationStatus())) {
                 throw new UsernameNotFoundException("username not exist");
             }
        }

        return new User(userEntity.getEmail(),
                userEntity.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority("User"))
        );
    }
}
