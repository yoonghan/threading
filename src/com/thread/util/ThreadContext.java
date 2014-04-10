package com.thread.util;

import java.util.HashMap;
import java.util.Map;

/**
 * This utility can be used for system web application program to set context variable.
 * @author yoong.han
 *
 */

public final class ThreadContext {
	
	private  Map<String,Object> contextAttributes = new HashMap<String, Object>();

	private static final ThreadLocal<ThreadContext> currentContext = new ThreadLocal<ThreadContext>(){
		protected ThreadContext initialValue() {
			return new ThreadContext();
		};
	};

	public static void setValue(String key, Object value){
		get().contextAttributes.put(key, value);
	}
	
	public static Object getValue(String key){
		return get().contextAttributes.get(key);
	}
	
	public static void unset(){
		get().contextAttributes.clear();
		currentContext.remove();
	}
	
	public static ThreadContext get(){
		return currentContext.get();
	}
}
