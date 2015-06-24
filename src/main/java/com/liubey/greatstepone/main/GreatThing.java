package com.liubey.greatstepone.main;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.liubey.greatstepone.util.Robot;

public class GreatThing {
    
    public static boolean login(Robot robot) {
    	String renRenLoginURL = "http://www.renren.com/PLogin.do"; 
        HttpPost httpost = new HttpPost(renRenLoginURL);  
        // All the parameters post to the web site  
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();  
        nvps.add(new BasicNameValuePair("origURL", "http://www.renren.com"));  
        nvps.add(new BasicNameValuePair("domain", "renren.com"));  
        nvps.add(new BasicNameValuePair("isplogin", "true")); 
        //nvps.add(new BasicNameValuePair("key_id", "1"));
        nvps.add(new BasicNameValuePair("formName", ""));  
        nvps.add(new BasicNameValuePair("method", ""));  
        nvps.add(new BasicNameValuePair("submit", "登录"));  
        nvps.add(new BasicNameValuePair("email", robot.getEmail()));  
        nvps.add(new BasicNameValuePair("password", robot.getPassword()));
        if(robot.getCaptcha() != null) {
            //nvps.add(new BasicNameValuePair("captcha", "web_login"));  
            nvps.add(new BasicNameValuePair("icode", robot.getCaptcha()));
        }
        try {  
            httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));  
            HttpResponse response = robot.getHttpClient().execute(httpost);
            String redirectLocation = getRedirectLocation(response);
            sendRedirect(robot,redirectLocation);
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        } finally {  
            httpost.abort();  
        }
        System.out.println("登陆成功！" + robot);
        robot.setLogin(true);
        return true;  
    }
    
    public static boolean ajaxLogin(Robot robot) {
    	String renRenLoginURL = "http://www.renren.com/ajaxLogin/login"; 
        HttpPost httpost = new HttpPost(renRenLoginURL);  
        // All the parameters post to the web site  
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();  
        nvps.add(new BasicNameValuePair("origURL", "http://www.renren.com/home"));  
        nvps.add(new BasicNameValuePair("domain", "renren.com"));  
        //nvps.add(new BasicNameValuePair("isplogin", "true")); 
        nvps.add(new BasicNameValuePair("key_id", "1"));
        //nvps.add(new BasicNameValuePair("formName", ""));  
        //nvps.add(new BasicNameValuePair("method", ""));  
        //nvps.add(new BasicNameValuePair("submit", "登录"));  
        nvps.add(new BasicNameValuePair("email", robot.getEmail()));  
        nvps.add(new BasicNameValuePair("password", robot.getPassword()));
        nvps.add(new BasicNameValuePair("captcha", "web_login"));  
        nvps.add(new BasicNameValuePair("icode", robot.getCaptcha()));
        nvps.add(new BasicNameValuePair("rkey", "d0cf42c2d3d337f9e5d14083f2d52cb2"));
        //StringBuilder sb = new StringBuilder();
        HttpResponse response = null;
        try {  
            httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));  
            response = robot.getHttpClient().execute(httpost);
    		//HttpEntity responseEntity = response.getEntity();
    	    //byte[] bytes = EntityUtils.toByteArray(responseEntity);
    	    //String charset="UTF-8";
    	    //InputStreamReader isr = new InputStreamReader(new InputStream(bytes));
    	    //java.io.BufferedReader br = new java.io.BufferedReader(isr);
    	    //String tempbf = new String(bytes);
    	    //while((tempbf=br.readLine())!=null){
    	    //	sb.append(tempbf);
    	    //	sb.append("\r\n");
    	    //}
            String redirectLocation = getRedirectLocation(response);
            sendRedirect(robot,redirectLocation);
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        } finally {  
            httpost.abort();  
        }
        System.out.println("登陆成功！" + robot);
        robot.setLogin(true);
        return true;  
    }

    
    private static String getRedirectLocation(HttpResponse response) {  
        Header locationHeader = response.getFirstHeader("Location");  
        if (locationHeader == null) {  
            return null;  
        }
        try {
			EntityUtils.consume(response.getEntity());
		} catch (IOException e) {
			e.printStackTrace();
		}
        return locationHeader.getValue();  
    }  
  
    private static boolean sendRedirect(Robot robot,String redirectLocation) {  
        HttpGet httpget = new HttpGet(redirectLocation);  
        //ResponseHandler<String> responseHandler = new BasicResponseHandler();  
        //String responseBody = "";
        HttpResponse response = null;
        try {  
        	response = robot.getHttpClient().execute(httpget);  
        } catch (Exception e) {  
            e.printStackTrace();  
            response = null;  
        } finally {  
            
            try {
				EntityUtils.consume(response.getEntity());
			} catch (IOException e) {
				e.printStackTrace();
			}
            httpget.abort(); 
            //httpclient.getConnectionManager().shutdown();  
        }  
        return true;  
    }
    
