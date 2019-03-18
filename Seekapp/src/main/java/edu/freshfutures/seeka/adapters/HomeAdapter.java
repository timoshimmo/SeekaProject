package edu.freshfutures.seeka.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import edu.freshfutures.seeka.HomeActivity;
import edu.freshfutures.seeka.Models.HomeModel;
import edu.freshfutures.seeka.R;
import edu.freshfutures.seeka.WebsiteActivity;
import edu.freshfutures.seeka.volley.custom.application.AppController;

/**
 * Created by tokmang on 9/13/2016.
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private ArrayList<HomeModel> mArticles;
    private Context mContext;

    private static String TAG = HomeActivity.class.getSimpleName();

    public HomeAdapter(Context ctx, ArrayList<HomeModel> hm) {
        this.mArticles = hm;
        this.mContext = ctx;
    }


    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.home_bottom_body, parent, false);

        ViewHolder viewholder = new ViewHolder(v);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(final HomeAdapter.ViewHolder holder, int position) {

        final HomeModel hModel = mArticles.get(position);

        if(hModel.getCachedData() == 0) {
            Glide.with(mContext)
                    .load(hModel.getArticleImage())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.imgArticle);
        }
        else {
            Glide.with(mContext)
                    .load(hModel.getArticleImage())
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(holder.imgArticle);
        }

        final int articleId = hModel.getArticleId();
        holder.txtArticleTitle.setText(hModel.getArticleTitle());
        holder.txtArticleTopic.setText(hModel.getArticleTopic());
        holder.txtArticleDetails.setText(hModel.getArticleDetails());

        holder.likeValue.setText(hModel.getLikeValue() + " " + "Likes");

        holder.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                makeJsonObjectSharedRequest(articleId);

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Share data");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Share the benefits of the app to thers");
                mContext.startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });

        holder.btnLikeArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(holder.txtLikeStatus.getCurrentTextColor() == Color.parseColor("#646969")){

                    holder.txtLikeStatus.setText("Liked");
                    holder.txtLikeStatus.setTextColor(Color.parseColor("#0091f0"));
                    holder.imgLikeStatus.setImageResource(R.mipmap.liked_icon);


                }

                else {
                    holder.txtLikeStatus.setText("Like");
                    holder.txtLikeStatus.setTextColor(Color.parseColor("#646969"));
                    holder.imgLikeStatus.setImageResource(R.mipmap.like_article);

                }

            }
        });

        holder.readMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                makeJsonObjectReviewedRequest(articleId);

                Intent intent = new Intent(mContext, WebsiteActivity.class);
                intent.putExtra("ARTICLE_LINK", hModel.getArticleLink());
                mContext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgArticle;
        TextView txtArticleTitle;
        TextView txtArticleTopic;
        TextView txtArticleDetails;
        LinearLayout btnShare;
        LinearLayout btnLikeArticle;
        TextView readMore;

        TextView likeValue;
        ImageView imgLikeStatus;
        TextView txtLikeStatus;


        public ViewHolder(View itemView) {
            super(itemView);

            imgArticle = (ImageView) itemView.findViewById(R.id.imgHomeArticle);
            txtArticleTitle = (TextView) itemView.findViewById(R.id.txtArticleTitle);
            txtArticleTopic = (TextView) itemView.findViewById(R.id.txtHomeTopic);
            txtArticleDetails = (TextView) itemView.findViewById(R.id.txtHomeDetails);
            btnShare = (LinearLayout) itemView.findViewById(R.id.shareBody);
            btnLikeArticle = (LinearLayout) itemView.findViewById(R.id.likeBody);
            readMore = (TextView) itemView.findViewById(R.id.txtReadMore);

            likeValue = (TextView) itemView.findViewById(R.id.txtArticleLikesValue);
            txtLikeStatus = (TextView) itemView.findViewById(R.id.txtLikeStatus);
            imgLikeStatus = (ImageView) itemView.findViewById(R.id.imgLikeArticle);

        }
    }

    private void makeJsonObjectSharedRequest(int artId) {

        String requestURL = "http://ec2-52-74-92-131.ap-southeast-1.compute.amazonaws.com:8080/FF_WS/services/rest/ArticleService/shared/"+artId;

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                requestURL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                String message;

                try {
                    message = response.getString("message");

                    Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(mContext.getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);

    }


    private void makeJsonObjectReviewedRequest(int artId) {

        String requestURL = "http://ec2-52-74-92-131.ap-southeast-1.compute.amazonaws.com:8080/FF_WS/services/rest/ArticleService/reviewed/"+artId;

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                requestURL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                String message;

                try {
                    message = response.getString("message");

                    Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(mContext.getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);

    }
}
