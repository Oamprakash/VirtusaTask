package com.oam.virtusatask.virtusatask;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.oam.virtusatask.virtusatask.adapter.AlbumsAdapter;
import com.oam.virtusatask.virtusatask.model.Album;
import com.oam.virtusatask.virtusatask.webapi.ApiClient;
import com.oam.virtusatask.virtusatask.webapi.ApiService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.Realm;
import io.realm.RealmResults;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private static final String TAG = MainActivity.class.getSimpleName();
    private ApiService apiService;
    private CompositeDisposable disposable = new CompositeDisposable();
    private AlbumsAdapter mAdapter;
    private List<Album> albumList = new ArrayList<>();
    private Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mRealm = Realm.getDefaultInstance();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        apiService = ApiClient.getClient(getApplicationContext())
                .create(ApiService.class);

        mAdapter = new AlbumsAdapter(this, albumList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

// Fetching album list
        if(Utils.iisNetworkAvailable(this)) {

            disposable.add(
                    apiService.getAlbumList()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeWith(new DisposableSingleObserver<List<Album>>() {
                                @Override
                                public void onSuccess(List<Album> albums) {


                                    albumList.clear();
                                    albumList.addAll(albums);
                                    mAdapter.notifyDataSetChanged();

                                    insertIntoRealm(albums);


                                }

                                @Override
                                public void onError(Throwable e) {
                                    Log.e(TAG, "onError: " + e.getMessage());

                                }
                            })
            );
        }else{

            loadFromRealm();
        }
    }

    public void insertIntoRealm(List<Album> albums){
        final RealmResults<Album> realmAlbums = mRealm.where(Album.class).findAll();

                                realmAlbums.deleteAllFromRealm();

                                mRealm.beginTransaction();
                                mRealm.insert(albums);
                                mRealm.commitTransaction();
                                realmAlbums.size();

    }

public void loadFromRealm(){
    final RealmResults<Album> realmAlbums = mRealm.where(Album.class).findAll();
    AlbumsAdapter mAdapter = new AlbumsAdapter(this, realmAlbums);
    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
    recyclerView.setLayoutManager(mLayoutManager);
    recyclerView.setItemAnimator(new DefaultItemAnimator());
    recyclerView.setAdapter(mAdapter);
    realmAlbums.size();

}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }
}

