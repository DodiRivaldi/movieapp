package linknet.cataloguemovieuiux.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import linknet.cataloguemovieuiux.R;

public class DetailActivity extends AppCompatActivity {
    private TextView tvTitle, tvRelease, tvOverview;
    private ImageView imgPoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvTitle = (TextView) findViewById(R.id.tv_detail_title);
        tvOverview = (TextView) findViewById(R.id.tv_detail_overview);
        tvRelease = (TextView) findViewById(R.id.tv_release_detail);
        imgPoster = (ImageView) findViewById(R.id.img_detail);

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        final String title = (String) bundle.get("title");
        final String overview = (String) bundle.get("overview");
        final String release = (String) bundle.get("release");
        final String image = (String) bundle.get("image");

        tvTitle.setText(title);
        tvOverview.setText(overview);
        tvRelease.setText(release);
        Picasso.with(this).load("http://image.tmdb.org/t/p/w185"+image).into(imgPoster);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            // finish the activity
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
