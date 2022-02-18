package com.unitech.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.unitech.dao.entity.UserEntity;
import com.unitech.dao.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserUtil {

    private final UserRepository userRepository;

    public UserEntity findUserByToken(String authHeader) {
        String token = authHeader.substring("Bearer ".length());
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        String pin = decodedJWT.getSubject();

        Optional<UserEntity> optionalUser = userRepository.findByPin(pin);

        return optionalUser.get();
    }
}
