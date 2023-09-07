package com.team.prj.p_recipe;

import java.math.BigDecimal;

public class PRecipe {
	private BigDecimal num;
	private String RCP_NM;
	private String RCP_WAY;
	private String RCP_PAT;
	private String INFO_ENG;
	private String RCP_PARTS_DTLS;
	private String ATT_FILE_NO_MAIN;
	private String RCP_NA_TIP;
	private String MANUAL;
	private String MANUAL_IMG;
	private BigDecimal views;
	
	public PRecipe() {
	}

	public PRecipe(BigDecimal num, String rCP_NM, String rCP_WAY, String rCP_PAT, String iNFO_ENG,
			String rCP_PARTS_DTLS, String aTT_FILE_NO_MAIN, String rCP_NA_TIP, String mANUAL, String mANUAL_IMG,
			BigDecimal views) {
		super();
		this.num = num;
		RCP_NM = rCP_NM;
		RCP_WAY = rCP_WAY;
		RCP_PAT = rCP_PAT;
		INFO_ENG = iNFO_ENG;
		RCP_PARTS_DTLS = rCP_PARTS_DTLS;
		ATT_FILE_NO_MAIN = aTT_FILE_NO_MAIN;
		RCP_NA_TIP = rCP_NA_TIP;
		MANUAL = mANUAL;
		MANUAL_IMG = mANUAL_IMG;
		this.views = views;
	}

	public BigDecimal getNum() {
		return num;
	}

	public void setNum(BigDecimal num) {
		this.num = num;
	}

	public String getRCP_NM() {
		return RCP_NM;
	}

	public void setRCP_NM(String rCP_NM) {
		RCP_NM = rCP_NM;
	}

	public String getRCP_WAY() {
		return RCP_WAY;
	}

	public void setRCP_WAY(String rCP_WAY) {
		RCP_WAY = rCP_WAY;
	}

	public String getRCP_PAT() {
		return RCP_PAT;
	}

	public void setRCP_PAT(String rCP_PAT) {
		RCP_PAT = rCP_PAT;
	}

	public String getINFO_ENG() {
		return INFO_ENG;
	}

	public void setINFO_ENG(String iNFO_ENG) {
		INFO_ENG = iNFO_ENG;
	}

	public String getRCP_PARTS_DTLS() {
		return RCP_PARTS_DTLS;
	}

	public void setRCP_PARTS_DTLS(String rCP_PARTS_DTLS) {
		RCP_PARTS_DTLS = rCP_PARTS_DTLS;
	}

	public String getATT_FILE_NO_MAIN() {
		return ATT_FILE_NO_MAIN;
	}

	public void setATT_FILE_NO_MAIN(String aTT_FILE_NO_MAIN) {
		ATT_FILE_NO_MAIN = aTT_FILE_NO_MAIN;
	}

	public String getRCP_NA_TIP() {
		return RCP_NA_TIP;
	}

	public void setRCP_NA_TIP(String rCP_NA_TIP) {
		RCP_NA_TIP = rCP_NA_TIP;
	}

	public String getMANUAL() {
		return MANUAL;
	}

	public void setMANUAL(String mANUAL) {
		MANUAL = mANUAL;
	}

	public String getMANUAL_IMG() {
		return MANUAL_IMG;
	}

	public void setMANUAL_IMG(String mANUAL_IMG) {
		MANUAL_IMG = mANUAL_IMG;
	}

	public BigDecimal getViews() {
		return views;
	}

	public void setViews(BigDecimal views) {
		this.views = views;
	}
	
}
