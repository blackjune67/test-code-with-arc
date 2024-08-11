package com.example.demo.user.service;

import com.example.demo.common.domain.exception.CertificationCodeNotMatchedException;
import com.example.demo.common.domain.exception.ResourceNotFoundException;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserCreate;
import com.example.demo.user.domain.UserStatus;
import com.example.demo.user.domain.UserUpdate;
import com.example.demo.user.infrastructure.UserEntity;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@TestPropertySource("classpath:test-application.properties")
@SqlGroup({
        @Sql(value = "/sql/user-service-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private JavaMailSender mailSender;

    @Test
    void getByEmail은_ACTIVE_상태인_유저를_찾아올_수_있다() {
        // given
        String email = "buea486@gmail.com";
        // when
        User result = userService.getByEmail(email);
        // then
        assertThat(result.getNickname()).isEqualTo("june67");
    }

    @Test
    void getByEmail은_PENDING_상태인_유저를_찾아올_수_없다() {
        String email = "buea496@gmail.com";
        // then
        assertThatThrownBy(() -> {
            User result = userService.getByEmail(email);
        }).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getById은_ACTIVE_상태인_유저를_찾아올_수_있다() {
        // given
        // when
        User result = userService.getById(1);
        // then
        assertThat(result.getNickname()).isEqualTo("june67");
    }

    @Test
    void getById은_ACTIVE_상태인_유저를_찾아올_수_없다() {
        // given
        // when
        // then
        assertThatThrownBy(() -> {
            User result = userService.getById(2);
        }).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void userCreateDto_를_이용해_유저를_생성할_수_도_있다() {
        // given
        UserCreate userCreate = UserCreate.builder()
                .email("buea500@gmail.com")
                .address("Gyeongi")
                .nickname("june69")
                .build();
        BDDMockito.doNothing().when(mailSender).send(any(SimpleMailMessage.class));
        // when
        User result = userService.create(userCreate);
        // then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getStatus()).isEqualTo(UserStatus.PENDING);
//        assertThat(result.getCertificationCode()).isEqualTo("");
    }

    @Test
    void userUpdateDto_를_이용해_유저를_수정할_수_도_있다() {
        // given
        UserUpdate userUpdate = UserUpdate.builder()
                .nickname("haha1")
                .address("Inchoen")
                .build();

        BDDMockito.doNothing().when(mailSender).send(any(SimpleMailMessage.class));
        // when
        userService.update(1, userUpdate);
        // then
        User result = userService.getById(1);
        assertThat(result.getId()).isNotNull();
        assertThat(result.getAddress()).isEqualTo("Inchoen");
        assertThat(result.getNickname()).isEqualTo("haha1");
    }

    @Test
    void user를_이용해_유저를_로그인_할_수_있다() {
        // given
        // when
        userService.login(1);
        // then
        User result = userService.getById(1);
        assertThat(result.getLastLoginAt()).isGreaterThan(0L);
//        assertThat(result.getLastLoginAt()).isEqualsTo(Clock.systemUTC().millis());
    }

    @Test
    void PENDING의_상태의_사용자는_인증_코드로_ACTIVE_시킬_수_있다() {
        // given
        // when
        userService.verifyEmail(1, "aaaaaa-aa-aaaaa-aaaaa-aaaaaa");
        // then
        User result = userService.getById(2);
        assertThat(result.getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    void PENDING의_상태의_사용자는_잘못된_인증_코드를_받으면_에러를_발생() {
        // given
        // when
        // then
        assertThatThrownBy(() -> {
            userService.verifyEmail(1, "aaaaaa-aa-aaaaa-aaaaa-aaaaaa11");
        }).isInstanceOf(CertificationCodeNotMatchedException.class);
    }
}
