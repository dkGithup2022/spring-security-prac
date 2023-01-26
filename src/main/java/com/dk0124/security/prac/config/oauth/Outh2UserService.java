package com.dk0124.security.prac.config.oauth;

import com.dk0124.security.prac.config.auth.CustomUserDetails;
import com.dk0124.security.prac.config.oauth.provider.GoogleOAuth2UserInfo;
import com.dk0124.security.prac.config.oauth.provider.OAuth2UserInfo;
import com.dk0124.security.prac.model.Account;
import com.dk0124.security.prac.model.Role;
import com.dk0124.security.prac.repo.AccountRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class Outh2UserService extends DefaultOAuth2UserService {
    private final AccountRepo accountRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    // 로그인 후처리 함수

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);
        return processOAuth2User(userRequest, user);
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) throws OAuth2AuthenticationException {
        OAuth2UserInfo oAuth2UserInfo = null;
        if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            oAuth2UserInfo = new GoogleOAuth2UserInfo(oAuth2User.getAttributes());
        }
        // if & else if there is other provider -> can be pattern here

        Optional<Account> accountOptional = accountRepo.findByProviderAndProviderId(oAuth2UserInfo.getProvider(), oAuth2UserInfo.getProviderId());

        Account account;
        if (accountOptional.isPresent()) {
            account = accountOptional.get();
            account.setUsername(oAuth2UserInfo.getEmail());
            accountRepo.save(account);
        } else {
            account = Account.builder()
                    .username(oAuth2UserInfo.getProvider() + "_" + oAuth2UserInfo.getEmail())
                    .roles(Role.USER)
                    .provider(oAuth2UserInfo.getProvider())
                    .providerId(oAuth2UserInfo.getProviderId())
                    .password(bCryptPasswordEncoder.encode(String.valueOf((int) Math.random() * 100000)))
                    .build();
            accountRepo.save(account);
        }
        return new CustomUserDetails(account, oAuth2User.getAttributes());
    }

    /*
    아래의 코드는 auth 정보를 컨트롤러에서 다루는 코드의 일부이다.
    아래의 예시에서 outh 로 받은 정보는 Authentication, repo 에서 가져온 유저 정보는 UserDetails 에 있다.

    변경의 여지가 있는 코드이다.
    데이터를 제공하는 클래스만 다르고, 실제로는 같은 정보를 포함하고있다.

    UserDetails 에 Outh2User 를 상속받게 만드는 것으로 해결이 가능하다.

    @GetMapping("/test/login")
    public @ResponseBody
    String testLogin(
            Authentication authentication,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
     */
}


