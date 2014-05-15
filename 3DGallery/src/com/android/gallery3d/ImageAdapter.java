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

	//���ɴ��е�ӰЧ����ͼƬ
	@SuppressWarnings("deprecation")
	public void createReflectionBitmap() {
		
		//ѭ������
		int index = 0;
		
		//ͼƬ�뷭תͼƬ֮��ļ�϶
		int reflectionGap = 5;
		for (int imageID : imageIds) {
			//�õ�ԴͼƬ
			Bitmap resource = BitmapFactory.decodeResource(context.getResources(), imageID);
			//ԴͼƬ�Ŀ��
			int width = resource.getWidth();
			int height = resource.getHeight();
			
			//��ӰͼƬ
			/**
			 * source 			    ԴͼƬ
			 * x, y  			    ָ�����Ͻ�����
			 * width, height	    ��ӰͼƬ�Ŀ��
			 *  Matrix m     	   ָ��ͼƬ����ʽ
			 *  boolean filter   ������
			 */
			
			Matrix m = new Matrix();
			//���÷�תЧ��
			m.setScale(1.0f, -1.0f);  //��ֱ��ת Y��  ˮƽ��ת X��
			Bitmap bitmap2 = Bitmap.createBitmap(resource, 0, height/2, width, height/2, m, false);
			
			//�ϳ���ʾ�Ĵ�bitmap
			Bitmap bitmap = Bitmap.createBitmap(width, height + height/2 + reflectionGap , Config.ARGB_8888);
			
			//��������
			Canvas canvas = new Canvas(bitmap);
			//����ԴͼƬ
			canvas.drawBitmap(resource, 0, 0, null);
			//���Ƽ��
			Paint paint = new Paint();
			canvas.drawRect(0, height, width, height + reflectionGap, paint);;
			//���Ƶ�ӰͼƬ
			canvas.drawBitmap(bitmap2, 0, height + reflectionGap, null);
			//ps ����  ����
			Paint paint2 = new Paint();
			//����
			paint2.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
			//����
			/**
			 * �ӵ�һ������ڶ����㽥��
			 * ��ɫ��0x70ffffff �� 0x00ffffff
			 */
			LinearGradient shader = new LinearGradient(0, height, 0, bitmap.getHeight(), 0x70ffffff, 0x00ffffff, TileMode.CLAMP);
			paint2.setShader(shader);  //��ɫ��
			canvas.drawRect(0, height, width, bitmap.getHeight(), paint2);
			/**
			 * //��λͼ��Y����שģʽ 0x70ffffff, 0x00ffffff
				TileMode����һ�������֣� 
				CLAMP �������Ⱦ������ԭʼ�߽緶Χ���Ḵ�Ʒ�Χ�ڱ�ԵȾɫ�����ֱ�ˮЧ���� 
				REPEAT �������������ظ���Ⱦ��ͼƬ��ƽ�̡�(��������ƽ��Ч��) 
				MIRROR �������������ظ���Ⱦ��ͼƬ�������REPEAT �ظ���ʽ��һ���������Ծ���ʽƽ�̡�
			 */
			
			//��ʾԴͼƬ
			ImageView imageView = new ImageView(context);
			imageView.setImageBitmap(bitmap);
			imageView.setLayoutParams(new GallyFlow.LayoutParams(240,320));
			images[index++] = imageView;
		}
		
	}

}
