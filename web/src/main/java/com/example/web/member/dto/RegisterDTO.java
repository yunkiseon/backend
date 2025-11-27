package com.example.web.member.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {

    @Pattern(regexp = "(?=^[A-Za-z])(?=.+\\d)[A-Za-z\\d]{5,12}", message = "아이디는 영대소문자, 숫자를 이용해 5~12 자리 사이로 해야합니다.")
    // @Length(min=4,max=8, message = "아이디는 4~8 자리 사이로 작성해야 합니다")
    // @NotBlank(message = "아이디는 4~8 자리 사이로 작성해야 합니다")
    private String id;

    // @Length(min = 4,max = 8, message = "비밀번호는 4~8 자리 사이로 작성해야 합니다")
    // @NotEmpty(message = "비밀번호는 4~8 자리 사이로 작성해야 합니다")
    @Pattern(regexp = "(?=^[A-Za-z])(?=.+[\\d])(?=.+[!#@$%])[A-Za-z\\d!#@$%]{5,12}", message = "비밀번호는 영대소문자, 숫자를 이용해 5~12 자리 사이로 해야합니다.")
    private String password;


    @NotEmpty(message = "이메일은 필수 요소입니다.")
    @Email(message = "이메일 형식을 맞춰 주세요.")
    private String email;

    @Pattern(regexp = "^[가-힣]{2,6}$", message = "이름은 2~6 자리로 작성해야 합니다.")
    private String name;

    @Max(value = 120, message = "나이는 최대 120 이하여야 합니다.")
    @Min(value = 0, message = "나이는 최소 0 이상이여야 합니다.")
    @NotNull(message = "나이는 필수 요소 입니다.")
    private Integer age;

}
