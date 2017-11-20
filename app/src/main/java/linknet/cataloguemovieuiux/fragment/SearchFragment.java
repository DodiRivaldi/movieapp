package linknet.cataloguemovieuiux.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import linknet.cataloguemovieuiux.R;
import linknet.cataloguemovieuiux.adapter.SearchAdapter;
import linknet.cataloguemovieuiux.model.Search;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {
    private RecyclerView recyclerView;
    private SearchAdapter adapter;
    private List<Search> searchList;
    private EditText edtSearch;
    private Button btnSearch;
    private static final String TAG = SearchFragment.class.getSimpleName();

    private ProgressDialog progressDialog;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_search);
        edtSearch = (EditText) view.findViewById(R.id.edt_search);
        btnSearch = (Button) view.findViewById(R.id.btn_search);
        progressDialog = new ProgressDialog(getContext());

        searchList = new ArrayList<>();
        adapter = new SearchAdapter(getContext(), searchList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edtSearch.getText().toString())) {
                    edtSearch.setError("Field tidak boleh kosong");
                    return;
                } else
                    adapter.refreshItem(searchList);
                loadData();
            }
        });
        return view;
    }

    private void loadData() {
        // Creating volley request obj
        progressDialog.setMessage("Memuat data");
        progressDialog.show();
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,
                "https://api.themoviedb.org/3/search/movie?api_key=0b8306d7d4e46c13da26789e18038dad&language=en-US&query=" + edtSearch.getText().toString(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            progressDialog.dismiss();
                            JSONArray jsonArray = response.getJSONArray("results");
                            Log.d(TAG, jsonArray.toString());

                            for (int i = 0; i <= jsonArray.length(); i++) {
                                JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                                final Search search = new Search();
                                search.setTitle(jsonObject.getString("title"));
                                search.setReview(jsonObject.getString("overview"));
                                search.setReleaseDate(jsonObject.getString("release_date"));
                                search.setImage(jsonObject.getString("poster_path"));

                                searchList.add(search);
                                adapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getContext(), "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

        req.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {
                progressDialog.dismiss();
            }
        });
        RequestQueue rQueue = Volley.newRequestQueue(getContext());
        rQueue.add(req);

    }


}
