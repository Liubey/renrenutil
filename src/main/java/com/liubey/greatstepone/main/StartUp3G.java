package com.liubey.greatstepone.main;

import java.util.Map;

import com.liubey.greatstepone.util.AccountsUtil;
import com.liubey.greatstepone.util.Robot;

public class StartUp3G {

	/**
	 * 启动方法 3G版
	 * @param args
	 */
	public static void main(String[] args) {
		String[] readyForVisit = AccountsUtil.getReadyToVisit();
		AccountsUtil.initAccounts();
		AccountsUtil.printALLAccount();
		AccountsUtil.allLogin();
		int visitCount = 0;
		for(int x=0;x<readyForVisit.length; x++) {
			for(int i=0;i<17;i++) {
				for(Map.Entry<String, Robot> entry : AccountsUtil.getAccouts().entrySet()) {
					Robot robot = entry.getValue();
					GreatThing3G.visit(robot, readyForVisit[x]);
					visitCount++;
					System.out.println("访问次数为:" + visitCount);
				}
			}
		}
		
	}

}
