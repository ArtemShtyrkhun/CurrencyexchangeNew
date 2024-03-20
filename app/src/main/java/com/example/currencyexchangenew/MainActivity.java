package com.example.currencyexchangenew;

import static java.nio.charset.StandardCharsets.UTF_8;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.currencyexchangenew.async.RetrieveRatesTask;
import com.example.currencyexchangenew.holder.CurrencyRateHolder;
import com.example.currencyexchangenew.model.CurrencyRateModel;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    TextView currencyOfSell, currencyOfBuy, rateText;
    EditText amountToSell, amountToBuy;
    RecyclerView categoryRecycler;
    CategoryAdapter categoryAdapter;
    Button calculateButton;
    Button getRatesButton;
    ArrayList<String> arrayList;
    Dialog fromDialog;
    Dialog toDialog;
    String convertFromValue, convertToValue, conversionValue;

    final Context context = this;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currencyOfSell = findViewById((R.id.currency_of_sell));
        currencyOfBuy = findViewById((R.id.currency_of_buy));
//         registerForContextMenu(currency_of_sell);
//         registerForContextMenu(currency_of_buy);
        amountToSell = findViewById(R.id.amount_to_sell);
        amountToBuy = findViewById(R.id.amount_to_buy);
        rateText = findViewById(R.id.rate_text);
        calculateButton = (Button) findViewById(R.id.calculate_button);
        getRatesButton = (Button) findViewById(R.id.get_rates_button);
        //Executors.newScheduledThreadPool(1)
        //        .scheduleAtFixedRate(new RetrieveRatesTask(), 0, 5, TimeUnit.MINUTES);
        getRates();
        getRatesButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                getRates();
            }
        });

//
//            //add button listener
//            calculateButton.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View arg0) {
//
//
//                    // custom dialog
//                    final Dialog dialog = new Dialog(context);
//                    dialog.setContentView(R.layout.dialogfon);  // Передайем ссылку на разметку
//                    dialog.setTitle("Заголовок не показывает"); // Установим заголовок
//
//
//                    // set the custom dialog components - text, image and button
//                    TextView text = (TextView) dialog.findViewById(R.id.dialog_text);
//                    text.setText("Currency converted \n You have 100.00 EUR to 110.30 USD. Commision Fee: 0.70 EUR");
//                    Button dialogButton = (Button) dialog.findViewById(R.id.dialog_button);
//                    // if button is clicked, close the custom dialog
//                    dialogButton.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            dialog.dismiss();
//                        }
//                    });
//
//                    dialog.show();  //  показать диалоговое окно
//                }
//            });


        currencyOfSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromDialog = new Dialog(MainActivity.this);
                fromDialog.setContentView(R.layout.from_spinner);
                fromDialog.getWindow().setLayout(650, 800);
                fromDialog.show();

                EditText editText = fromDialog.findViewById(R.id.edit_text);
                ListView listView = fromDialog.findViewById(R.id.list_view);
                List<String> currencies = List.copyOf(CurrencyRateHolder.getCurrencyRateModel().getRates().keySet());
                ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, currencies);
                listView.setAdapter(adapter);

                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        adapter.getFilter().filter(s);

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        currencyOfSell.setText(adapter.getItem(position));
                        fromDialog.dismiss();
                        convertFromValue = adapter.getItem(position);
                    }
                });
            }
        });
        currencyOfBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toDialog = new Dialog(MainActivity.this);
                toDialog.setContentView(R.layout.to_spinner);
                toDialog.getWindow().setLayout(650, 800);
                toDialog.show();

                EditText editText = toDialog.findViewById(R.id.edit_text);
                ListView listView = toDialog.findViewById(R.id.list_view);
                List<String> currencies = List.copyOf(CurrencyRateHolder.getCurrencyRateModel().getRates().keySet());

                ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, currencies);
                listView.setAdapter(adapter);

                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        adapter.getFilter().filter(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        currencyOfBuy.setText(adapter.getItem(position));
                        toDialog.dismiss();
                        convertToValue = adapter.getItem(position);
                    }
                });
            }
        });
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    BigDecimal amountToSell = BigDecimal.valueOf(Double.valueOf(MainActivity.this.amountToSell.getText().toString()));
                    BigDecimal rate = CurrencyCalculator.getRate(currencyOfSell.getText().toString(),
                            currencyOfBuy.getText().toString());
                    BigDecimal result = CurrencyCalculator.convert(rate, amountToSell);
                    rateText.setText(rate.toPlainString());
                    amountToBuy.setText(result.toPlainString());
                }
                catch (Exception e){

                }
            }
        });

    }

//    public CurrencyRateModel getConversionRates() {
//        String url = "https://developers.paysera.com/tasks/api/currency-exchange-rates";
//
//        try {
//            String response = getRates(url);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        return new CurrencyRateModel();
//        RequestQueue queue = Volley.newRequestQueue(this);
//        CurrencyRateModel[] responseModel = new CurrencyRateModel[1];
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//
//            @Override
//            public void onResponse(String response) {
//                try {
//                    responseModel[0] = OBJECT_MAPPER.readValue(response.getBytes(), CurrencyRateModel.class);
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//
//            }
//        });
//        queue.add(stringRequest);
//        return responseModel[0];
//    }

    private String getConversionRate(String convertFrom, String convertTo, Double amountToConvert) {
//        double conversionRateValue = round(((Double) jsonObject.get(convertFrom + "_" + convertTo)), 2);
//        conversionValue = "" + round((conversionRateValue * amountToConvert), 2);
//        currencyOfBuy.setText(conversionValue);
        return "0.32";
    }

    private double round(double value, int places){
        if (places<0) throw new IllegalArgumentException("Scale should be more than 0");
        BigDecimal bd = BigDecimal.valueOf(value);bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private List<Category> getCategoryList(Map<String, BigDecimal> rates) {
        List<Category> categoryList = new ArrayList<>();
        int i = 1;
        for (Map.Entry<String, BigDecimal> rateEntry : rates.entrySet()) {
            categoryList.add(new Category(i, rateEntry.getKey()));
            i++;
        }
        return categoryList;
    }

    private void setCategoryRecycler(List<Category> categoryList) {
        RecyclerView.LayoutManager LayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);  //делаем вывод горизонтальным

        categoryRecycler = findViewById(R.id.categoryRecycler);  //устанавливаем ссылку на нужный объект из дизайна
        categoryRecycler.setLayoutManager(LayoutManager);     // указываем настройки
        categoryAdapter = new CategoryAdapter(this, categoryList);
        categoryRecycler.setAdapter(categoryAdapter);     //устанавливаем адаптер
    }

    private void getRates() {
        new Thread(() -> {
            final OkHttpClient CLIENT = new OkHttpClient();
            final ObjectMapper OBJECT_MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());
            final String URL = "https://developers.paysera.com/tasks/api/currency-exchange-rates";
            Request request = new Request.Builder()
                    .url(URL)
                    .build();

            try (Response response = CLIENT.newCall(request).execute()) {
                CurrencyRateHolder.setCurrencyRateModel(
                        OBJECT_MAPPER.readValue(response.body().string().getBytes(UTF_8), CurrencyRateModel.class));
                System.out.println(CurrencyRateHolder.getCurrencyRateModel());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            runOnUiThread(new Runnable() {
                public void run() {
                    setCategoryRecycler(getCategoryList(CurrencyRateHolder.getCurrencyRateModel().getRates()));
                }
            });
        }).start();
    }
}
