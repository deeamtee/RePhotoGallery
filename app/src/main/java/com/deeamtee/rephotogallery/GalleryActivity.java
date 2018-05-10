package com.deeamtee.rephotogallery;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.deeamtee.rainbowgallery.Models.FlickrAPI;
import com.deeamtee.rainbowgallery.Models.FlickrResultModel;
import com.deeamtee.rainbowgallery.Models.Photo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class GalleryActivity extends AppCompatActivity {

    private static String TAG = "GalleryActivity";
    private final String API_KEY = "08d40bde6d31142e79ca826915480141";
    public String searshResult = "Girl";
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    private static ArrayList<String> imageUrls = new ArrayList<>();


    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        ButterKnife.bind(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new ImageAdapter(GalleryActivity.this, imageUrls);

        recyclerView.setAdapter(adapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.flickr.com")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FlickrAPI flickrAPI = retrofit.create(FlickrAPI.class);

        Observable<FlickrResultModel> model = flickrAPI.getPhotos(
                API_KEY,
                searshResult,
                "interestingness-asc",
                "1",
                "json",
                "url_m"
        );
        model.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( response -> {
                    imageUrls.clear();
                    for (Photo photo : response.getPhotos().getPhoto()) {
                        String url = photo.getUrlM();
                        imageUrls.add(0, url);
                    }
                    adapter.notifyDataSetChanged();
                });
    }



}

}
