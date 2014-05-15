package com.android.gallery3d;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

	private GallyFlow gallyFlow;
	private int[] imageIds;
	private ImageAdapter imageAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initData();

		gallyFlow = (GallyFlow) findViewById(R.id.gallery);

		imageAdapter = new ImageAdapter(this,imageIds);
		
		//生成带有倒影效果的图片
		imageAdapter.createReflectionBitmap();
		
		gallyFlow.setAdapter(imageAdapter);
	}

	private void initData() {
		imageIds = new int[] { R.drawable.photo1, R.drawable.photo2,
				R.drawable.photo3, R.drawable.photo4, R.drawable.photo5,
				R.drawable.photo6, R.drawable.photo7, R.drawable.photo8 };
	}

	/*
	 * private class MyAdapter extends BaseAdapter{
	 * 
	 * @Override public int getCount() { // TODO Auto-generated method stub
	 * return imageIds.length; }
	 * 
	 * @Override public Object getItem(int position) { // TODO Auto-generated
	 * method stub return imageIds[position]; }
	 * 
	 * @Override public long getItemId(int position) { // TODO Auto-generated
	 * method stub return position; }
	 * 
	 * @Override public View getView(int position, View convertView, ViewGroup
	 * parent) { ImageView imageView = new ImageView(getApplicationContext());
	 * imageView.setImageResource(imageIds[position]);
	 * 
	 * 
	 * return imageView; }
	 * 
	 * }
	 */

}
