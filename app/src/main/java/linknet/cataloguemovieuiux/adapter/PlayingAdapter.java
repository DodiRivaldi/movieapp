package linknet.cataloguemovieuiux.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import linknet.cataloguemovieuiux.R;
import linknet.cataloguemovieuiux.activity.DetailActivity;
import linknet.cataloguemovieuiux.model.Playing;

/**
 * Created by Dodi Rivaldi on 10/11/2017.
 */

public class PlayingAdapter extends RecyclerView.Adapter<PlayingAdapter.PlayingHolder> {

    private Context context;
    private List<Playing> playingList;

    @Override
    public PlayingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_now_playing,parent,false);
        return new  PlayingHolder(view);
    }

    @Override
    public void onBindViewHolder(final PlayingHolder holder, int position) {
        final Playing playing = playingList.get(position);
        holder.tvTitle.setText(playing.getTitle());
        holder.tvDate.setText(playing.getReleaseDate());
        holder.tvOverview.setText(playing.getReview());
        Picasso.with(context).load("http://image.tmdb.org/t/p/w185"+playing.getImage()).into(holder.imgBanner);

        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context,playing.getTitle(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("title", playing.getTitle());
                intent.putExtra("overview", playing.getReview());
                intent.putExtra("release", playing.getReleaseDate());
                intent.putExtra("image", playing.getImage());
                context.startActivity(intent);
            }
        });

        holder.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,context.getString(R.string.share_text));
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, playing.getTitle()+playing.getReview());
                context.startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });
    }

    @Override
    public int getItemCount() {
        return playingList.size();
    }

    public class PlayingHolder extends RecyclerView.ViewHolder {
        private ImageView imgBanner;
        private TextView tvTitle, tvOverview, tvDate;
        private Button btnShare, btnDetail;

        public PlayingHolder(View view) {
            super(view);

            imgBanner = (ImageView) view.findViewById(R.id.img_nowplaying);
            tvTitle = (TextView) view.findViewById(R.id.tv_title_now_playing);
            tvOverview = (TextView) view.findViewById(R.id.tv_overview_now_playing);
            tvDate = (TextView) view.findViewById(R.id.tv_date_now_playing);
            btnShare = (Button) view.findViewById(R.id.btn_share_nowplaying);
            btnDetail = (Button) view.findViewById(R.id.btn_detail_now_playing);
        }
    }

    public PlayingAdapter(Context context, List<Playing>playingList){
        this.context = context;
        this.playingList = playingList;
    }


}
