package com.example.demo.post.service;

import com.example.demo.post.domain.PostCreate;
import com.example.demo.post.domain.PostUpdate;
import com.example.demo.post.infrastructure.PostEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource("classpath:test-application.properties")
@SqlGroup({
        @Sql(value = "/sql/post-service-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
public class PostServiceTest {

    @Autowired
    private PostService postService;

    @Test
    void getById는_존재하는_게시물을_내려준다() {
        // given
        // when
        PostEntity result = postService.getById(1);
        // then
        assertThat(result.getContent()).isEqualTo("testpost");
        assertThat(result.getWriter().getEmail()).isEqualTo("buea486@gmail.com");
    }

    @Test
    void post_를_이용해_게시물을_생성할_수_도_있다() {
        // given
        PostCreate postEntity = PostCreate.builder()
                .writerId(1)
                .content("testpost")
                .build();
        // when
        PostEntity result = postService.create(postEntity);
        // then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getContent()).isEqualTo("testpost");
        assertThat(result.getCreatedAt()).isGreaterThan(0L);
    }

    @Test
    void post_를_이용해_게시물을_수정할_수_도_있다() {
        // given
        PostUpdate postUpdateEntity = PostUpdate.builder()
                .content("helloworld!")
                .build();
        // when
        postService.update(1, postUpdateEntity);
        // then
        PostEntity result = postService.getById(1);
        assertThat(result.getId()).isNotNull();
        assertThat(result.getContent()).isEqualTo("helloworld!");
        assertThat(result.getModifiedAt()).isGreaterThan(0L);
    }
}
