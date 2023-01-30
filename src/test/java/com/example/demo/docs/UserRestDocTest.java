package com.example.demo.docs;

import com.example.demo.config.WebMvcSupportTest;
import com.example.demo.controller.UserController;
import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@MockBeans({@MockBean(UserRepository.class)})
@WebMvcTest(UserController.class)
class UserRestDocTest extends WebMvcSupportTest {
    @MockBean
    private UserService userService;

    @Test
    @DisplayName("유저 정보를 저장하는 API Rest Doc 템플릿 만들기 위한 테스트입니다.")
    void saveUserTest() throws Exception {
        String saveRequest = jsonConverter.fromJson("/request/save-request.json");

        this.mockMvc.perform(post("/user/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(saveRequest)
                        .accept(MediaType.APPLICATION_JSON)
                        )
                .andExpect(status().isOk())
                .andDo(document("user-save",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("application/json;UTF-8"),
                                headerWithName(HttpHeaders.ACCEPT).description("application/json;UTF-8")
                        ),
                        requestFields(
                                fieldWithPath("userName").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("phone").type(JsonFieldType.STRING).description("핸드폰 번호"),
                                fieldWithPath("userAge").type(JsonFieldType.NUMBER).description("나이")
                        )
                ));
    }

    @Test
    @DisplayName("유저 정보를 조회하는 API Rest Doc 템플릿 만들기 위한 테스트입니다.")
    void findUserTest() throws Exception {
        String findResponseJson = jsonConverter.fromJson("/response/find-response.json");
        User findResponseObject =  jsonConverter.getGson().fromJson(findResponseJson, User.class);

        when(userService.findUser(anyInt())).thenReturn(findResponseObject);

        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/user/find/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(document("user-find",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("application/json;UTF-8"),
                                headerWithName(HttpHeaders.ACCEPT).description("application/json;UTF-8")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("application/json;UTF-8")
                        ),
                        responseFields(
                                fieldWithPath("seqUser").type(JsonFieldType.NUMBER).description("회원 고유번호"),
                                fieldWithPath("userName").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("phone").type(JsonFieldType.STRING).description("핸드폰 번호"),
                                fieldWithPath("userAge").type(JsonFieldType.NUMBER).description("나이")
                        )
                ));
    }
}


