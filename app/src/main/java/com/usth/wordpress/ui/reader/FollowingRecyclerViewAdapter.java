package com.usth.wordpress.ui.reader;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.usth.wordpress.R;

import com.usth.wordpress.ui.WebviewPostContent;
import com.usth.wordpress.ui.reader.entity.PostData;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FollowingRecyclerViewAdapter extends RecyclerView.Adapter<FollowingRecyclerViewAdapter.ViewHolder> {

    private final List<PostData> mValues;

    public FollowingRecyclerViewAdapter(List<PostData> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_following_item, parent, false);

        return new ViewHolder(view,parent.getContext());
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        Picasso.get().load(Uri.parse(mValues.get(position).getAuthor().getAvatarURL())).into(holder.circleImageView);
        holder.mTitle.setText(mValues.get(position).getTitle());
        Picasso.get().load(Uri.parse(mValues.get(position).getFeaturedMedia().getURI())).into(holder.mImagePanel);
        holder.mUsername.setText(mValues.get(position).getAuthor().getName());
        holder.contentHtml = mValues.get(position).getContent();
        holder.title = mValues.get(position).getTitle();
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;
        public final TextView mTitle;
        public final TextView mUsername;
        public final ImageView mImagePanel;
        public final CircleImageView circleImageView;
        public PostData mItem;

        private String contentHtml = "";
        private String title = "";

        private Context context;

        public ViewHolder(View view, Context context) {
            super(view);
            mView = view;
            mTitle = (TextView) view.findViewById(R.id.item_title);
            circleImageView = (CircleImageView) view.findViewById(R.id.image_avatar_user_list);
            mUsername = (TextView) view.findViewById(R.id.text_username_user_list);
            mImagePanel = (ImageView) view.findViewById(R.id.item_image_panel);
            this.context = context;
            view.setOnClickListener(this);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTitle.getText() + "'";
        }


        @Override
        public void onClick(View v) {
            System.out.println("lol loo");

            Intent myIntent = new Intent(context , WebviewPostContent.class );
            myIntent.putExtra("content_html", this.contentHtml);
            myIntent.putExtra("title", this.title);//Optional parameters
            context.startActivity(myIntent);
        }
    }
}