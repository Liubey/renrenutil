package com.liubey.greatstepone.main;

import java.util.Map;

import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.DefaultHttpClient;

import com.liubey.greatstepone.util.AccountsUtil;
import com.liubey.greatstepone.util.CaptchaUtil;
import com.liubey.greatstepone.util.Robot;

public class StartUp {

	/**
	 * 启动方法
	 * @param args
	 */
	public static void main(String[] args) {
		String readyForVisit = "221277642";
		AccountsUtil.initAccounts();
		AccountsUtil.printALLAccount();
		for(int i=0;i<20;i++) {
			for(Map.Entry<String, Robot> entry : AccountsUtil.getAccouts().entrySet()) {
				Robot robot = AccountsUtil.getAccount(entry.getKey());
				if(robot.getHttpClient() == null) {
					DefaultHttpClient httpCilent = new DefaultHttpClient();
					HttpClientParams.setCookiePolicy(httpCilent.getParams(),CookiePolicy.BROWSER_COMPATIBILITY);
					robot.setHttpClient(httpCilent);
				}
//				if(!robot.isLogin()) {
//					GreatThing.fakeVisitIndex(robot);
//					if(GreatThing.isCaptcha(robot)) {
//						System.out.println(robot + "需要输入验证码");
//						String iCode = CaptchaUtil.getCode(robot);
//						robot.setCaptcha(iCode);
//						GreatThing.ajaxLogin(robot);
//					}else {
//						GreatThing.login(robot);
//					}
//				}
//				GreatThing.visit(robot, readyForVisit);
				
				if(!robot.isLogin()) {
					GreatThing3G.fakeVisitIndex(robot);
					String iCode = CaptchaUtil.getCode3G(robot);
					robot.setCaptcha(iCode);
					GreatThing3G.login(robot);
				}
				GreatThing3G.visit(robot, readyForVisit);
			}
		}
	}

}
