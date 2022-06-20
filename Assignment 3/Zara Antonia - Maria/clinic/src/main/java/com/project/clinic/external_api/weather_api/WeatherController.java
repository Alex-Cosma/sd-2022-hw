package com.project.clinic.external_api.weather_api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static com.project.clinic.URLMapping.WEATHER;

@CrossOrigin
@RestController
@RequestMapping(WEATHER)
@RequiredArgsConstructor
public class WeatherController {

    @GetMapping()
    public ResponseEntity<Double> getUvIndex(){
        try {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("https://api.openuv.io/api/v1/uv?lat=46.67&lng=23.592&dt=2022-05-19T10%3A50%3A52.283Z")
                    .get()
                    .addHeader("x-access-token", "a379b4337c7ddac2b2c2366b495d3057")
                    .build();

            Response response = client.newCall(request).execute();
            String body = response.body().string();

            JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
            jsonObject = jsonObject.getAsJsonObject("result");
            Double uv = jsonObject.get("uv").getAsDouble();

            return ResponseEntity.ok(uv);

        }
        catch (Exception e){
            return ResponseEntity.ok(0.0);
        }
    }
}
