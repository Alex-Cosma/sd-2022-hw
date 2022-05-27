package com.lab4.demo.stock;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.lab4.demo.price.StockPriceRepository;
import com.lab4.demo.price.model.StockPrice;
import com.lab4.demo.stock.model.Stock;
import com.lab4.demo.stock.model.dto.StockDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.json.JSONObject;
import org.json.JSONException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockRepository stockRepository;
    private final StockPriceRepository stockPriceRepository;
    private final StockMapper stockMapper;
    public void loadPricesForStock(String stockSymbol){
        Stock stock = stockRepository.findBySymbol(stockSymbol);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://alpha-vantage.p.rapidapi.com/query?interval=60min&function=TIME_SERIES_INTRADAY&symbol="
                        + stockSymbol
                        +"&datatype=json&output_size=compact"))
                .header("X-RapidAPI-Host", "alpha-vantage.p.rapidapi.com")
                .header("X-RapidAPI-Key", "6b69b80f10msh87e3a1efc04ea00p179b56jsnb36d5e27c257")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        assert response != null;


        try {
            String jsonString = response.body();
            JSONObject object = new JSONObject(response.body().toString());
            JSONObject timeSeries = object.getJSONObject("Time Series (60min)");
            for(int i=timeSeries.length()-1; i>0; i--){
                JSONObject time = timeSeries.getJSONObject(timeSeries.names().get(i).toString());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime dateTime = LocalDateTime.parse(timeSeries.names().get(i).toString(), formatter);
                StockPrice stockPrice = new StockPrice().builder()
                        .id(stock.getId()*100+(long) i)
                        .openPrice(Double.parseDouble(time.get("1. open").toString()))
                        .highPrice(Double.parseDouble(time.get("2. high").toString()))
                        .lowPrice(Double.parseDouble(time.get("3. low").toString()))
                        .closePrice(Double.parseDouble(time.get("4. close").toString()))
                        .volume(Long.parseLong(time.get("5. volume").toString()))
                        .dateTime(dateTime)
                        .stock(stock)
                        .build();
                stockPriceRepository.save(stockPrice);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        stockRepository.save(stock);

        for(int i=0; i<stock.getStockPrices().size()-1; i++){
            for(int j=i+1; j<stock.getStockPrices().size(); j++){
                if(stock.getStockPrices().get(j).getDateTime().isAfter(stock.getStockPrices().get(i).getDateTime())){
                    StockPrice temp = stock.getStockPrices().get(i);
                    stock.getStockPrices().set(i, stock.getStockPrices().get(j));
                    stock.getStockPrices().set(j, temp);
                }
            }
        }
        stockRepository.save(stock);
    }

    public void loadPricesForAllStocks(){
        List<Stock> stocks = stockRepository.findAll();
        stockPriceRepository.deleteAll();
        for(int i=0;i<stocks.size();i++){
            loadPricesForStock(stocks.get(i).getSymbol());
        }

    }

    public List<StockDTO> findAll(){
        loadPricesForAllStocks();
        return stockRepository.findAll(PageRequest.of(0,10) ).stream().map(stockMapper::toDto).collect(toList());
    }

    public Stock findById(Long id){
        return stockRepository.findById(id).orElseThrow(() -> new RuntimeException("Stock not found"));
    }

    public StockDTO create(StockDTO stockDTO){
        return stockMapper.toDto(stockRepository.save(stockMapper.fromDto(stockDTO)));
    }

    public void delete(Long id){
        stockRepository.deleteById(id);
    }

    public StockDTO update(Long id, StockDTO stockDTO){
        return stockMapper.toDto(stockRepository.save(stockMapper.fromDto(stockDTO)));
    }

    public List<StockDTO> findAllByTitleLikeOrSymbolLike(String title, String symbol){
        return stockRepository.findAllByNameLikeOrSymbolLike(title, symbol).stream().map(stockMapper::toDto).collect(toList());
    }
}
