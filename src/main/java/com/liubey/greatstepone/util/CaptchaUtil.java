package com.liubey.greatstepone.util;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.DefaultHttpClient;

import com.liubey.greatstepone.main.GreatThing;
import com.liubey.greatstepone.main.GreatThing3G;

public class CaptchaUtil {
	public static String getCode(Robot robot) {
		JFrame frame = new JFrame("验证码");
		JLabel label = new JLabel(new ImageIcon(GreatThing.showCaptcha(robot)),
				JLabel.CENTER);
		String input = JOptionPane.showInputDialog(frame, label,"请输入验证码", JOptionPane.DEFAULT_OPTION);
		frame.dispose();
		return input;
	}
	
	public static String getCode3G(Robot robot) {
		JFrame frame = new JFrame("验证码");
		JLabel label = new JLabel(new ImageIcon(GreatThing3G.showCaptcha(robot)),
				JLabel.CENTER);
		String input = JOptionPane.showInputDialog(frame, label,"请输入验证码", JOptionPane.DEFAULT_OPTION);
		frame.dispose();
		return input;
	}
	
	public static void main(String[] args) {
		Robot robot = new Robot();
		DefaultHttpClient httpCilent = new DefaultHttpClient();
		HttpClientParams.setCookiePolicy(httpCilent.getParams(),CookiePolicy.BROWSER_COMPATIBILITY);
		robot.setHttpClient(httpCilent);
		GreatThing.fakeVisitIndex(robot);
		String code = "";
		code = CaptchaUtil.getCode(robot);
		System.out.println("captcha:" + code);
	}
}
