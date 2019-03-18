package edu.freshfutures.seeka.adapters;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import edu.freshfutures.seeka.CourseInfoActivity;
import edu.freshfutures.seeka.R;

/**
 * Created by tokmang on 5/8/2016.
 */
public class YoutubeRecyclerAdapter extends RecyclerView.Adapter<YoutubeRecyclerAdapter.VideoInfoHolder> {

    Context ctx;
    String[] videoIds;
    String[] vidViews;
    String[] vidTitle;
    String[] vidPublished;
    String[] vidDuration;

    public YoutubeRecyclerAdapter(Context context, String[] vid, String[] vTitle,
                                  String[] vPublished, String[] vViews, String[] duration) {
        this.ctx = context;
        this.videoIds = vid;
        this.vidTitle = vTitle;
        this.vidViews = vViews;
        this.vidPublished = vPublished;
        this.vidDuration = duration;
    }

    @Override
    public VideoInfoHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.youtube_view_layout_structure, viewGroup, false);
        return new VideoInfoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final VideoInfoHolder viewHolder, final int i) {

        final YouTubeThumbnailLoader.OnThumbnailLoadedListener onThumbnailLoadedListener = new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {

            @Override
            public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {

                youTubeThumbnailView.setVisibility(View.VISIBLE);
                viewHolder.txtDuration.setVisibility(View.VISIBLE);

            }

            @Override
            public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {

            }
        };

        viewHolder.youTubeThumbnailView.initialize(ctx.getResources().getString(R.string.youtube_api_key), new YouTubeThumbnailView.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {

                youTubeThumbnailLoader.setVideo(videoIds[i]);
                youTubeThumbnailLoader.setOnThumbnailLoadedListener(onThumbnailLoadedListener);
            }

            @Override
            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
                viewHolder.noVid.setVisibility(View.VISIBLE);
            }
        });

        viewHolder.txtTitle.setText(vidTitle[i]);
        viewHolder.txtLastUpload.setText(vidPublished[i]);
        viewHolder.txtViews.setText(vidViews[i]);
        viewHolder.txtDuration.setText(vidDuration[i]);
    }

    @Override
    public int getItemCount() {
        return this.vidTitle.length;
    }

    public class VideoInfoHolder extends RecyclerView.ViewHolder {

        protected YouTubeThumbnailView youTubeThumbnailView;
        protected TextView txtDuration;
        protected TextView txtLastUpload;
        protected TextView txtViews;
        protected TextView txtTitle;
        protected ImageView noVid;


        public VideoInfoHolder(View itemView) {
            super(itemView);

            youTubeThumbnailView = (YouTubeThumbnailView) itemView.findViewById(R.id.imgYoutubeThumb);
            txtDuration = (TextView) itemView.findViewById(R.id.txtYouTubeDuration);
            txtLastUpload = (TextView) itemView.findViewById(R.id.txtYouTubeLastUploaded);
            txtViews = (TextView) itemView.findViewById(R.id.txtYouTubeViews);
            txtTitle = (TextView) itemView.findViewById(R.id.txtYouTubeTitle);
            noVid = (ImageView) itemView.findViewById(R.id.imgNoVideo);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = YouTubeStandalonePlayer.createVideoIntent((CourseInfoActivity)ctx,
                            ctx.getResources().getString(R.string.youtube_api_key), videoIds[getLayoutPosition()],
                            0, true, false);

                    ctx.startActivity(intent);
                }
            });

        }
    }

}
