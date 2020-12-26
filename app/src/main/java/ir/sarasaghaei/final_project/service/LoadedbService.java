package ir.sarasaghaei.final_project.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import java.util.List;

import ir.sarasaghaei.final_project.Const;
import ir.sarasaghaei.final_project.entity.Biyab;
import ir.sarasaghaei.final_project.entity.database.BiyabDBHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoadedbService extends IntentService {

    public LoadedbService() {
        super("LoadedbService");
        Log.e(Const.TAG, "LoadedbService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Log.e(Const.TAG, "start onHandleIntent");
        loadedb();

    }


    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }


    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
    private void loadedb(){
        if (new BiyabDBHelper(LoadedbService.this).checkDataBase()) {
            Log.e(Const.TAG, "database is existence");

        } else {
            //------------- make Retrofit
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BiyabApi.BASE_CLASS)
                    .addConverterFactory(GsonConverterFactory.create()).build();
            Log.e(Const.TAG, "make retrofit");
            //------------- make Api
            BiyabApi biyabApi = retrofit.create(BiyabApi.class);
            Log.e(Const.TAG, "make Api");
            //------------- make Request
            Call<List<Biyab>> requst = biyabApi.selectAll();
            Log.e(Const.TAG, "make request");
            //------------- callback
            requst.enqueue(new Callback<List<Biyab>>() {

                @Override
                public void onResponse(Call<List<Biyab>> call, Response<List<Biyab>> response) {
                    Log.e(Const.TAG, "chech exist db");
                    try {

                        Log.e(Const.TAG, "database is NOT existence");
                        for (int i = 0; i < response.body().size(); i++) {
                            Biyab biyab = new Biyab(
                                    response.body().get(i).getId(),
                                    response.body().get(i).getTitle(),
                                    response.body().get(i).getDescription(),
                                    response.body().get(i).getCategory(),
                                    response.body().get(i).getSub_category(),
                                    response.body().get(i).getTell(),
                                    response.body().get(i).getPrice(),
                                    response.body().get(i).getImage1(),
                                    response.body().get(i).getImage2(),
                                    response.body().get(i).getImage3(),
                                    response.body().get(i).getOffer(),
                                    response.body().get(i).getRank());

                            new BiyabDBHelper(LoadedbService.this).insert(biyab);
                        }


                    } catch (Exception ex) {
                        Log.e(Const.TAG, "Fail chech exist db" + ex.getMessage());
                    }
                    Log.e(Const.TAG, "CREAT DATABASE");
                    Intent intent_success = new Intent("DB_Created_successfully");
                    Log.e(Const.TAG, "sendBroadcast :           successful");
                    Const.CONNECT = true;
                    sendBroadcast(intent_success);
                }

                @Override
                public void onFailure(Call<List<Biyab>> call, Throwable t) {
                    Log.e(Const.TAG, "fail" + t.getMessage());
                }
            });

        }

    }

}
