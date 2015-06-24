package com.liubey.greatstepone.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import com.liubey.greatstepone.main.GreatThing3G;

public class AccountsUtil {
	private static final Map<String,Robot> accounts = new HashMap<String,Robot>();
	
	public static Map<String,Robot> getAccouts() {
		return accounts;
	}
	
	public static Robot getAccount(String id) {
		return accounts.get(id);
	}
	
	public static void initAccounts() {
		InputStream in;
		try {
			in = AccountsUtil.class.getClassLoader()  
                    .getResourceAsStream("accounts.properties");
			ResourceBundle rb = new PropertyResourceBundle(in);
			for(int i=1;i<=30;i++) {
				String accountString =rb.getString("account" + i);
				if(accountString != null) {
					String[] accountArray = accountString.split(",");
					Robot robot = new Robot();
					robot.setEmail(accountArray[0]);
					robot.setPassword(accountArray[1]);
					accounts.put(Integer.toString(i), robot);
				}
			}
			System.out.println("初始化账号完成，共初始化" + accounts.size() + "个账号！");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void printALLAccount() {
		System.out.println("从配置文件中读取的全部账号密码为：");
		for(Map.Entry<String, Robot> entry : accounts.entrySet()) {
			System.out.println("第" + entry.getKey() + "个账号:" + entry.getValue());
		}
	}
	
	public static void allLogin() {
		int loginCount = 0;
		while(loginCount < 30) {
			for(Map.Entry<String, Robot> entry : AccountsUtil.getAccouts().entrySet()) {
				Robot robot = AccountsUtil.getAccount(entry.getKey());
				//初始化httpClient
				if(robot.getHttpClient() == null) {
					robot.setHttpClient(HttpUtil.getHttpClient());
				}
				if(!robot.isLogin()) {
					GreatThing3G.fakeVisitIndex(robot);
					String iCode = CaptchaUtil.getCode3G(robot);
					robot.setCaptcha(iCode);
					GreatThing3G.login(robot);
					if(robot.isLogin()) {
						loginCount++;
						System.out.println("已登录数量为：" + loginCount);
					}
				}
			}
		}
		
		System.out.println("数量已够30，开整！！！");
	}
	
	public static String[] getReadyToVisit() {
		InputStream in;
		String[] accountArray = null;
		try {
			in = AccountsUtil.class.getClassLoader()  
                    .getResourceAsStream("readytovisit.properties");
			ResourceBundle rb = new PropertyResourceBundle(in);
			String accountsStr =rb.getString("accounts");
			if(accountsStr != null) {
				accountArray = accountsStr.split(",");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("待刷账号共" + accountArray.length + ":如下所示:" + accountArray);
		return accountArray;
	}
}
