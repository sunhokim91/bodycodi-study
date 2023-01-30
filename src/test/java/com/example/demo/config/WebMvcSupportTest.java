package com.example.demo.config;

import com.example.demo.util.JsonConverter;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.ConditionalOnDefaultWebSecurity;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.stream.Collectors;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@ExtendWith({RestDocumentationExtension.class})
@Import(value = {
        RestDocConfig.class,
        WebSecurityConfig.class
})
@ConditionalOnDefaultWebSecurity
@MockBeans(@MockBean(WebMvcConfig.class))
public class WebMvcSupportTest {
    @Autowired
    protected MockMvc mockMvc;
    protected static JsonConverter jsonConverter = new JsonConverter();
}
