package com.edu.iuh.shop_managerment.configurations;

import com.edu.iuh.shop_managerment.enums.user.Gender;
import com.edu.iuh.shop_managerment.enums.user.UserRole;
import com.edu.iuh.shop_managerment.models.User;
import com.edu.iuh.shop_managerment.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {
    PasswordEncoder passwordEncoder;
    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository){
        return args -> {
            if(!userRepository.existsUserByUserName("admin")){
                User user = User.builder()
                        .userName("admin")
                        .fullName("admin")
                        .password(passwordEncoder.encode("admin"))
                        .gender(Gender.FEMALE)
                        .dob(Date.valueOf(LocalDate.of(2002, 4, 13)))
                        .userRole(UserRole.ADMIN)
                        .build();
                userRepository.save(user);
                log.warn("Admin user has been created with default email: 'admin', password: 'admin', please change it! ");
            }
        };
    }
}