//    public void printText() {  
//        if (login()) {  
//            String redirectLocation = getRedirectLocation();  
//            if (redirectLocation != null) {  
//                System.out.println(getText(redirectLocation));  
//            }  
//        }  
//    }
    
    public static void fakeVisitIndex(Robot robot) {
    	String url = "http://www.renren.com";
        HttpGet httpget = new HttpGet(url);
        HttpResponse httpResponse = null;
        try {
			httpResponse = robot.getHttpClient().execute(httpget);
			String content = EntityUtils.toString(httpResponse.getEntity());  
	          
	        //System.out.println("Response content:" + content);
	        robot.set_rtk(content.substring(1945,1953));
			EntityUtils.consume(httpResponse.getEntity());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally { 
			try {
				EntityUtils.consume(httpResponse.getEntity());
			} catch (IOException e) {
				e.printStackTrace();
			}
			httpget.abort();
        }
    }
    
    /**
     * 登陆时是否需要输入验证码
     * @param robot
     * @return true为需要，false为不需要
     */
    public static boolean isCaptcha(Robot robot) {
    	String verifyUrl = "http://www.renren.com/ajax/ShowCaptcha";
    	HttpPost httpPost = new HttpPost(verifyUrl);
    	httpPost.addHeader("Host","www.renren.com");
    	httpPost.addHeader("User-Agent","Mozilla/5.0 (Windows NT 5.1; rv:15.0) Gecko/20100101 Firefox/15.0.1");
    	httpPost.addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
    	httpPost.addHeader("Accept-Language","zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
    	httpPost.addHeader("Accept-Encoding","gzip, deflate");
    	httpPost.addHeader("Connection","keep-alive");
    	httpPost.addHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
    	httpPost.addHeader("Refer","http://www.renren.com/");
    	//httpPost.addHeader("Content-Length","34");
    	//httpPost.addHeader("Cookie","mop_uniq_ckid=127.0.0.1_1329706834_683650382; __utma=10481322.147463089.1329888214.1329888214.1329888214.1; anonymid=h7iwqgf5-rgzwsx; _r01_=1; depovince=BJ; feedType=245929433_hot; jebecookies=4b6abcb1-6eb7-45a2-b2ac-a67ee8194037|||||; JSESSIONID=abc3mIHoY5U_v4DSwKVTt; ick_login=5ed36755-07b9-407d-bd16-c5c7daf13d19");
    	httpPost.addHeader("Pragma", "no-cache");
    	httpPost.addHeader("Cache-Control", "no-cache");
    	
    	List<NameValuePair> nvps = new ArrayList<NameValuePair>();
    	nvps.add(new BasicNameValuePair("email", robot.getEmail()));
    	nvps.add(new BasicNameValuePair("_rtk", robot.get_rtk()));
    	try {
			httpPost.setEntity(new UrlEncodedFormEntity(nvps,HTTP.UTF_8));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
    	
    	HttpResponse httpResponse = null;
    	StringBuilder sb = new StringBuilder();
		try {
			httpResponse = robot.getHttpClient().execute(httpPost);
			HttpEntity responseEntity = httpResponse.getEntity();
	    	byte[] bytes = EntityUtils.toByteArray(responseEntity);
	    	GZIPInputStream gzin = new GZIPInputStream(new ByteArrayInputStream(bytes));
	    	String charset="UTF-8";
	    	InputStreamReader isr = new InputStreamReader(gzin, charset);
	    	java.io.BufferedReader br = new java.io.BufferedReader(isr);
	    	String tempbf;
	    	
	    	while((tempbf=br.readLine())!=null){
	    		sb.append(tempbf);
	    		sb.append("\r\n");
	    	}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally { 
			try {
				EntityUtils.consume(httpResponse.getEntity());
			} catch (IOException e) {
				e.printStackTrace();
			}
            httpPost.abort();  
        } 
    	return sb.toString().trim().equals("0")?false:true;
    }
    
    public static byte[] showCaptcha(Robot robot) {
    	String url = "http://icode.renren.com/getcode.do?t=web_login&rnd=" + Math.random();
    	HttpGet get = new HttpGet(url);
    	get.addHeader("Host","icode.renren.com");
    	get.addHeader("User-Agent","Mozilla/5.0 (Windows NT 5.1; rv:15.0) Gecko/20100101 Firefox/15.0.1");
    	get.addHeader("Accept","image/png,image/*;q=0.8,*/*;q=0.5");
    	get.addHeader("Accept-Language","zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
    	get.addHeader("Accept-Encoding","gzip, deflate");
    	get.addHeader("Connection","keep-alive");
    	get.addHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
    	get.addHeader("Refer","http://www.renren.com/");
    	HttpResponse response = null;
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
    	try {  
            response = robot.getHttpClient().execute(get);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream is = entity.getContent();
				byte[] buf = new byte[1024];
				int len = -1;
				while ((len = is.read(buf)) > -1) {
					baos.write(buf, 0, len);
				}
			}
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {
			try {
				EntityUtils.consume(response.getEntity());
			} catch (IOException e) {
				e.printStackTrace();
			}
			get.abort();
        }
    	//robot.setCaptcha(code);
    	return baos.toByteArray();
    }
    
    public static boolean visit(Robot robot,String id) {
    	String url = "http://www.renren.com/" + id + "/profile";
    	HttpGet get = new HttpGet(url);
    	HttpResponse response = null;
    	try {  
            response = robot.getHttpClient().execute(get);  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {
			try {
				EntityUtils.consume(response.getEntity());
			} catch (IOException e) {
				e.printStackTrace();
			}
			get.abort();
        }
    	System.out.println("机器人:" + robot + ":访问:" + id + "成功!");
    	return true;
    }
}
