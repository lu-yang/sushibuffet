package com.betalife.sushibuffet.activity;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.betalife.sushibuffet.AbstractAsyncTask;
import com.betalife.sushibuffet.adapter.CategoryAdapter;
import com.betalife.sushibuffet.adapter.CurrentOrderAdapter;
import com.betalife.sushibuffet.adapter.ProductAdapter;
import com.betalife.sushibuffet.dialog.OrderAlertDialog;
import com.betalife.sushibuffet.model.Category;
import com.betalife.sushibuffet.model.Order;
import com.betalife.sushibuffet.model.Product;
import com.betalife.sushibuffet.util.DodoroContext;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */

/* 菜单，点餐页面 */
public class FragmentOrderpage extends Fragment implements Refreshable {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LinkedList<Order> linkedList = new LinkedList<Order>();
		DodoroContext.getInstance().setCurrentOrdersCache(linkedList);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_orderpage, container, false);

		TextView table_no = (TextView) view.findViewById(R.id.table_no);
		int tableId = DodoroContext.getInstance().getTurnover().getTableId();
		table_no.setText(getActivity().getString(R.string.lbl_table_no) + tableId);

		Button btn_take_orders = (Button) view.findViewById(R.id.btn_take_orders);
		btn_take_orders.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setMessage(R.string.msg_take_orders);
				builder.setPositiveButton(R.string._yes, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						TakeOrdersAsyncTask task = new TakeOrdersAsyncTask(getActivity());
						task.execute();
					}
				});

				builder.setNegativeButton(R.string._no, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});

				builder.create().show();
			}
		});

		return view;

	}

	@Override
	public void onResume() {
		super.onResume();
		// refresh();
	}

	private class TakeOrdersAsyncTask extends AbstractAsyncTask<Void, Boolean> {

		public TakeOrdersAsyncTask(Activity activity) {
			super(activity, true);
		}

		@Override
		protected Boolean inBackground(Void... params) {
			final String url = activity.getString(R.string.base_uri) + "/takeOrders/"
					+ DodoroContext.languageCode(getActivity());

			List<Order> currentOrdersCache = DodoroContext.getInstance().getCurrentOrdersCache();
			HttpEntity<List<Order>> requestEntity = new HttpEntity<List<Order>>(currentOrdersCache,
					requestHeaders);
			ResponseEntity<Boolean> responseEntity = restTemplate.exchange(url, HttpMethod.POST,
					requestEntity, Boolean.class);
			return responseEntity.getBody();
		}

		@Override
		public void postCallback(Boolean result) {
			if (result) {
				DodoroContext.getInstance().getCurrentOrdersCache().clear();

				MainActivity mainActivity = (MainActivity) activity;
				mainActivity.changeTab(mainActivity.getTabIndex() + 1);
			} else {
				Toast.makeText(activity, activity.getString(R.string.err_take_orders), Toast.LENGTH_SHORT)
						.show();
			}
		}

	}

	private class GetCategoriesAsyncTask extends AbstractAsyncTask<Void, List<Category>> {

		public GetCategoriesAsyncTask(Activity activity) {
			super(activity);
		}

		@Override
		public void postCallback(final List<Category> result) {
			Log.i("FragmentOrderpage", "" + result.size());
			CategoryAdapter aa = new CategoryAdapter(activity, result);
			ListView categories = (ListView) activity.findViewById(R.id.categories);
			categories.setAdapter(aa);
			categories.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					Category selected = result.get(position);

					GetProductsByCategoryIdAsyncTask task2 = new GetProductsByCategoryIdAsyncTask(
							getActivity(), selected.getId());
					task2.execute();
				}
			});
		}

		@Override
		protected List<Category> inBackground(Void... params) {
			String url = getString(R.string.base_uri) + "/categories/"
					+ DodoroContext.languageCode(getActivity()) + "/1";
			HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
			ResponseEntity<Category[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET,
					requestEntity, Category[].class);
			List<Category> categories = Arrays.asList(responseEntity.getBody());

			return categories;
		}
	}

	private class GetProductsByCategoryIdAsyncTask extends AbstractAsyncTask<Void, List<Product>> {

		private int categoryId;

		public GetProductsByCategoryIdAsyncTask(Activity activity, int categoryId) {
			super(activity);
			this.categoryId = categoryId;
		}

		@Override
		public void postCallback(final List<Product> result) {
			Log.i("FragmentOrderpage", "" + result.size());
			ProductAdapter aa = new ProductAdapter(activity, result);
			GridView products = (GridView) activity.findViewById(R.id.products);
			products.setAdapter(aa);
			products.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					Product selected = result.get(position);

					OrderAlertDialog dialog = new OrderAlertDialog(activity, selected);
					dialog.show();
				}
			});

		}

		@Override
		protected List<Product> inBackground(Void... params) {
			String url = getString(R.string.base_uri) + "/products/"
					+ DodoroContext.languageCode(getActivity()) + "/" + categoryId;
			HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
			ResponseEntity<Product[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET,
					requestEntity, Product[].class);
			List<Product> products = Arrays.asList(responseEntity.getBody());

			return products;
		}
	}

	@Override
	public void refresh() {

		GetCategoriesAsyncTask task = new GetCategoriesAsyncTask(getActivity());
		task.execute();
		// TODO
		GetProductsByCategoryIdAsyncTask task2 = new GetProductsByCategoryIdAsyncTask(getActivity(), 2);
		task2.execute();

		final List<Order> currentOrdersCache = DodoroContext.getInstance().getCurrentOrdersCache();
		CurrentOrderAdapter oa = new CurrentOrderAdapter(getActivity(), currentOrdersCache);
		ListView currentOrders = (ListView) getActivity().findViewById(R.id.current_orders);
		currentOrders.setAdapter(oa);
		currentOrders.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Order selected = currentOrdersCache.get(position);

				OrderAlertDialog dialog = new OrderAlertDialog(getActivity(), selected.getProduct());
				dialog.show();
			}
		});

	}
}
