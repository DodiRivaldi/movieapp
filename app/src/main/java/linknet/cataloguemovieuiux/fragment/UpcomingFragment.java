package linknet.cataloguemovieuiux.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import linknet.cataloguemovieuiux.adapter.UpcomingAdapter;
import linknet.cataloguemovieuiux.model.Upcoming;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpcomingFragment extends Fragment {
    private RecyclerView recyclerView;
    private UpcomingAdapter adapter;
    private List<Upcoming> upcomingList;

    private static final String TAG = PlayingFragment.class.getSimpleName();


    public UpcomingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upcoming, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_upcoming);

        upcomingList = new ArrayList<>();
        adapter = new UpcomingAdapter(getContext(), upcomingList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        loadData();
        return view;
    }

    private void loadData() {
        // Creating volley request obj

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,
                "https://api.themoviedb.org/3/movie/upcoming?api_key=0b8306d7d4e46c13da26789e18038dad&language=en-US", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");
//                            Log.d(TAG, jsonArray.toString());

                            for (int i = 0; i <= jsonArray.length(); i++) {
                                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                                Log.d(TAG, jsonObject.toString());

                                //Toast.makeText(getContext(),jsonObject.getString("title"),Toast.LENGTH_SHORT).show();

                                final Upcoming upcoming = new Upcoming();
                                upcoming.setTitle(jsonObject.getString("title"));
                                upcoming.setReview(jsonObject.getString("overview"));
                                upcoming.setReleaseDate(jsonObject.getString("release_date"));
                                upcoming.setImage(jsonObject.getString("poster_path"));

                                upcomingList.add(upcoming);
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
            }
        });
        RequestQueue rQueue = Volley.newRequestQueue(getContext());
        rQueue.add(req);

    }


}
