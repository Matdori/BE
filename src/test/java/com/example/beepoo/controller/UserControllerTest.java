package com.example.beepoo.controller;

import com.example.beepoo.dto.UserRequestDto;
import com.example.beepoo.jwt.JwtUtil;
import com.example.beepoo.repository.UserRepository;
import com.example.beepoo.service.UserService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

//@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
@MockBean(JpaMetamodelMappingContext.class)
class UserControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
    @Autowired
    private Validator validatorInjected;
    @MockBean
    private UserService userService;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private JwtUtil jwtUtil;


    @Test
    void test1() {
        System.out.println("하이");

    }

    @Test
    @DisplayName("디스플레이네임")
    void testName() {

        //given
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setUserName("");

        //when
        Set<ConstraintViolation<UserRequestDto>> validate = validatorInjected.validate(userRequestDto);

        //then
        Iterator<ConstraintViolation<UserRequestDto>> iterator = validate.iterator();
        List<String> messages = new ArrayList<>();
        while (iterator.hasNext()) {
            ConstraintViolation<UserRequestDto> next = iterator.next();
            messages.add(next.getMessage());
            System.out.println("message = " + next.getMessage());
        }
        Assertions.assertThat(messages).contains("공백일 수 없습니다"
                , "이름이 비 상식적이다 임마");
    }

}