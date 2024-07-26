package com.example.demo.user.infrastructure;

import com.example.demo.user.domain.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

//@ExtendWith(SpringExtension.class)
@DataJpaTest(showSql = true)
//@TestPropertySource("classpath:test-application.properties")
@Sql(scripts = {"/sql/user-repository-test-data.sql"})
public class UserJpaRepositoryTest {

    @Autowired
    private UserJpaRepository userJpaRepository;

    /*@Test
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
    }*/

    @Test
    void findByIdAndStatu_로_유저_데이터를_찾아올_수_있다() {
        // given
        /*UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setEmail("buea@486@gmail.com");
        userEntity.setAddress("Seoul");
        userEntity.setNickname("june67");
        userEntity.setStatus(UserStatus.ACTIVE);
        userEntity.setCertificationCode("aaaaaa-aa-aaaaa-aaaaa-aaaaaa");*/

        // when
//        userRepository.save(userEntity);
        Optional<UserEntity> result = userJpaRepository.findByIdAndStatus(1, UserStatus.ACTIVE);

        // then
        assertThat(result.isPresent()).isTrue();
    }

    @Test
    void findByIdAndStatus_는_데이터가_없으면_Optional_empty_를_내려준다() {
        // given
        /*UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setEmail("buea@486@gmail.com");
        userEntity.setAddress("Seoul");
        userEntity.setNickname("june67");
        userEntity.setStatus(UserStatus.ACTIVE);
        userEntity.setCertificationCode("aaaaaa-aa-aaaaa-aaaaa-aaaaaa");*/

        // when
//        userRepository.save(userEntity);
        Optional<UserEntity> result = userJpaRepository.findByIdAndStatus(1, UserStatus.PENDING);

        // then
        assertThat(result.isPresent()).isFalse();
        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    void findByEmailAndStatus_로_유저_데이터를_찾아올_수_있다() {
        // given
        /*UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setEmail("buea@486@gmail.com");
        userEntity.setAddress("Seoul");
        userEntity.setNickname("june67");
        userEntity.setStatus(UserStatus.ACTIVE);
        userEntity.setCertificationCode("aaaaaa-aa-aaaaa-aaaaa-aaaaaa");*/

        // when
//        userRepository.save(userEntity);
        Optional<UserEntity> result = userJpaRepository.findByEmailAndStatus("buea486@gmail.com", UserStatus.ACTIVE);

        // then
        assertThat(result.isPresent()).isTrue();
//        assertThat(result.isEmpty()).isTrue();
    }


    @Test
    void findByEmailAndStatus_는_데이터가_없으면_Optional_empty_를_내려준다() {
        // given
        /*UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setEmail("buea@486@gmail.com");
        userEntity.setAddress("Seoul");
        userEntity.setNickname("june67");
        userEntity.setStatus(UserStatus.ACTIVE);
        userEntity.setCertificationCode("aaaaaa-aa-aaaaa-aaaaa-aaaaaa");*/

        // when
//        userRepository.save(userEntity);
        Optional<UserEntity> result = userJpaRepository.findByEmailAndStatus("buea486@gmail.com", UserStatus.PENDING);

        // then
        assertThat(result.isPresent()).isFalse();
        assertThat(result.isEmpty()).isTrue();
    }
}
