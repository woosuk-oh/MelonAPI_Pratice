package com.tacademy.woosuk_melonapi;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // 메인 액티비티.xml을 컨텐츠뷰에 셋 해준당


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); // 위에 툴바 생성과 동시에 연결 (툴바 할당1)
        setSupportActionBar(toolbar); //툴바 할당2.

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager); // 뷰페이저 선언과 xml과 셋팅
        if(viewPager != null){ //선언해준 viewPager에 setAdapter 해준게 없다면
            setupMenuViewPager(viewPager);  //밑에 생성한 메소드에 viewPager를 인자값으로 보내어 호출
        }


        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabs); // 탭(선택용) 할당
        tabLayout.setupWithViewPager(viewPager); //뷰페이저에 셋팅


    }
        private void setupMenuViewPager(ViewPager viewPager){
            MelonPagerAdapter melonAdapter = new MelonPagerAdapter(getSupportFragmentManager());
            melonAdapter.appendFragment(MelonFragment_gayo.newInstance(1),"실시간 가요");
            melonAdapter.appendFragment(MelonFragment_janre.newInstance(2),"실시간 장르");
            viewPager.setAdapter(melonAdapter);
        }






    // PaegerAdapter

    private static class MelonPagerAdapter extends FragmentPagerAdapter{
        private final ArrayList<Fragment> Realtime_Fragment = new ArrayList<>();

        private final ArrayList<String> tabTitles = new ArrayList<>();


        public MelonPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void appendFragment(Fragment fragment, String title){
            Realtime_Fragment.add(fragment);

            tabTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return Realtime_Fragment.get(position);
        }

        @Override
        public int getCount() {
            return Realtime_Fragment.size();
        }

        @Override
        public CharSequence getPageTitle(int position){
            return tabTitles.get(position);
        }

    }


}

