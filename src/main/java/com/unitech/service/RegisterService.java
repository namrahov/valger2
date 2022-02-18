package com.unitech.service;

import com.unitech.dao.entity.RoleEntity;
import com.unitech.dao.entity.UserEntity;
import com.unitech.dao.repository.UserRepository;
import com.unitech.logger.DPLogger;
import com.unitech.mapper.RoleMapper;
import com.unitech.model.UserRegistrationDto;
import com.unitech.model.exception.UserRegisterException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegisterService {
    private static final DPLogger log = DPLogger.getLogger(RegisterService.class);

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void registerUser(UserRegistrationDto dto) {
        log.info("ActionLog.registerUser.start");

        checkUserIfExist(dto.getPin());

        List<RoleEntity> roleEntities = RoleMapper.buildRoleEntityList();

        UserEntity user = UserEntity.builder()
                .pin(dto.getPin())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .roles(roleEntities)
                .build();

        userRepository.save(user);

        log.info("ActionLog.registerUser.success");
    }

    private void checkUserIfExist(String pin) {
        Optional<UserEntity> optionalUser = userRepository.findByPin(pin);
        if (optionalUser.isPresent()) {
            throw new UserRegisterException("User has already registered");
        }
    }
}
