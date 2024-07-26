package com.example.demo.post.controller;

import com.example.demo.post.domain.PostUpdate;
import com.example.demo.post.infrastructure.PostRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@SqlGroup({
        @Sql(value = "/sql/post-controller-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
public class PostControllerTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @Test
    void postId로_단_건_게시물을_갖고_올_수_있다() throws Exception {
        // given
        // when
        // then
        mockMvc.perform(get("/api/posts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.content").value("testpost"))
                .andExpect(jsonPath("$.writer.id").isNumber())
                .andExpect(jsonPath("$.writer.email").value("buea486@gmail.com"));
    }

    @Test
    void 사용자가_존재하지_않는_경우_게시물을_조희할_경우_에러가_난다() throws Exception {
        // given
        // when
        // then
        mockMvc.perform(get("/api/posts/11234"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Posts에서 ID 11234를 찾을 수 없습니다."));
    }

    @Test
    void 게시물을_수정할_수_있다() throws Exception {
        PostUpdate postUpdate = PostUpdate.builder()
                .content("helloworld!")
                .build();
        // given
//        PostEntity result = postRepository.getReferenceById(1L);

        // when
        mockMvc.perform(put("/api/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.content").value("helloworld!"))
                .andExpect(jsonPath("$.writer.id").isNumber())
                .andExpect(jsonPath("$.writer.email").value("buea486@gmail.com"))
                .andExpect(jsonPath("$.writer.nickname").value("june67"));
        // then
    }


}
