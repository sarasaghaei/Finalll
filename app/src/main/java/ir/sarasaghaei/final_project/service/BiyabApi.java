package ir.sarasaghaei.final_project.service;

import java.util.List;
import ir.sarasaghaei.final_project.entity.Biyab;
import retrofit2.Call;
import retrofit2.http.GET;

public interface BiyabApi {
    String BASE_CLASS = "https://run.mocky.io/";

    @GET("v3/7040bf2a-978e-4856-97ab-a44bef2c0867")
    Call<List<Biyab>> selectAll();
}
