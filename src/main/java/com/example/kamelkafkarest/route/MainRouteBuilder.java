package com.example.kamelkafkarest.route;

import com.example.kamelkafkarest.model.DateDTO;
import com.example.kamelkafkarest.model.Train;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.ProtobufDataFormat;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import javax.sql.DataSource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class MainRouteBuilder extends RouteBuilder {
    @Autowired
    private DataSource dataSource;
    int quantity;
    List<Train> trains = new ArrayList<>();


    @Override
    public void configure() throws Exception {
        //настройка рест
        restConfiguration()
                .component("servlet")
                .dataFormatProperty("prettyPrint", "true")
                .bindingMode(RestBindingMode.json);

        //rest

        rest("/trains")
                .post()
                .type(DateDTO.class)
                .to("direct:start");

        // посылаем в kafka
//        from("direct:start")
//                .process(exchange -> {
//                    DateDTO trainDto = exchange.getIn().getBody(DateDTO.class);
//                    ServletRequest request = exchange.getIn().getBody(HttpServletRequest.class);
//                    ProtoData.RequestData trainMessage = ProtoData.RequestData.newBuilder()
//                            .setTimestamp(String.valueOf(System.currentTimeMillis()))
//                            .setIpAddress(request.getRemoteAddr())
//                            .setDate(trainDto.getDate().toString())
//                            .build();
//                    exchange.getIn().setBody(trainMessage.toByteArray());
//                })
//                .to("kafka:topic2?brocers=localhost:29092")
//                .process(exchange -> {
//                    Thread.sleep(1000);
//                    //Создаем новый exchenge на основе существующего
//                    Exchange newExchange = exchange.copy();            //3 строки лишние, можно сразу
//                    //Устанавливаем тело exchange в список trains
//                    newExchange.getIn().setBody(trains);
//                    //Заменяем исходный exchange новым
//                    exchange.setIn(newExchange.getIn());
//                })
//                .log("${body}");
//
//        //Принимаем из kafka
//        from("kafka:topic2?brocers=localhost:29092")
//                .process(exchange -> {
//                    trains.removeAll(trains);
//                })
//                .unmarshal().protobuf(ProtoData.RequestData.getDefoultInstance())
//                .to("sql:SELECT * FROM trains WHERE dt_start > CAST(:#${body.getDate()} AS TIMESTAMP) ORDER BY dt_start")
//                .split().body().threads(5) //многопоточка
//                    .process(exchange -> {
//                        //Получаем первую строку запроса к БД
//                        Map<String, Object> row = (Map<String, Object>)exchange.getIn().getBody();
//                        //Преобразуем значение поля "train_name" в верхний регистр
//                        String trainName = ((String) row.get("train_name")).toUpperCase();
//                        // Обновляем значение поля "train_name" в строке
//                        row.put("train_name", trainName);
//                        // Записываем обновленную строку в тело сообщения
//                        exchange.getIn().setBody(row);
//                    })
//                .process(exchange -> {
//                    Map<String, Object> row = (Map<String, Object>)exchange.getIn().getBody();
//                    String trainName = ((String) row.get("train_name"));
//                    int id_train = ((int) row.get("id_train"));
//                    Date dt_start = ((Date) row.get("dt_start"));
//                    int id_station_start = ((int) row.get("id_station_start"));
//                    Train train = new Train(id_train, dt_start, id_station_start, trainName);
//                    trains.add(train);
//                });
    }
}
