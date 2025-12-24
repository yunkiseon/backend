package com.example.movietalk.movie.dto;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieImageDTO {

    private Long inum;

    private String uuid;

    private String path;

    private String imgName;
    
    // 아래의 메소드들은 아래의 형식의 URL주소를 만들기 위한 메소드들이다. 그리고 이 메소드들은
    // thymeleaf 으로 호출할 수 있다. list.html 참고
    // static 폴더 안에 있으면 html에서 src를 통해서 접근할 수 있지만, 다른 컴퓨터에서 호완이 안된다.
    

    public String getThumnailURL(){
        String thumbFullPath = "";

        // java.ner.URL~
        try {
            thumbFullPath = URLEncoder.encode(path + "/s_" + uuid + "_" + imgName, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return thumbFullPath;
    }

    public String getImageURL(){
        String fullPath = "";
        // java.net.URl~
        try {
            fullPath = URLEncoder.encode(path + "/" + uuid + "_" + imgName, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return fullPath;
    }
}
