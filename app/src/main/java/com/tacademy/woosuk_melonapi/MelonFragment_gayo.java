package com.tacademy.woosuk_melonapi;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Tacademy on 2016-11-08.
 */

//실시간 차트

public class MelonFragment_gayo extends Fragment {
    public MainActivity owner;
    public static int increment;

    ArrayList<Song> mAdapter;
    MelonRecyclerViewAdapter melonRecyclerViewAdapter;

    public MelonFragment_gayo() {

    }

    public static MelonFragment_gayo newInstance(int initValue) {
        MelonFragment_gayo melonFragmentGayo = new MelonFragment_gayo();
        Bundle bundle = new Bundle();
        bundle.putInt("value", initValue);
        melonFragmentGayo.setArguments(bundle);
        return melonFragmentGayo;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = (View) inflater.inflate(
                R.layout.realtime_gayo, container, false);

        RecyclerView rv = (RecyclerView) v.findViewById(R.id.recyclerview_gayo);
        mAdapter = new ArrayList<>();
        Bundle initBundle = getArguments();
        increment += initBundle.getInt("value");
        owner = (MainActivity) getActivity();
        melonRecyclerViewAdapter = new MelonRecyclerViewAdapter(getContext(),mAdapter);
        rv.setLayoutManager(new LinearLayoutManager(MelonApplication.getMelonContext()));
        rv.setAdapter(melonRecyclerViewAdapter);


        return rv;
    }


    String urlFormat = "http://apis.skplanetx.com/melon/charts/realtime?" +
            "count=%s&page=%s&version=1";

//최신 차트 가져오는 백그라운드 스레드


    @Override
    public void onResume() {
        super.onResume();

        if(mAdapter.size() > 0){
            mAdapter.clear();
            melonRecyclerViewAdapter.notifyDataSetChanged();
        }
        new AsyncMelonTask().execute(urlFormat);
    }

    class AsyncMelonTask extends AsyncTask<String, Integer, Melon> {
        private ProgressDialog progressDialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }


        @Override
        protected Melon doInBackground(String... params) {

            String urlText = String.format(urlFormat, 10, 1);
            try {
                URL url = new URL(urlText);
                HttpURLConnection conn =
                        (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Accept",
                        "application/json");
                conn.setRequestProperty("appkey",
                        "19c9546d-a916-329a-a7c6-588c70db1ce3");
                int code = conn.getResponseCode();
                if (code >= HttpURLConnection.HTTP_OK && code
                        < HttpURLConnection.HTTP_MULT_CHOICE) {

                    Gson gson = new Gson();
                    InputStream is = conn.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    MelonData data = gson.fromJson(isr, MelonData.class);
                    //Log.d("result","result: "+data.melon.menuId);
                    return data.melon;

                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(Melon melon) {
            super.onPostExecute(melon);
            if (melon != null) {

                    for (Song s : melon.songs.songlist) {
                        mAdapter.add(s);

                }
                melonRecyclerViewAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getContext(),
                        "멜론데이터 없음", Toast.LENGTH_SHORT).show();
            }
        }
    }





    public static class MelonRecyclerViewAdapter
            extends RecyclerView.Adapter<MelonRecyclerViewAdapter.ViewHolder> {

        private Context context;
        // 가져오는값 추가
        ArrayList<Song> songList = new ArrayList<>();


        public MelonRecyclerViewAdapter(Context cotext, ArrayList<Song> songList) {
            this.context = context;
            this.songList = songList;
        // 가져오는값 추가
        }



        public static class ViewHolder extends RecyclerView.ViewHolder{
            public final View mView;
            public final TextView text1;
            public final TextView text2;
            public final TextView text3;

            public ViewHolder(View view){
                super(view);
                mView=view;
                text1 = (TextView) view.findViewById(R.id.textView1);
                text2 = (TextView) view.findViewById(R.id.textView2);
                text3 = (TextView) view.findViewById(R.id.textView3);
            }

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_layout, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.text1.setText(String.valueOf(songList.get(position).currentRank));
            holder.text2.setText(songList.get(position).songName);
            holder.text3.setText(songList.get(position).albumName);
        }

        @Override
        public int getItemCount() {
            return songList.size();
        }
    }
}
