package com.zdlog.smartbutler.ui;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.zdlog.smartbutler.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GuideTwoActivity extends Activity implements View.OnClickListener {

    @Bind(R.id.viewPager)
    ViewPager viewPager;
    @Bind(R.id.positionRG)
    RadioGroup positionRG;
    @Bind(R.id.into)
    Button into;
    //---------------------------------------------------------------
    private int[] imagesid = {R.drawable.demo, R.drawable.demo, R.drawable.demo, R.drawable.demo};
    //---------------------------------------------------------------
    private ArrayList<ImageView> views = new ArrayList<ImageView>();
    private GuideAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_guide_two);
        ButterKnife.bind(this);

        initViewPaager();
        initPosition();

    }
//---------------------------------------------------------------

    /**
     * 用户维护：修改MainActivity为用户定义的class
     */
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(this,LoginActivity.class);
       startActivity(intent);
        finish();
    }
//---------------------------------------------------------------


    /**
     * 初始化viewpager
     */
    private void initViewPaager() {
        for (int i = 0; i < imagesid.length; i++) {
            ImageView iamge = new ImageView(this);
            ViewPager.LayoutParams p = new ViewPager.LayoutParams();
            iamge.setScaleType(ImageView.ScaleType.FIT_XY);
            iamge.setImageResource(imagesid[i]);
            iamge.setLayoutParams(p);
            views.add(iamge);
            if (i == imagesid.length - 1) {
                iamge.setOnClickListener(this);
            }
        }
        adapter = new GuideAdapter(views);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (imagesid.length - 1 == position) {
                    into.setVisibility(View.VISIBLE);
                } else {
                    into.setVisibility(View.GONE);
                }
                toPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 初始化底部
     */
    private void initPosition() {
        positionRG.removeAllViews();
        for (int i = 0; i < imagesid.length; i++) {
            RadioButton rb = (RadioButton) getLayoutInflater().inflate(R.layout.guide_position_button, null);
            rb.setChecked(i == 0);
            RadioGroup.LayoutParams p = new RadioGroup.LayoutParams(20, 20);
            p.setMargins(10, 10, 10, 10);
            rb.setLayoutParams(p);
            positionRG.addView(rb);
        }
    }

    /**
     * 移动底部position圆点
     */
    private void toPosition(int posi) {
        for (int i = 0; i < imagesid.length; i++) {
            ((RadioButton) positionRG.getChildAt(i)).setChecked(false);
        }
        ((RadioButton) positionRG.getChildAt(posi)).setChecked(true);
    }

    /**
     * 跳过，进入
     */
    @OnClick({R.id.next, R.id.into})
    public void onClick() {
        onClick(null);
    }


    class GuideAdapter extends PagerAdapter {
        private ArrayList<ImageView> views;

        public GuideAdapter(ArrayList<ImageView> views) {
            this.views = views;
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView(views.get(arg1));
        }

        @Override
        public int getCount() {
            if (views != null) {
                return views.size();
            }
            return 0;
        }

        @Override
        public Object instantiateItem(View arg0, int arg1) {
            ((ViewPager) arg0).addView(views.get(arg1), 0);
            return views.get(arg1);

        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return (arg0 == arg1);
        }
    }

}
