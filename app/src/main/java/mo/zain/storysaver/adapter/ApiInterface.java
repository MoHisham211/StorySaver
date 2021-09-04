package mo.zain.storysaver.adapter;

import java.util.List;

import mo.zain.storysaver.model.TopModel;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("top.json")
    Call<List<TopModel>> getTop();
}
