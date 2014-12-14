//package com.betalife.sushibuffet.activity;
//
//import java.util.Arrays;
//import java.util.List;
//
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.widget.GridView;
//
//import com.betalife.sushibuffet.AbstractAsyncTask;
//import com.betalife.sushibuffet.adapter.ChangeTableAdapter;
//import com.betalife.sushibuffet.model.Diningtable;
//
//public class ChangeTableActivity extends Activity {
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.fragment_tables);
//	}
//
//	@Override
//	protected void onResume() {
//		super.onResume();
//		GetAllTablesAsyncTask task = new GetAllTablesAsyncTask(this);
//		task.execute();
//	}
//
//	private class GetAllTablesAsyncTask extends AbstractAsyncTask<Void, List<Diningtable>> {
//
//		public GetAllTablesAsyncTask(Activity activity) {
//			super(activity);
//		}
//
//		@Override
//		public void postCallback(final List<Diningtable> result) {
//			ChangeTableAdapter aa = new ChangeTableAdapter(activity, result);
//			GridView tables = (GridView) activity.findViewById(R.id.diningtables);
//			tables.setAdapter(aa);
//		}
//
//		@Override
//		protected List<Diningtable> inBackground(Void... params) {
//			String url = getString(R.string.base_uri) + "/availableTables";
//			HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
//			ResponseEntity<Diningtable[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET,
//					requestEntity, Diningtable[].class);
//			return Arrays.asList(responseEntity.getBody());
//		}
//
//	};
//
// }
