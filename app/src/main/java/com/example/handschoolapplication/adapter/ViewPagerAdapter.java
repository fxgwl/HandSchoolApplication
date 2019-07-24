package com.example.handschoolapplication.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * 
 * @author vinYang
 *
 */
public class ViewPagerAdapter extends PagerAdapter {

	private List<ImageView> viewLists;

	public ViewPagerAdapter(List<ImageView> viewLists) {
		this.viewLists = viewLists;
	}

	@Override
	public int getCount() {
		return viewLists.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	/**
	 * 适配器给container容器添加视图
	 */
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		position = position % viewLists.size();
		container.addView(viewLists.get(position));
		return viewLists.get(position);
	}

	/**
	 * 适配器移除container容器中的视图
	 */
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		position = position % viewLists.size();
		container.removeView(viewLists.get(position));
	}

}
