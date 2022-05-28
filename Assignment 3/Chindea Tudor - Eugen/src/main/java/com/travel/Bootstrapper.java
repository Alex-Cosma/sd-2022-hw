package com.travel;
import com.amadeus.Amadeus;
import com.amadeus.Params;

import com.amadeus.Response;
import com.amadeus.exceptions.ResponseException;
import com.amadeus.referenceData.Locations;
import com.amadeus.resources.Location;
import com.travel.city.CityRepository;
import com.travel.city.model.City;
import com.travel.flight.FlightRepository;
import com.travel.flight.model.Flight;
import com.travel.hotel.HotelRepository;
import com.travel.hotel.model.Hotel;
import com.travel.security.AuthService;
import com.travel.security.dto.SignupRequest;
import com.travel.user.RoleRepository;
import com.travel.user.UserRepository;
import com.travel.user.model.ERole;
import com.travel.user.model.Role;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class Bootstrapper implements ApplicationListener<ApplicationReadyEvent> {
    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final AuthService authService;
    private final FlightRepository flightRepository;
    private final CityRepository cityRepository;
    private final HotelRepository hotelRepository;
    @Value("false")
    private Boolean bootstrap;

    @SneakyThrows
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (bootstrap) {
            cityRepository.deleteAll();
            hotelRepository.deleteAll();
            int i;
            ArrayList<String> names = new ArrayList<>(Arrays.asList("AMS","BCN","BER","BEL","PAR"));
            for (String name : names) {
                cityRepository.save(
                        City.builder()
                                .name(name)
                                .build()
                );
            }
            Random random = new Random();
            for (String name : names) {
                for(String name2: names){
                    if(!name.equals(name2)){
                        flightRepository.save(Flight.builder().price(random.nextInt(30)+20).seats(random.nextInt(200)+50).departureCity(cityRepository.findByName(name).get()).arrivalCity(cityRepository.findByName(name2).get()).build());
                    }

                }
            }

            Amadeus amadeus = Amadeus
                    .builder("3OX1ojGQzjulYwv0c3oRLKxN0SeR2eAM", "UFEUiAJxILCdNST6")
                    .build();
            Response response = amadeus.get("/v1/reference-data/locations/hotels/by-city?cityCode=AMS&radius=5&radiusUnit=KM&hotelSource=ALL");
            response.getResult();
            JSONObject jsonObject = new JSONObject(response.getBody());
            JSONArray hotelsJson = (JSONArray) jsonObject.get("data");

            for(i = 0; i< hotelsJson.length() && i < 10;i++){
                JSONObject hotel = hotelsJson.getJSONObject(i);
                String nameHotel = "";
                if(hotel.has("name")){
                    nameHotel = hotel.getString("name");
                    hotelRepository.save(Hotel.builder().name(nameHotel).places(random.nextInt(300)+50).price(random.nextInt(40)+50).city(cityRepository.findByName("AMS").get()).build());
                }
            }
            response = amadeus.get("/v1/reference-data/locations/hotels/by-city?cityCode=BCN&radius=5&radiusUnit=KM&hotelSource=ALL");
            response.getResult();
            jsonObject = new JSONObject(response.getBody());
            hotelsJson = (JSONArray) jsonObject.get("data");
            for(i = 0; i< hotelsJson.length() && i < 10;i++){
                JSONObject hotel = hotelsJson.getJSONObject(i);
                String nameHotel = "";
                if(hotel.has("name")){
                    nameHotel = hotel.getString("name");
                    hotelRepository.save(Hotel.builder().name(nameHotel).places(random.nextInt(300)+50).price(random.nextInt(40)+50).city(cityRepository.findByName("BCN").get()).build());
                }
            }
            response = amadeus.get("/v1/reference-data/locations/hotels/by-city?cityCode=BER&radius=5&radiusUnit=KM&hotelSource=ALL");
            response.getResult();
            jsonObject = new JSONObject(response.getBody());
            hotelsJson = (JSONArray) jsonObject.get("data");
            for(i = 0; i< hotelsJson.length() && i < 10;i++){
                JSONObject hotel = hotelsJson.getJSONObject(i);
                String nameHotel = "";
                if(hotel.has("name")){
                    nameHotel = hotel.getString("name");
                    hotelRepository.save(Hotel.builder().name(nameHotel).places(random.nextInt(300)+50).price(random.nextInt(40)+50).city(cityRepository.findByName("BER").get()).build());
                }
            }
            response = amadeus.get("/v1/reference-data/locations/hotels/by-city?cityCode=BEL&radius=5&radiusUnit=KM&hotelSource=ALL");
            response.getResult();
            jsonObject = new JSONObject(response.getBody());
            hotelsJson = (JSONArray) jsonObject.get("data");
            for(i = 0; i< hotelsJson.length() && i < 10;i++){
                JSONObject hotel = hotelsJson.getJSONObject(i);
                String nameHotel = "";
                if(hotel.has("name")){
                    nameHotel = hotel.getString("name");
                    hotelRepository.save(Hotel.builder().name(nameHotel).places(random.nextInt(300)+50).price(random.nextInt(40)+50).city(cityRepository.findByName("BEL").get()).build());
                }
            }
            response = amadeus.get("/v1/reference-data/locations/hotels/by-city?cityCode=PAR&radius=5&radiusUnit=KM&hotelSource=ALL");
            response.getResult();
            jsonObject = new JSONObject(response.getBody());
            hotelsJson = (JSONArray) jsonObject.get("data");
            for(i = 0; i< hotelsJson.length() && i < 10;i++){
                JSONObject hotel = hotelsJson.getJSONObject(i);
                String nameHotel = "";
                if(hotel.has("name")){
                    nameHotel = hotel.getString("name");
                    hotelRepository.save(Hotel.builder().name(nameHotel).places(random.nextInt(300)+50).price(random.nextInt(40)+50).city(cityRepository.findByName("PAR").get()).build());
                }
            }



            userRepository.deleteAll();
            roleRepository.deleteAll();
            for (ERole value : ERole.values()) {
                roleRepository.save(
                        Role.builder()
                                .name(value)
                                .build()
                );
            }

            authService.register(SignupRequest.builder()
                    .email("alex@email.com")
                    .username("alex")
                    .password("WooHoo1!")
                    .roles(Set.of("ADMIN"))
                    .build());
            authService.register(SignupRequest.builder()
                    .email("alex1@email.com")
                    .username("alex1")
                    .password("WooHoo1!")
                    .roles(Set.of("CLIENT"))
                    .build());
        }
    }
}
