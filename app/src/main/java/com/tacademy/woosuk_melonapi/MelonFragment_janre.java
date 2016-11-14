package com.tacademy.woosuk_melonapi;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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
import java.util.HashMap;

/**
 * Created by Tacademy on 2016-11-08.
 */


//장르차트
public class MelonFragment_janre extends Fragment{
    public MainActivity owner;
    public static int increment;




    ArrayList<Song> mAdapter;
    MelonRecyclerViewAdapter melonRecyclerViewAdapter;
    Spinner spinner;
    HashMap<String, String> janreMap;
    ArrayAdapter<String> janreNameListAdapter;
    ArrayList<String> janreNameList;

    MelonGenre melonGenre;

    //String genreName = janreMap.get(spinner.getSelectedItem().toString());

    public MelonFragment_janre(){

    }

    public static MelonFragment_janre newInstance(int initValue){
        MelonFragment_janre melonFragmentJanre = new MelonFragment_janre();
        Bundle bundle = new Bundle();
        bundle.putInt("value", initValue);
        melonFragmentJanre.setArguments(bundle);
        return melonFragmentJanre;
    }


    /*@Override
    public void onRefresh() {
        refreshRequest(genreName);
    }

    private void refreshRequest(String genreCode) {
         new AsyncMelonTask().execute(janreMap.get(spinner.getSelectedItem().toString()));
    }
*/
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = (View) inflater.inflate(
                R.layout.realtime_janre, container, false);

        janreNameList = new ArrayList<>();
        janreMap = new HashMap<>();

       RecyclerView rv = (RecyclerView) v.findViewById(R.id.recyclerview_janre);
       mAdapter = new ArrayList<>();

        spinner = (Spinner) v.findViewById(R.id.spinner);

        janreNameListAdapter = new ArrayAdapter<> (getContext(),android.R.layout.simple_spinner_item, janreNameList);
        // 장르이름을 담아둔 리스트를 스피너로 셋팅할 어댑터에 담는다.
        janreNameListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        spinner.setAdapter(janreNameListAdapter); //장르이름들이 담겨있는 어댑터로 스피너를 셋팅한다.
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (mAdapter.size() > 0){
                    mAdapter.clear();
                }

                new AsyncMelonTask().execute(janreMap.get(spinner.getSelectedItem().toString()));
                //스피너에서 선택한 아이템(장르이름=키)의 janreMap(해시맵)을 이용하여 해당 키값(장르이름)의 장르아이디(밸류)를 가져온 뒤,
                // 해당 인자값으로 AsyncMelonTask를 실행. (해당 장르 아이디값으로 노래들을 가져오게끔 한다.)
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        Bundle initBundle = getArguments();
        increment += initBundle.getInt("value");
        owner = (MainActivity) getActivity();


       melonRecyclerViewAdapter = new MelonRecyclerViewAdapter(getContext(),mAdapter);
       rv.setLayoutManager(new LinearLayoutManager(MelonApplication.getMelonContext()));
       rv.setAdapter(melonRecyclerViewAdapter);



       return v;

    }

    @Override
    public void onResume() {
        super.onResume();

        new AsyncGenreTask().execute();





    }







    // Genre 가져오는 백그라운드 스레드
    public class AsyncGenreTask extends AsyncTask<Void, Void, GenreList>{


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected GenreList doInBackground(Void... voids) {
            String urlText = String.format("http://apis.skplanetx.com/melon/genres?version=1");
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

                    melonGenre = gson.fromJson(isr,MelonGenre.class);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                // 동기방식의 okhttp는 여기서 닫아준다
                // ex)  response.close();
            }
            return melonGenre.getMelon();
        }

        @Override
        protected void onPostExecute(GenreList genreList) {
            super.onPostExecute(genreList);

            if(janreMap.size() > 0){
                janreMap.clear();
            }
            if(janreNameList.size() > 0){
                janreNameList.clear();
            }

            for(int i = 0; i < genreList.getGenres().getGenre().size();i++){
                janreMap.put(genreList.getGenres().getGenre().get(i).getGenreName(),genreList.getGenres().getGenre().get(i).getGenreId());
                janreNameList.add(genreList.getGenres().getGenre().get(i).getGenreName());
            }
            janreNameListAdapter.notifyDataSetChanged();
        }
    }







    //song 가져오는 백그라운드 스레드

    class AsyncMelonTask extends AsyncTask<String, Integer, Melon> {
        private ProgressDialog progressDialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }


        @Override
        protected Melon doInBackground(String... params) {
            String genreId = params[0];
            //String page = params[1];

            String urlFormat = "http://apis.skplanetx.com/melon/charts/topgenres/"+
                    "%s?&count=10&page=10&version=1";

            String urlText = String.format(urlFormat, genreId);
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
                    Log.d("result","result:"+data.melon.menuId);
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
                    Log.d("result","result:"+melon.menuId);




                }

                melonRecyclerViewAdapter.notifyDataSetChanged();
                //mAdapter.clear();
            } else {
                Toast.makeText(getContext(),
                        "멜론데이터 없음", Toast.LENGTH_SHORT).show();
            }
        }

    }














    public static class MelonRecyclerViewAdapter
            extends RecyclerView.Adapter<MelonRecyclerViewAdapter.ViewHolder> {

        private Context context;

        ArrayList<Song> songList = new ArrayList<>();


        // 가져오는값 추가

        public MelonRecyclerViewAdapter(Context cotext, ArrayList<Song> songList) {
            this.context = context;
            this.songList = songList;

        // 가져오는값 추가
        }



        public static class ViewHolder extends RecyclerView.ViewHolder{
            public final View mView;
            public final TextView text4;
            public final TextView text5;
            public final TextView text6;

            public ViewHolder(View view){
                super(view);
                mView=view;
                text4 = (TextView) view.findViewById(R.id.textView1);
                text5 = (TextView) view.findViewById(R.id.textView2);
                text6 = (TextView) view.findViewById(R.id.textView3);

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
            holder.text4.setText(String.valueOf(songList.get(position).currentRank));
            holder.text5.setText(songList.get(position).songName);
            holder.text6.setText(songList.get(position).albumName);


            Log.d("result_songName","result_songName:"+songList.get(position).songName);
        }

        @Override
        public int getItemCount() {
            return songList.size();
        }
    }
}
