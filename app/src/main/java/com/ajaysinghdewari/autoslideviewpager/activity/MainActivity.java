package com.ajaysinghdewari.autoslideviewpager.activity;

import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.ajaysinghdewari.autoslideviewpager.R;
import com.ajaysinghdewari.autoslideviewpager.adapter.BannerPagerAdapter;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private ViewPager mBannerViewPager;
    private BannerPagerAdapter mBannerPagerAdapter;
    private LinearLayout mBannerDotsLayout;
    private TypedArray mBannerArray;
    private int numberOfBannerImage;
    private View[] mBannerDotViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         /*===================Inetelizing Banner Variables===============*/
        mBannerArray = getResources().obtainTypedArray(R.array.banner_img_array);
        numberOfBannerImage=mBannerArray.length();
        mBannerDotViews = new View[numberOfBannerImage]; // create an empty array;

        /*===================Banner Pager Configuration=================*/
        mBannerViewPager = (ViewPager) findViewById(R.id.bannerViewPager);
        mBannerDotsLayout= (LinearLayout) findViewById(R.id.bannerDotsLayout);
        mBannerPagerAdapter=new BannerPagerAdapter(MainActivity.this, mBannerArray);
        mBannerViewPager.setAdapter(mBannerPagerAdapter);


        /*===========================START Banner Configuration Code ======================================*/

        for (int i = 0; i < numberOfBannerImage; i++) {
            // create a new textview
            final View bannerDotView = new View(this);
/*Creating the dynamic dots for banner*/
            LinearLayout.LayoutParams dotLayoutParm=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            dotLayoutParm.height = getResources().getDimensionPixelSize(R.dimen.standard_10);
            dotLayoutParm.width = getResources().getDimensionPixelSize(R.dimen.standard_10);
            dotLayoutParm.setMargins(getResources().getDimensionPixelSize(R.dimen.standard_8),0,0,0);
            bannerDotView.setLayoutParams(dotLayoutParm);
            bannerDotView.setBackground(getResources().getDrawable(R.drawable.shape_deselected_dot));

            // add the textview to the linearlayout
            mBannerDotsLayout.addView(bannerDotView);


            // save a reference to the textview for later
            mBannerDotViews[i] = bannerDotView;
        }

        AutoSwipeBanner();
        mBannerViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeDotBG(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
/*===========================END Banner Configuration Code ======================================*/

    }

    public void AutoSwipeBanner(){
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                int currentPage=mBannerViewPager.getCurrentItem();
                if (currentPage == numberOfBannerImage-1) {
                    currentPage = -1;
                }
                mBannerViewPager.setCurrentItem(currentPage+1, true);
            }
        };

        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(Update);
            }
        }, 500, 3000);

    }

    private void changeDotBG(int position){

        for(int i = 0; i < numberOfBannerImage; i++){
            if(position==i){
                mBannerDotViews[i].setBackground(getResources().getDrawable(R.drawable.shape_selected_dot));
            }else{
                mBannerDotViews[i].setBackground(getResources().getDrawable(R.drawable.shape_deselected_dot));
            }

        }
    }
}
