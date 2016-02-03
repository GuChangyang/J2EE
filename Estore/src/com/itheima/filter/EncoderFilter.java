package com.itheima.filter;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class EncoderFilter implements Filter{
    private FilterConfig config = null;
    private ServletContext context = null;
    private String encode = null;
    
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		//--响应乱码解决
		response.setCharacterEncoding(encode);
		response.setContentType("text/html;charset=" + encode);
		// 利用装饰设计模式改变request 对象，和获取参数的方法，从而解决全站乱码
		chain.doFilter(new MyHttpServletRequest((HttpServletRequest)request), response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		this.config= filterConfig;
		this.context = filterConfig.getServletContext();
		this.encode = context.getInitParameter("encode");
	}
  
private class MyHttpServletRequest extends HttpServletRequestWrapper{
    	private HttpServletRequest request = null;
    	boolean isNotEncode = true;
    	public MyHttpServletRequest(HttpServletRequest request){
    		super(request);
    		this.request = request;
    	}
    	@Override
    	public Map<String,String[]> getParameterMap() {
    		// TODO Auto-generated method stub
    		try {
    		if(request.getMethod().equalsIgnoreCase("POST")){
    			request.setCharacterEncoding(encode);
    			return request.getParameterMap();
    		}
    		else if (request.getMethod().equalsIgnoreCase("GET")){   			   			
	    			Map<String,String[]> map = request.getParameterMap();
	    			if(isNotEncode){
	    			for(Map.Entry<String, String[]> entry : map.entrySet()){
	    				String[] vs = entry.getValue();
	    				for(int i = 0;i<vs.length;i++){
	    					vs[i] = new String(vs[i].getBytes("iso8859-1"),encode);
	    				}
	     			}
	    			isNotEncode = false;
    			}
    			return map;
    		}else{
    			return request.getParameterMap();
    		}    		
    		//return super.getParameterMap();
    		}catch (Exception e) {
				// TODO: handle exception
    			e.printStackTrace();
    			throw new RuntimeException(e);
			}
    		
    	}
    	@Override
    	public String[] getParameterValues(String name) {
    		// TODO Auto-generated method stub
    		return getParameterMap().get(name);
    	}
    	@Override
    	public String getParameter(String name) {
    		// TODO Auto-generated method stub
    		return getParameterValues(name) == null?null:getParameterValues(name)[0];
    	}
    }
}
  