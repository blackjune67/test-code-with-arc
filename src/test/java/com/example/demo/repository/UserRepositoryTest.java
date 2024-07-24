package com.example.demo.repository;

import com.example.demo.model.UserStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest(showSql = true)
@TestPropertySource("classpath:test-application.properties")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void userRepository_가_연결되는가() {
        // given
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("buea@486@gmail.com");
        userEntity.setAddress("Seoul");
        userEntity.setNickname("june67");
        userEntity.setStatus(UserStatus.ACTIVE);
        userEntity.setCertificationCode("aaaaaa-aa-aaaaa-aaaaa-aaaaaa");

        // when
        UserEntity save = userRepository.save(userEntity);

        // then
        assertThat(save.getId()).isNotNull();
    }

    @Test
    void findByIdAndStatu_로_유저_데이터를_찾아올_수_있다() {
        // given
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setEmail("buea@486@gmail.com");
        userEntity.setAddress("Seoul");
        userEntity.setNickname("june67");
        userEntity.setStatus(UserStatus.ACTIVE);
        userEntity.setCertificationCode("aaaaaa-aa-aaaaa-aaaaa-aaaaaa");

        // when
        userRepository.save(userEntity);
        Optional<UserEntity> result = userRepository.findByIdAndStatus(1, UserStatus.ACTIVE);

        // then
        assertThat(result.isPresent()).isTrue();
    }
}
