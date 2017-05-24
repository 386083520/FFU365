/*
 * Copyright (c) 2013. wyouflf (wyouflf@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.administrator.ffu365.util.view;

import android.app.Activity;
import android.preference.PreferenceActivity;
import android.view.View;
import android.view.View.OnClickListener;


import com.example.administrator.ffu365.util.view.annotation.CheckNet;
import com.example.administrator.ffu365.util.view.annotation.ViewById;
import com.example.administrator.ffu365.util.view.annotation.ViewFinder;
import com.example.administrator.ffu365.util.view.annotation.event.OnClick;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * 
 * ============================================================
 * 
 * project name :
 * 
 * copyright ZENG HUI (c) 2015
 * 
 * author : HUI
 * 
 * version : 1.0
 * 
 * date created : On October, 2015
 * 
 * description : 注解工具
 * 
 * revision history :
 * 
 * ============================================================
 * 
 */
public class ViewUtils {

	private ViewUtils() {
	}

	public static void inject(View view) {
		injectObject(view, new ViewFinder(view));
	}

	public static void inject(Activity activity) {
		injectObject(activity, new ViewFinder(activity));
	}

	public static void inject(PreferenceActivity preferenceActivity) {
		injectObject(preferenceActivity, new ViewFinder(preferenceActivity));
	}

	public static void inject(Object handler, View view) {
		injectObject(handler, new ViewFinder(view));
	}

	public static void inject(Object handler, Activity activity) {
		injectObject(handler, new ViewFinder(activity));
	}


	private static void injectObject(Object handler, ViewFinder finder) {

		Class<?> handlerType = handler.getClass();

		// inject view
		// 注入View , 遍历所有属性，注解在一定的程度上会影响性能
		Field[] fields = handlerType.getDeclaredFields();
		if (fields != null && fields.length > 0) {
			for (Field field : fields) {
				ViewById viewInject = field.getAnnotation(ViewById.class);
				if (viewInject != null) {
					try {
						View view = finder.findViewById(viewInject.value());
						if (view != null) {
							field.setAccessible(true);
							field.set(handler, view);
						}
					} catch (Throwable e) {

					}
				}
			}
		}

		// inject event
		// 注入事件
		Method[] methods = handlerType.getDeclaredMethods();
		if (methods != null && methods.length > 0) {
			for (Method method : methods) {
				// 新加入一种方式,在任意方法上加OnClick点击注解
				OnClick onClick = method.getAnnotation(OnClick.class);
				if (onClick != null) {
					int[] ids = onClick.value();
					for (int i = 0; i < ids.length; i++) {
						View view = finder.findViewById(ids[i]);
						if (view != null) {
							// 是否需要检测网络
							CheckNet checkNet = method
									.getAnnotation(CheckNet.class);
							view.setOnClickListener(new DeclaredOnClickListener(
									handler, view, method,
									checkNet != null));
						}
					}
				}
			}
		}

	}

	/**
	 * An implementation of OnClickListener that attempts to lazily load a named
	 * click handling method from a parent or ancestor context.
	 */
	private static class DeclaredOnClickListener implements OnClickListener {
		private final View mHostView;
		private Method mMethod;
		private Object mHandler;
		// 是否检测网络
		private final boolean mIsCheckNet;

		public DeclaredOnClickListener(Object handler, View hostView,
				Method method, boolean isCheckNet) {
			this.mHandler = handler;
			this.mHostView = hostView;
			this.mMethod = method;
			this.mIsCheckNet = isCheckNet;
		}

		@Override
		public void onClick(View v) {

			if (mMethod != null) {
				// 忽略访问权限，可以执行私有的也可以执行父类的
				mMethod.setAccessible(true);
			}

			// 执行这两个方法
			try {
				mMethod.invoke(mHandler, mHostView);
			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
			} catch (IllegalArgumentException e1) {
				e1.printStackTrace();
			} catch (InvocationTargetException e1) {
				e1.printStackTrace();
			}
			
			try {
				mMethod.invoke(mHandler);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}

}
