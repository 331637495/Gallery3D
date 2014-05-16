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

	private int maxZoom = -300; // �������ֵ
	private int maxRotateAngle = 60; // �����ת�Ƕ�
	private int galleryOfCenter; //galleryչʾ��������ĵ�
	private Camera camera = new Camera();

	// ���galleryչʾ��������ĵ�
	private int getGalleryOfCenter() {

		return (getWidth() - getPaddingLeft() - getPaddingRight()) / 2
				+ getPaddingLeft();
	}
	
	/**
	 * ��gallery�ߴ緢���仯ʱ���Զ�����
	 */
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		galleryOfCenter = getGalleryOfCenter();
	}

	// ��õ�ǰչʾͼƬ�����ĵ�
	private int getViewOfCenter(View view) {
		return  view.getLeft() + view.getWidth() / 2;
		
	}

	// ͼƬ����
	@Override
	protected boolean getChildStaticTransformation(View child, Transformation t) {
		// View childָ����ͼƬ
		// Transformation ���ε�����
		
		int width = child.getLayoutParams().width;
//		int height = child.getHeight();
		
		int rotateAngle = 0;
		//ָ�����ε���ʽ
		t.clear();
		t.setTransformationType(Transformation.TYPE_MATRIX);
		
		//ͼƬ���ĵ�
		int childOfCenter = getViewOfCenter(child);
		
		//�ж�һ�µ�ǰͼƬ�Ƿ���gallery����
		if (childOfCenter == galleryOfCenter) {
			transformationBitmap((ImageView)child,t,rotateAngle);
		}else {
			//��������õ��Ƕ�
			rotateAngle = (int) ((float)(galleryOfCenter - childOfCenter)*maxRotateAngle/width );
//			rotateAngle = (int) ((float)(childOfCenter - galleryOfCenter )/width *maxRotateAngle);
			//���Ƕ���50  ��Ҫת����50���ڵĶ���
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

	//ͼƬ���κ��ķ���
	private void transformationBitmap(ImageView child, Transformation t, int rotateAngle) {
		camera.save(); //����
		//������ʽ
		Matrix imageMatrix = t.getMatrix();
		//�õ�ͼƬ���
		int width = child.getLayoutParams().width;
		int height = child.getLayoutParams().height;
		
		int rotate = Math.abs(rotateAngle); //ͼƬ����ת��
		
		camera.translate(0.0f, 0.0f, 100.0f);
		if (rotate <maxRotateAngle) {
			float zoom = (float) (rotate * 1.5 + maxZoom);
			camera.translate(0.0f, 0.0f, zoom);
			
			//����ͼƬ��͸����
			child.setAlpha((int) (255 - rotate * 2.5));
		}
		
		//ͼ�����ת
//		camera.rotateX(rotateAngle);
		camera.rotateY(rotateAngle);
//		camera.rotateZ(rotateAngle);
		
		//Ӧ�õ�ǰͼ����Ч��  ����ͼ�����Ϳ���
		camera.getMatrix(imageMatrix);
		
		imageMatrix.preTranslate(-(width/2), -(height/2));
		imageMatrix.postTranslate((width/2), (height/2));
		
		camera.restore(); // ��ԭ
		
	}

}
