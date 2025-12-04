package com.example.book.dto;

import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    private Long id;
    @NotBlank(message = "공백일 수 없습니다.")
    private String isbn;
    @NotBlank(message = "공백일 수 없습니다.")
    private String title;
    @NotBlank(message = "공백일 수 없습니다.")
    private String author;
    // @Max(value = 1200000, message = "상한가 1200000 입니다.")
    // @Min(value = 0)
    @Range(min = 0,max = 10000000, message = "가격은 0 ~ 10,000,000 사이입니다")
    @NotNull(message = "공백일 수 없습니다.")
    private Integer price;

    private String description;
}
