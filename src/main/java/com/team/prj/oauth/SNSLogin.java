package com.team.prj.oauth;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.team.prj.user.MailService;
import com.team.prj.user.User;

public class SNSLogin implements SnsUrls {

	private OAuth20Service oauthService; // 토큰을 주거나 프로필을 가져오거나 하는 서비스는 OAuth20Service가 다 함
	private String profileUrl;
	private String service;
	
	public SNSLogin(SnsValue sns) {
		if (sns.getService().equals("naver")) {	// naver, google, kakao, ... 등 xml에 등록한 bean객체에 맞는 SNS login하기
			
			this.oauthService = new ServiceBuilder(sns.getClientId())
					.apiSecret(sns.getCliendSecret())
					.callback(sns.getRedirectUrl())
					.build(NaverAPI20.instance());
			
			this.profileUrl = sns.getProfileUrl(); // 네이버 프로필을 받아올 url
			this.service = sns.getService();
			}
		
		}
	
	public String getNaverAuthURL() {
		return this.oauthService.getAuthorizationUrl();
	}

	public String getUserProfile(String code) throws Exception {
		OAuth2AccessToken accessToken = oauthService.getAccessToken(code);
										// RequestMapping( method = RequestMethod.GET 과 같은 의미 Verb.GET 
		OAuthRequest request = new OAuthRequest(Verb.GET, this.profileUrl);
		
		oauthService.signRequest(accessToken, request);// 토큰을 req에 담기
		Response response = oauthService.execute(request); // GET, POST 등 메서드를 통틀어 사용하는 메서드
		
    	return response.getBody(); // 응답을 받으면 JSON으로 옴
	}
	
	public User parseJson(String body) throws Exception {
				
	    		ObjectMapper mapper = new ObjectMapper();
	    		JsonNode rootNode = mapper.readTree(body);
	    		
	    		User u = new User();
	    		if (service.equals("naver")) { // 네이버일 때
	    			// JsonNode resNode = rootNode.path("response");
	    			JsonNode resNode = rootNode.get("response");
	    			
	    			String id = resNode.get("id").asText();
	    			String temp_pw = mixNum(); // 받아올 비밀번호가 없어 임시 비밀번호 설정
	    			String email = resNode.get("email").asText();
	    			String name = resNode.get("name").asText();
	    			String nickname = resNode.get("nickname").asText();
	    			nickname = "NAVER_"+nickname;
	    			String mobile = resNode.get("mobile").asText();
//	    			System.out.println(id);
//	    			....
//	    			System.out.println(name);
	    			
	    			u.setU_id(id);
	    			u.setU_pw(temp_pw);
	    			u.setU_email(email);
	    			u.setU_nickname(nickname);
	    			u.setU_name(name);
	    			u.setU_phone(mobile);
	    			u.setU_photo("defaultimage.png");
	    			u.setU_addr("!!주소 입력 필수!!");;
	    			u.setU_role("u");
	    			
				}
	    		
				return u;
	    		// 3. DB에 해당 유저가 존재하는지 체크 (네이버 아이디를 기본 아이디 컬럼에 같이 사용?)
	    		// 4. 존재시 강제 로그인, 미존재시 
	}
	
	// 임시 비밀번호를 위한 난수 생성
	public String mixNum() {
		int[] randomNum = new int[6];
		Random r = new Random();
		for (int i = 0; i < randomNum.length; i++) {
			randomNum[i] = r.nextInt(10);
			for (int j = 0; j < i; j++) {
				if (randomNum[i]==randomNum[j]) {
					i--;
				}
			}
		}
		
		String text = "";
		for (int i = 0; i < randomNum.length; i++) {
			text = text + randomNum[i];
		}
		
		return text;
	}
	
	
	
}