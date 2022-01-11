package com.example.demo.src.tmp.model;

//import lombok.AllArgsConstructor;
import lombok.Data;

//@Getter
//@Setter
//@AllArgsConstructor
@Data
public class NaverOuathParams {
        public String resultcode;
        public String message;
        public Response response;
        @Data
        public class Response {
            public String id;
            public String gender;
            public String email;
          //  private String nickname;
           // private String profile_image;
           // private String age;
          //  private String gender;
          //  private String id;
          public String name;
            public String birthday;
            public String birthyear;
          //  private String mobile;
        }

}
