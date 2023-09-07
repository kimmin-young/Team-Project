package com.team.prj.oauth;


//모든 데이터 내용을 받는 역할의 DTO
public class SnsValue implements SnsUrls {
	private String service;
	private String clientId;
	private String cliendSecret;
	private String redirectUrl;
	private String profileUrl;
	
	public SnsValue(String service, String clientId, String cliendSecret, String redirectUrl, String profileUrl) {
		super();
		this.service = service;
		this.clientId = clientId;
		this.cliendSecret = cliendSecret;
		this.redirectUrl = redirectUrl;
		this.profileUrl = profileUrl;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getCliendSecret() {
		return cliendSecret;
	}
	public void setCliendSecret(String cliendSecret) {
		this.cliendSecret = cliendSecret;
	}
	public String getRedirectUrl() {
		return redirectUrl;
	}
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}
	public String getProfileUrl() {
		return profileUrl;
	}
	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}

}
