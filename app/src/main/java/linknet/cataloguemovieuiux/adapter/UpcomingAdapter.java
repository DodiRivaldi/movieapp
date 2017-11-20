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
import linknet.cataloguemovieuiux.model.Upcoming;

/**
 * Created by Dodi Rivaldi on 10/11/2017.
 */

public class UpcomingAdapter extends RecyclerView.Adapter<UpcomingAdapter.UpcomingHolder> {

    private Context context;
    private List<Upcoming> upcomingList;

    @Override
    public UpcomingAdapter.UpcomingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_upcoming,parent,false);
        return new UpcomingAdapter.UpcomingHolder(view);
    }

    @Override
    public void onBindViewHolder(final UpcomingAdapter.UpcomingHolder holder, int position) {
        final Upcoming upcoming = upcomingList.get(position);
        holder.tvTitle.setText(upcoming.getTitle());
        holder.tvDate.setText(upcoming.getReleaseDate());
        holder.tvOverview.setText(upcoming.getReview());
        Picasso.with(context).load("http://image.tmdb.org/t/p/w185"+upcoming.getImage()).into(holder.imgBanner);

        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context,playing.getTitle(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("title", upcoming.getTitle());
                intent.putExtra("overview", upcoming.getReview());
                intent.putExtra("release", upcoming.getReleaseDate());
                intent.putExtra("image", upcoming.getImage());
                context.startActivity(intent);
            }
        });

        holder.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,context.getString(R.string.share_text));
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, upcoming.getTitle()+upcoming.getReview());
                context.startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });
    }

    @Override
    public int getItemCount() {
        return upcomingList.size();
    }

    public class UpcomingHolder extends RecyclerView.ViewHolder {
        private ImageView imgBanner;
        private TextView tvTitle, tvOverview, tvDate;
        private Button btnShare, btnDetail;

        public UpcomingHolder(View view) {
            super(view);

            imgBanner = (ImageView) view.findViewById(R.id.img_upcoming);
            tvTitle = (TextView) view.findViewById(R.id.tv_title_upcoming);
            tvOverview = (TextView) view.findViewById(R.id.tv_overview_upcoming);
            tvDate = (TextView) view.findViewById(R.id.tv_date_upcoming);
            btnShare = (Button) view.findViewById(R.id.btn_share_upcoming);
            btnDetail = (Button) view.findViewById(R.id.btn_detail_upcoming);
        }
    }

    public UpcomingAdapter(Context context, List<Upcoming>upcomingList){
        this.context = context;
        this.upcomingList = upcomingList;
    }

}
