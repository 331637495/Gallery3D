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
		gallyFlow.setFadingEdgeLength(0);
		gallyFlow.setSpacing(-100);
		gallyFlow.setAdapter(imageAdapter);
		gallyFlow.setSelection(4);

	}

	private void initData() {
		imageIds = new int[] { R.drawable.a, R.drawable.b,
				R.drawable.c, R.drawable.d, R.drawable.e,
				R.drawable.f , R.drawable.g,
				R.drawable.h};
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
