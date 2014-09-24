package com.betalife.sushibuffet.util;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.util.Hashtable;
import java.util.Map;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;

public class AsyncImageLoader {

	private Map<String, SoftReference<Drawable>> imageCache;
	private static AsyncImageLoader instance;

	private AsyncImageLoader() {
		imageCache = new Hashtable<String, SoftReference<Drawable>>();

	}

	public static AsyncImageLoader getInstance() {
		if (instance == null) {
			instance = new AsyncImageLoader();
		}
		return instance;
	}

	public void loadDrawable(final String imageUrl, final ImageCallback imageCallback) {

		/**
		 * 首先判断图片是否已经在内存中存在 如果存在，直接获取图片的Drawable对象
		 */
		if (imageCache.containsKey(imageUrl)) {
			SoftReference<Drawable> softReference = imageCache.get(imageUrl);
			Drawable drawable = softReference.get();
			if (drawable != null) {
				imageCallback.imageLoaded(drawable, imageUrl);
			}
		}

		/**
		 * 判断图片在内存中不存在，则连接网络下载 创建Handler对象，用于在图片下载成功后通知UI线程
		 */
		final Handler handler = new Handler() {

			@Override
			public void handleMessage(Message message) {
				imageCallback.imageLoaded((Drawable) message.obj, imageUrl);
			}
		};

		/**
		 * 创建线程用于连接网络下载图片
		 */
		new Thread() {

			@Override
			public void run() {
				// 获取图片
				Drawable drawable = loadImageFromUrl(imageUrl);

				// 缓存图片
				imageCache.put(imageUrl, new SoftReference<Drawable>(drawable));

				// 获取Message对象，通知Handler
				Message message = handler.obtainMessage(0, drawable);
				handler.sendMessage(message);
			}
		}.start();

	}

	public static Drawable loadImageFromUrl(String url) {
		Drawable drawable = null;
		try {
			drawable = Drawable.createFromStream(new URL(url).openStream(), "");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return drawable;
	}

	public interface ImageCallback {
		public void imageLoaded(Drawable imageDrawable, String imageUrl);
	}
}
