package com.codepath.android.booksearch.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.ShareActionProvider;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.core.view.ActionProvider;
import androidx.core.view.MenuItemCompat;

import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.codepath.android.booksearch.R;
import com.codepath.android.booksearch.models.Book;

import org.parceler.Parcels;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class BookDetailActivity extends AppCompatActivity {
    private ImageView ivBookCover;
    private TextView tvTitle;
    private TextView tvAuthor;
    private ShareActionProvider shareActionProvider;
    private Intent shareIntent;

    private TextView tvPublisher;

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
        tvPublisher = (TextView) findViewById(R.id.tvPublisher);

        // Extract book object from intent extras
        Book book = Parcels.unwrap(getIntent().getParcelableExtra("book"));

        Glide.with(this)
                .load(book.getCoverUrl())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                        prepareShareIntent(((BitmapDrawable) resource).getBitmap());
//
//                        attachShareIntentAction();
                        return false;
                    }
                })
                .into(ivBookCover);

        tvTitle.setText(book.getTitle());
        tvAuthor.setText(book.getAuthor());
        tvPublisher.setText(book.getPublish_date());


        // Checkpoint #5
        // Reuse the Toolbar previously used in the detailed activity by referring to this guide
        // Follow using a Toolbar guide to set the Toolbar as the ActionBar.
        setSupportActionBar(toolbar);
        // Change activity title to reflect the book title by referring to the Configuring The ActionBar guide.
        getSupportActionBar().setTitle(book.getTitle());

        // (Bonus) Get additional book information like publisher and publish_year from the Books API and display in details view.
    }

    public Uri getBitmapFromDrawable(Bitmap bmp){

        // Store image to default external storage directory
        Uri bmpUri = null;
        try {

            // Use methods on Context to access package-specific directories on external storage.

            // This way, you don't need to request external read/write permission.

            // See https://youtu.be/5xVh-7ywKpE?t=25m25s

            File file =  new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();



            // wrap File object into a content provider. NOTE: authority here should match authority in manifest declaration

            bmpUri = FileProvider.getUriForFile(BookDetailActivity.this, "com.codepath.fileprovider", file);  // use this version for API >= 24

            // **Note:** For API < 24, you may use bmpUri = Uri.fromFile(file);

        } catch (IOException e) {

            e.printStackTrace();

        }

        return bmpUri;

    }

//    private void attachShareIntentAction() {
//        if (shareActionProvider != null && shareIntent != null)
//
//            shareActionProvider.setShareIntent(shareIntent);
//    }
//
//    private void prepareShareIntent(Bitmap drawableImage) {
//        Uri bmpUri = getBitmapFromDrawable(drawableImage);// see previous remote images section and notes for API > 23
//
//        // Construct share intent as described above based on bitmap
//
//        shareIntent = new Intent();
//
//        shareIntent.setAction(Intent.ACTION_SEND);
//
//        shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
//
//        shareIntent.setType("image/*");
//    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_book_detail, menu);
        // Checkpoint #6
        // Add Share Intent
        MenuItem item = menu.findItem(R.id.menu_item_share);
        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

//        attachShareIntentAction();
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
