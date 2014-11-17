package com.betalife.sushibuffet.util;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.betalife.sushibuffet.util.AsyncImageLoader.ImageCallback;

public class ImageViewUtil {

	public static void setImage(String imageUrl, final ImageView thumb) {
		AsyncImageLoader asyncImageLoader = AsyncImageLoader.getInstance();
		asyncImageLoader.loadDrawable(imageUrl, new ImageCallback() {
			@Override
			public void imageLoaded(Drawable imageDrawable, String imageUrl) {
				thumb.setImageDrawable(imageDrawable);
			}
		});
	}
}
