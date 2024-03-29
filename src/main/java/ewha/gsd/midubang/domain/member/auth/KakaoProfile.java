package ewha.gsd.midubang.domain.member.auth;

import lombok.Data;

import java.util.Properties;

@Data
public class KakaoProfile {
    public Long id;
    public String connected_at;
//    public Properties properties;
    public KakaoAccount kakao_account;


//    public class Properties {
//        public String nickname;
//        public String profile_image; //이미지 경로 필드1
//        public String thumbnail_image;
//    }

    @Data
    public class KakaoAccount {
        public Boolean has_email;
        public Boolean email_needs_agreement;
        public Boolean is_email_valid;
        public Boolean is_email_verified;
        public String email;

//        @Data
//        public class Profile {
//            public String nickname;
//            public String thumbnail_image_url;
//            public String profile_image_url; // 이미지 경로 필드2
//            public Boolean is_default_image;
//        }
    }
}
