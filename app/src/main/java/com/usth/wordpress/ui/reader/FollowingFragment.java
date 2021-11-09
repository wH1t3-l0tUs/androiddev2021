package com.usth.wordpress.ui.reader;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.usth.wordpress.R;
import com.usth.wordpress.other.MySingleton;
import com.usth.wordpress.ui.reader.entity.Author;
import com.usth.wordpress.ui.reader.entity.FeaturedMedia;
import com.usth.wordpress.ui.reader.entity.PostData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A fragment representing a list of Items.
 */
public class FollowingFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    private RecyclerView.Adapter mFollowingAdapter;
    private RecyclerView mRecyclerView;
    private FrameLayout mProgressBar;

    public FollowingFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static FollowingFragment newInstance(int columnCount) {
        FollowingFragment fragment = new FollowingFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_following_list, container, false);
        mRecyclerView = view.findViewById(R.id.list);
        mProgressBar = view.findViewById(R.id.progress_bar_following);

        Context context = view.getContext();
        // Set the adapter
        if (view instanceof RecyclerView) {
            mRecyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                mRecyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
        }
        updateFollowerList();
        return view;
    }

    private void updateFollowerList(){
        String access_token = getContext().getSharedPreferences(MySingleton.PREF_LOGIN_INFO, Context.MODE_PRIVATE)
                .getString(MySingleton.KEY_ACCESS_TOKEN, "");
        mProgressBar.setVisibility(View.VISIBLE);
        String url = "https://public-api.wordpress.com/rest/v1.1/read/following";
//        mProgressBar.setVisibility(View.VISIBLE);
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response)  {
                        ArrayList<PostData> followersList = null;
                        try {
                            followersList = processRawJson(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        mFollowingAdapter = new FollowingRecyclerViewAdapter(followersList);
                        mRecyclerView.setAdapter(mFollowingAdapter);
                        mProgressBar.setVisibility(View.INVISIBLE);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Nothing
                        Toast.makeText(getContext(),"Loading follower failed",Toast.LENGTH_SHORT).show();
                    }
                }){

                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String,String> params = new HashMap<>();
                        params.put("authorization","Bearer "+access_token);
                        return params;
                    }

                };

        MySingleton.getInstance(getActivity()).addToRequestQueue(jsonArrayRequest);
    }

    private ArrayList<PostData> processRawJson(JSONObject response) throws JSONException {
        JSONObject currentItem;
        ArrayList<PostData> followersList = new ArrayList<>();

        JSONArray listPost = response.getJSONArray("posts");

        String name, userAvatarURL, userURL;
        for (int i = 0; i < listPost.length(); i++) {
            try {
                currentItem = listPost.getJSONObject(i);

                JSONObject authorJson = currentItem.getJSONObject("author");
                String nameAuthor = authorJson.getString("name");
                String urlAvatarAuthor = authorJson.getString("avatar_URL");
                Author author = new Author(nameAuthor, urlAvatarAuthor);


                JSONObject featuredMediaJson = currentItem.getJSONObject("featured_media");
                String urlImage = featuredMediaJson.getString("uri");
                FeaturedMedia featuredMedia = new FeaturedMedia(urlImage);

                String title = currentItem.getString("title");
                String content = currentItem.getString("content");

                followersList.add(new PostData(author,featuredMedia,title, content));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return followersList;
    }
}