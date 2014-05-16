package com.android.gallery3d;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Transformation;
import android.widget.Gallery;
import android.widget.ImageView;

@SuppressWarnings("deprecation")
public class GallyFlow extends Gallery {

	public GallyFlow(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		setStaticTransformationsEnabled(true);
	}

	public GallyFlow(Context context, AttributeSet attrs) {
		super(context, attrs);
		setStaticTransformationsEnabled(true);
	}

	public GallyFlow(Context context) {
		super(context);
		setStaticTransformationsEnabled(true);
	}

	private int maxZoom = -300; // 最大缩放值
	private int maxRotateAngle = 60; // 最大旋转角度
	private int galleryOfCenter; //gallery展示区域的中心点
	private Camera camera = new Camera();

	// 获得gallery展示区域的中心点
	private int getGalleryOfCenter() {

		return (getWidth() - getPaddingLeft() - getPaddingRight()) / 2
				+ getPaddingLeft();
	}
	
	/**
	 * 当gallery尺寸发生变化时候自动调用
	 */
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		galleryOfCenter = getGalleryOfCenter();
	}

	// 获得当前展示图片的中心点
	private int getViewOfCenter(View view) {
		return  view.getLeft() + view.getWidth() / 2;
		
	}

	// 图片变形
	@Override
	protected boolean getChildStaticTransformation(View child, Transformation t) {
		// View child指定的图片
		// Transformation 变形的样子
		
		int width = child.getLayoutParams().width;
//		int height = child.getHeight();
		
		int rotateAngle = 0;
		//指定变形的样式
		t.clear();
		t.setTransformationType(Transformation.TYPE_MATRIX);
		
		//图片中心点
		int childOfCenter = getViewOfCenter(child);
		
		//判断一下当前图片是否在gallery中心
		if (childOfCenter == galleryOfCenter) {
			transformationBitmap((ImageView)child,t,rotateAngle);
		}else {
			//经过换算得到角度
			rotateAngle = (int) ((float)(galleryOfCenter - childOfCenter)*maxRotateAngle/width );
//			rotateAngle = (int) ((float)(childOfCenter - galleryOfCenter )/width *maxRotateAngle);
			//最大角度是50  需要转换成50以内的度数
			if(Math.abs(rotateAngle) > maxRotateAngle){
				//-50  ----50
				System.out.println("----------->>"+rotateAngle);
				rotateAngle = rotateAngle > 0? maxRotateAngle : -maxRotateAngle;
				System.out.println("----------->>"+rotateAngle);
			}
//			if(Math.abs(rotateAngle) < 20){
//				//-50  ----50
//				rotateAngle = 0;
//			}
			transformationBitmap((ImageView)child,t,rotateAngle);
		}
		
		

		return true;
		// return super.getChildStaticTransformation(child, t);
	}

	//图片变形核心方法
	private void transformationBitmap(ImageView child, Transformation t, int rotateAngle) {
		camera.save(); //备份
		//变形样式
		Matrix imageMatrix = t.getMatrix();
		//得到图片宽高
		int width = child.getLayoutParams().width;
		int height = child.getLayoutParams().height;
		
		int rotate = Math.abs(rotateAngle); //图片的旋转度
		
		camera.translate(0.0f, 0.0f, 100.0f);
		if (rotate <maxRotateAngle) {
			float zoom = (float) (rotate * 1.5 + maxZoom);
			camera.translate(0.0f, 0.0f, zoom);
			
			//设置图片的透明度
			child.setAlpha((int) (255 - rotate * 2.5));
		}
		
		//图像的旋转
//		camera.rotateX(rotateAngle);
		camera.rotateY(rotateAngle);
//		camera.rotateZ(rotateAngle);
		
		//应用当前图像处理效果  交给图像矩阵就可以
		camera.getMatrix(imageMatrix);
		
		imageMatrix.preTranslate(-(width/2), -(height/2));
		imageMatrix.postTranslate((width/2), (height/2));
		
		camera.restore(); // 还原
		
	}

}
