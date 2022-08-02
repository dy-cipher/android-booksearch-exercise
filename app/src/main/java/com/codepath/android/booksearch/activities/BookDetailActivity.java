package com.codepath.android.booksearch.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ActionProvider;
import androidx.core.view.MenuItemCompat;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.android.booksearch.R;
import com.codepath.android.booksearch.models.Book;

import org.parceler.Parcels;

public class BookDetailActivity extends AppCompatActivity {
    private ImageView ivBookCover;
    private TextView tvTitle;
    private TextView tvAuthor;
    private ActionProvider shareActionProvider;

//    private TextView tvPublisher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main );
//        ActionBar actionBar = getSupportActionBar();
//        String title = actionBar.getTitle().toString();
//        getSupportActionBar().setTitle(title);




        // Fetch views
        ivBookCover = (ImageView) findViewById(R.id.ivBookCover);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvAuthor = (TextView) findViewById(R.id.tvAuthor);
//        tvPublisher = (TextView) findViewById(R.id.tvPublisher);

        // Extract book object from intent extras
        Book book = Parcels.unwrap(getIntent().getParcelableExtra("book"));

        Glide.with(this)
                .load(book.getCoverUrl())
                .into(ivBookCover);

        tvTitle.setText(book.getTitle());
        tvAuthor.setText(book.getAuthor());
//        tvPublisher.setText(book.getPublisherDate());


        // Checkpoint #5
        // Reuse the Toolbar previously used in the detailed activity by referring to this guide
        // Follow using a Toolbar guide to set the Toolbar as the ActionBar.
        setSupportActionBar(toolbar);
        // Change activity title to reflect the book title by referring to the Configuring The ActionBar guide.
        getSupportActionBar().setTitle(book.getTitle());

        // (Bonus) Get additional book information like publisher and publish_year from the Books API and display in details view.
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_book_detail, menu);
        // Checkpoint #6
        // Add Share Intent
        MenuItem item = menu.findItem(R.id.menu_item_share);
        shareActionProvider = MenuItemCompat.getActionProvider(item);
        // see http://guides.codepath.org/android/Sharing-Content-with-Intents#shareactionprovider
        // (Bonus) Share book title and cover image using the same intent.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
