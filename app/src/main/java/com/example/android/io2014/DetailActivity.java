/**
 * Copyright 2014 Google
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.io2014;

import android.animation.Animator;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends ActionBarActivity {
    private View mPhoto;
    private int mLeftDelta;
    private int mTopDelta;
    private float mScaleX;
    private float mScaleY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mPhoto = findViewById(R.id.photo);
        setupPhoto(getIntent().getIntExtra("photo", R.drawable.photo1));

        setupText();
        setTitle("");

        Bundle b = getIntent().getExtras();

        final int startTop     = b.getInt("top");
        final int startLeft    = b.getInt("left");
        final int startWidth   = b.getInt("width");
        final int startHeight  = b.getInt("height");

        //add predraw listener that will be called after all Views are positioned, but before drawing them
        //in order to calculate initial animation values.
        mPhoto.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mPhoto.getViewTreeObserver().removeOnPreDrawListener(this);
                int[] screenLocation = new int[2];
                mPhoto.getLocationOnScreen(screenLocation);

                int left    = screenLocation[0];
                int top     = screenLocation[1];
                int width   = mPhoto.getWidth();
                int height  = mPhoto.getHeight();

                //we have all the coordinates - lets do the calc
                mLeftDelta  = startLeft - left;
                mTopDelta   = startTop - top;
                mScaleX     = (float)startWidth / width;
                mScaleY     = (float)startHeight / height;

                runEnterAnimation();
                return false;
            }
        });

        Toolbar t = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(t);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    private void runEnterAnimation() {
        mPhoto.setPivotX(0);
        mPhoto.setPivotY(0);
        mPhoto.setScaleX(mScaleX);
        mPhoto.setScaleY(mScaleY);
        mPhoto.setTranslationX(mLeftDelta/2);
        mPhoto.setTranslationY(mTopDelta);

        mPhoto.animate().scaleX(1f).scaleY(1f).translationX(0).translationY(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        findViewById(R.id.container).animate().alpha(1f);
    }
    private void setupText() {
        TextView titleView = (TextView) findViewById(R.id.title);
        titleView.setText(getIntent().getStringExtra("title"));

        TextView descriptionView = (TextView) findViewById(R.id.description);
        descriptionView.setText(getIntent().getStringExtra("description"));
    }

    private Bitmap setupPhoto(int resource) {
        Bitmap bitmap = MainActivity.sPhotoCache.get(resource);
        ((ImageView) findViewById(R.id.photo)).setImageBitmap(bitmap);
        return bitmap;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }
}
