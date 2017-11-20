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
import linknet.cataloguemovieuiux.model.Search;

/**
 * Created by Dodi Rivaldi on 10/11/2017.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchHolder> {

    private Context context;
    private List<Search> searchList;

    public void refreshItem(List<Search> searchList) {
        this.searchList.clear();
        this.searchList.addAll(searchList);
        notifyDataSetChanged();
    }
    @Override
    public SearchAdapter.SearchHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_search, parent, false);
        return new SearchAdapter.SearchHolder(view);
    }

    @Override
    public void onBindViewHolder(final SearchAdapter.SearchHolder holder, int position) {
        final Search search = searchList.get(position);
        holder.tvTitle.setText(search.getTitle());
        holder.tvDate.setText(search.getReleaseDate());
        holder.tvOverview.setText(search.getReview());
        Picasso.with(context).load("http://image.tmdb.org/t/p/w185" + search.getImage()).into(holder.imgBanner);

        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context,playing.getTitle(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("title", search.getTitle());
                intent.putExtra("overview", search.getReview());
                intent.putExtra("release", search.getReleaseDate());
                intent.putExtra("image", search.getImage());
                context.startActivity(intent);
            }
        });

        holder.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, context.getString(R.string.share_text));
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, search.getTitle() + search.getReview());
                context.startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }

    public class SearchHolder extends RecyclerView.ViewHolder {
        private ImageView imgBanner;
        private TextView tvTitle, tvOverview, tvDate;
        private Button btnShare, btnDetail;

        public SearchHolder(View view) {
            super(view);

            imgBanner = (ImageView) view.findViewById(R.id.img_search);
            tvTitle = (TextView) view.findViewById(R.id.tv_title_search);
            tvOverview = (TextView) view.findViewById(R.id.tv_overview_search);
            tvDate = (TextView) view.findViewById(R.id.tv_date_search);
            btnShare = (Button) view.findViewById(R.id.btn_share_search);
            btnDetail = (Button) view.findViewById(R.id.btn_detail_search);
        }
    }

    public SearchAdapter(Context context, List<Search> searchList
    ) {
        this.context = context;
        this.searchList = searchList;
    }

}
