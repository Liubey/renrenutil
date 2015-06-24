package com.liubey.greatstepone.main;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.liubey.greatstepone.util.Robot;

public class GreatThing3G {
    
    public static boolean login(Robot robot) {
    	String renRenLoginURL = "http://3g.renren.com/login.do?autoLogin=true&&fx=0"; 
        HttpPost httpost = new HttpPost(renRenLoginURL);  
        httpost.setHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/534.3 (KHTML, like Gecko) Chrome/6.0.472.63 Safari/534.3");
        // All the parameters post to the web site  
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();  
        nvps.add(new BasicNameValuePair("appid", ""));
        nvps.add(new BasicNameValuePair("c", ""));
        nvps.add(new BasicNameValuePair("email", robot.getEmail()));
        nvps.add(new BasicNameValuePair("password", robot.getPassword()));
        nvps.add(new BasicNameValuePair("lbskey", robot.getLbskey()));
        nvps.add(new BasicNameValuePair("login", "登陆"));
        nvps.add(new BasicNameValuePair("origURL", "/home.do"));
        nvps.add(new BasicNameValuePair("pq", ""));
        nvps.add(new BasicNameValuePair("ref", "http://m.renren.com/q.do?null"));
        nvps.add(new BasicNameValuePair("verifycode", robot.getCaptcha()));
        nvps.add(new BasicNameValuePair("verifykey", robot.getVerifykey())); 
        try {  
            httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));  
            HttpResponse response = robot.getHttpClient().execute(httpost);
            //String content = EntityUtils.toString(response.getEntity()); 
            //
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY) {
            	String redirectLocation = getRedirectLocation(response);
                String sid = redirectLocation.substring(redirectLocation.indexOf("sid=") + 4,redirectLocation.indexOf("&bm"));
                robot.setSid(sid);
                sendRedirect(robot,redirectLocation);
                System.out.println("登陆成功！" + robot);
                robot.setLogin(true);
            }else {
            	robot.setLogin(false);
            	return false;
            }
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        } finally {  
            httpost.abort();  
        }
        return true;  
    }
    
    private static String getRedirectLocation(HttpResponse response) {  
        Header locationHeader = response.getFirstHeader("Location");  
        if (locationHeader == null) {  
            return null;  
        }
        try {
			if(response != null) {
				EntityUtils.consume(response.getEntity());
			}
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
				if(response != null) {
					EntityUtils.consume(response.getEntity());
				}
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
    	String url = "http://3g.renren.com";
        HttpGet httpget = new HttpGet(url);
        HttpResponse httpResponse = null;
        try {
			httpResponse = robot.getHttpClient().execute(httpget);
			String content = EntityUtils.toString(httpResponse.getEntity());  
	          
	        //System.out.println("Response content:" + content);
	        //robot.setLbskey(content.substring(1205,1238));
	        //robot.setVerifykey(content.substring(1619,1648));
			GreatThing3G.parse3GRenrenIndex(content, robot);
			EntityUtils.consume(httpResponse.getEntity());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally { 
			try {
				if(httpResponse != null) {
					EntityUtils.consume(httpResponse.getEntity());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			httpget.abort();
        }
    }
    
    public static void parse3GRenrenIndex(String content,Robot robot) {
    	Document doc = Jsoup.parse(content);
    	Elements inputs = doc.getElementsByTag("input");
    	Element lbskey = inputs.get(1);
    	Element verifykey = inputs.get(8);
    	robot.setLbskey(lbskey.attr("value"));
    	robot.setVerifykey(verifykey.attr("value"));
    }
    
    public static byte[] showCaptcha(Robot robot) {
    	String url = "http://3g.renren.com/rndimg_wap?post=_REQUESTFRIEND_" + robot.getVerifykey() + "&rnd=" + Math.random();
    	HttpGet get = new HttpGet(url);
    	///get.addHeader("Host","3g.renren.com");
    	//get.addHeader("User-Agent","Mozilla/5.0 (Windows NT 5.1; rv:15.0) Gecko/20100101 Firefox/15.0.1");
    	//get.addHeader("Accept","image/png,image/*;q=0.8,*/*;q=0.5");
    	//get.addHeader("Accept-Language","zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
    	//get.addHeader("Accept-Encoding","gzip, deflate");
    	//get.addHeader("Connection","keep-alive");
    	//get.addHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
    	//get.addHeader("Refer","http://3g.renren.com/");
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
				if(response != null) {
					EntityUtils.consume(response.getEntity());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			get.abort();
        }
    	//robot.setCaptcha(code);
    	return baos.toByteArray();
    }
    
    public static boolean visit(Robot robot,String id) {
    	//String url = "http://3g.renren.com/profile.do?id=" + id;
    	String url = "http://3g.renren.com/profile.do?id=" + id + "&htf=731&sid=" + robot.getSid();
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
				if(response != null) {
					EntityUtils.consume(response.getEntity());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			get.abort();
        }
    	System.out.println("机器人:" + robot + ":访问:" + id + "成功!");
    	return true;
    }
}
