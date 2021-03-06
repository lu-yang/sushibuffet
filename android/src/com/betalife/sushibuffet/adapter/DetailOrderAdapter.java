package com.betalife.sushibuffet.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.betalife.sushibuffet.activity.R;
import com.betalife.sushibuffet.model.Order;
import com.betalife.sushibuffet.util.DodoroContext;
import com.betalife.sushibuffet.util.ImageViewUtil;

public class DetailOrderAdapter extends BaseExpandableListAdapter {
	protected LayoutInflater layoutInflater;
	protected Activity activity;
	private Map<Date, List<Order>> map;
	private List<Date> groups;
	private SimpleDateFormat sdf;

	public DetailOrderAdapter(Activity activity) {
		this.activity = activity;
		this.layoutInflater = LayoutInflater.from(activity);
		sdf = new SimpleDateFormat(activity.getString(R.string.order_history_format));
	}

	@Override
	public int getGroupCount() {
		return groups.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		Date date = groups.get(groupPosition);
		List<Order> list = map.get(date);
		return list.size();
	}

	@Override
	public Date getGroup(int groupPosition) {
		Date date = groups.get(groupPosition);
		return date;
	}

	@Override
	public Order getChild(int groupPosition, int childPosition) {
		Date date = groups.get(groupPosition);
		List<Order> list = map.get(date);
		Order order = list.get(childPosition);
		return order;
	}

	@Override
	public long getGroupId(int groupPosition) {
		Date date = groups.get(groupPosition);
		return date.hashCode();
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		Date date = groups.get(groupPosition);
		List<Order> list = map.get(date);
		Order order = list.get(childPosition);
		return order.getId();
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = this.layoutInflater.inflate(R.layout.adapter_detail_order_group, parent, false);
		}
		Date date = getGroup(groupPosition);
		TextView time = (TextView) convertView.findViewById(R.id.time);

		String format = sdf.format(date);
		// CharSequence format =
		// DateFormat.format(activity.getString(R.string.order_history_format),
		// date);
		time.setText(format);

		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
			ViewGroup parent) {
		if (convertView == null) {
			convertView = this.layoutInflater.inflate(R.layout.adapter_order, parent, false);
		}

		Order result = getChild(groupPosition, childPosition);
		String imageUrl = DodoroContext.getProductThumbUrl(result.getProduct().getThumb());
		ImageViewUtil.setImage(imageUrl, (ImageView) convertView.findViewById(R.id.thumb));

		TextView name = (TextView) convertView.findViewById(R.id.name);
		name.setText(result.getProduct().getProductName());

		TextView count = (TextView) convertView.findViewById(R.id.count);
		int productCount = result.getCount();
		count.setText(activity.getString(R.string.lbl_count) + productCount);

		TextView price = (TextView) convertView.findViewById(R.id.price);
		int productPrice = result.getProduct().getProductPrice();
		price.setText(activity.getString(R.string.lbl_price) + DodoroContext.getDisplayPrice(productPrice)
				+ activity.getString(R.string.lbl_eur));

		TextView totalPrice = (TextView) convertView.findViewById(R.id.totalPrice);
		int subTotal = productCount * productPrice;
		totalPrice.setText(activity.getString(R.string.lbl_subtotal)
				+ DodoroContext.getDisplayPrice(subTotal) + activity.getString(R.string.lbl_eur));

		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}

	public void setRawList(List<Order> list) {
		map = new HashMap<Date, List<Order>>();
		for (Order order : list) {
			Date key = order.getCreated();
			List<Order> children = null;
			if (map.containsKey(key)) {
				children = map.get(key);
			} else {
				children = new ArrayList<Order>();
				map.put(key, children);
			}
			children.add(order);
		}

		Set<Date> keySet = map.keySet();
		groups = new ArrayList<Date>(keySet);
		Collections.sort(groups);
		Collections.reverse(groups);
	}

}
