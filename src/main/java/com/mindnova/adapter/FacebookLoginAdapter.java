//import com.mindnova.adapter.SocialLoginAdapter;
//import com.mindnova.dto.SocialUserDto;
//import org.springframework.stereotype.Component;
//
//@Component
//public class FacebookLoginAdapter implements SocialLoginAdapter {
//
//    @Override
//    public SocialUserDto authenticate(String accessToken) {
//        FacebookUserInfo fbUser = callFacebookGraphAPI(accessToken);
//
//        return new SocialUserDto(
//                fbUser.getId(),
//                fbUser.getEmail(),
//                fbUser.getName(),
//                fbUser.getPictureUrl(),
//                "FACEBOOK"
//        );
//    }
//
//    private FacebookUserInfo callFacebookGraphAPI(String token) {
//        // Gọi https://graph.facebook.com/me?fields=id,name,email,picture&access_token={token}
//    }
//}
