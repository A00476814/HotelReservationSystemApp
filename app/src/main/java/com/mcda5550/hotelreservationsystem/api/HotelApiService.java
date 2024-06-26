package com.mcda5550.hotelreservationsystem.api;
import com.mcda5550.hotelreservationsystem.model.Booking;
import com.mcda5550.hotelreservationsystem.model.HotelListData;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface HotelApiService {
    @GET("mcda5550/hotels/getAvailableHotels")
    Call<List<HotelListData>> getAvailableHotels(
            @Query("checkIn") String checkIn,
            @Query("checkOut") String checkOut,
            @Query("numberOfRoomsRequired") int numberOfRoomsRequired
    );

    @POST("mcda5550/hotels/createBooking")
    Call<Booking> createBooking(@Body Booking bookingData);
}