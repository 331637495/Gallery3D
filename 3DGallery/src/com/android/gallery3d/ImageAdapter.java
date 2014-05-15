package com.android.gallery3d;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader.TileMode;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {

	private Context context;
	private int[] imageIds;
	private ImageView[] images;

	public ImageAdapter(Context	context, int[] imageIds) {
		this.context = context;
		this.imageIds = imageIds;
		
		images = new ImageView[imageIds.length];
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return images.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return images[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		return images[position];
	}

	//生成带有倒影效果的图片
	@SuppressWarnings("deprecation")
	public void createReflectionBitmap() {
		
		//循环操作
		int index = 0;
		
		//图片与翻转图片之间的间隙
		int reflectionGap = 5;
		for (int imageID : imageIds) {
			//得到源图片
			Bitmap resource = BitmapFactory.decodeResource(context.getResources(), imageID);
			//源图片的宽高
			int width = resource.getWidth();
			int height = resource.getHeight();
			
			//倒影图片
			/**
			 * source 			    源图片
			 * x, y  			    指定左上角坐标
			 * width, height	    倒影图片的宽高
			 *  Matrix m     	   指定图片的样式
			 *  boolean filter   过滤器
			 */
			
			Matrix m = new Matrix();
			//设置翻转效果
			m.setScale(1.0f, -1.0f);  //垂直翻转 Y轴  水平翻转 X轴
			Bitmap bitmap2 = Bitmap.createBitmap(resource, 0, height/2, width, height/2, m, false);
			
			//合成显示的大bitmap
			Bitmap bitmap = Bitmap.createBitmap(width, height + height/2 + reflectionGap , Config.ARGB_8888);
			
			//创建画布
			Canvas canvas = new Canvas(bitmap);
			//绘制源图片
			canvas.drawBitmap(resource, 0, 0, null);
			//绘制间隔
			Paint paint = new Paint();
			canvas.drawRect(0, height, width, height + reflectionGap, paint);;
			//绘制倒影图片
			canvas.drawBitmap(bitmap2, 0, height + reflectionGap, null);
			//ps 遮罩  渐变
			Paint paint2 = new Paint();
			//遮罩
			paint2.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
			//渐变
			/**
			 * 从第一个点向第二个点渐变
			 * 颜色从0x70ffffff 到 0x00ffffff
			 */
			LinearGradient shader = new LinearGradient(0, height, 0, bitmap.getHeight(), 0x70ffffff, 0x00ffffff, TileMode.CLAMP);
			paint2.setShader(shader);  //着色器
			canvas.drawRect(0, height, width, bitmap.getHeight(), paint2);
			/**
			 * //在位图上Y方向花砖模式 0x70ffffff, 0x00ffffff
				TileMode：（一共有三种） 
				CLAMP ：如果渲染器超出原始边界范围，会复制范围内边缘染色。（钢笔水效果） 
				REPEAT ：横向和纵向的重复渲染器图片，平铺。(电脑桌面平铺效果) 
				MIRROR ：横向和纵向的重复渲染器图片，这个和REPEAT 重复方式不一样，他是以镜像方式平铺。
			 */
			
			//显示源图片
			ImageView imageView = new ImageView(context);
			imageView.setImageBitmap(bitmap);
			imageView.setLayoutParams(new GallyFlow.LayoutParams(240,320));
			images[index++] = imageView;
		}
		
	}

}
