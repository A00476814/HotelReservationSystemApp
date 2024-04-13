package com.mcda5550.hotelreservationsystem.api;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit = null;
    private static final String BASE_URL = "http://hotel-reservation-env.eba-ihpnu2st.us-east-1.elasticbeanstalk.com/";

    public static HotelApiService getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(HotelApiService.class);
    }
}