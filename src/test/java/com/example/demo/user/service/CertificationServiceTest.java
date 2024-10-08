package com.example.demo.user.service;

import com.example.demo.mock.FakeMailSender;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class CertificationServiceTest {

    @Test
    public void 이메일과_컨텐츠가_제대로_만들어져서_보내지는지_테스트한다() {
        FakeMailSender fakeMailSender = new FakeMailSender();
        CertificationService certificationService = new CertificationService(fakeMailSender);

        certificationService.send("fnffn0607@naver.com", 1, "aaaaaa-aa-aaaaa-aaaaa-aaaaaa");

        Assertions.assertThat(fakeMailSender.email).isEqualTo("fnffn0607@naver.com");
        Assertions.assertThat(fakeMailSender.title).isEqualTo("Please certify your email address");
        Assertions.assertThat(fakeMailSender.content).isEqualTo("Please click the following link to certify your email address: http://localhost:8080/api/users/1/verify?certificationCode=aaaaaa-aa-aaaaa-aaaaa-aaaaaa");
    }
}
