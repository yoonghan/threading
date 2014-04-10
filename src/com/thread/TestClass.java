package com.thread;

import com.thread.util.ThreadContext;


public class TestClass {
	
	public TestClass(){
		
		final int RUNS = 100;
		
		Thread[] threads = new Thread[RUNS];
		
		for(int i=0; i<RUNS; i++){
			threads[i] = new CustomThread("t"+i);
		}
		
		for(Thread t: threads){
			t.start();
		}
		
		//need not wait join.
	}
	

	public static void main(String args[]){
		new TestClass();
	}
	
}

class CustomThread extends Thread{
	
	public CustomThread(String name){
		super(name);
	}
	
	public void run(){
		String name = getName(); //this is how thread get their name too.
		ThreadContext.setValue(Keywords.HTTP_REFERER, name);
		ThreadContext.setValue(Keywords.USER_AGENT, name);
		
		new BusinessService(name).businessMethod();
		
		try{
			long sleepvalue = (long)Math.floor(Math.random()*50)*10;
			Thread.sleep(sleepvalue);
		}catch(Exception e){
			//do nothing.
		}
		
		ThreadContext.unset();
	}
	
}

class BusinessService{
	
	private final String NAME;
	
	public BusinessService(String name){
		NAME=name;
	}
	
	public void businessMethod(){
		String referer=(String)ThreadContext.getValue(Keywords.HTTP_REFERER);
		String userAgent=(String)ThreadContext.getValue(Keywords.USER_AGENT);
		System.out.println("1:"+referer);
		System.out.println("2:"+userAgent);
		if(userAgent.equals(referer) == false && userAgent.equals(NAME) == false){
			//impossible exception just to prove.
			throw new RuntimeException("The local thread is not working");
		}
	}
}

interface Keywords{
	public final static String HTTP_REFERER="http://google.com?test=1";
	public final static String USER_AGENT="mozilla";
}

