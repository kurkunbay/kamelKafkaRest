package com.example.kamelkafkarest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Train {
    private int id_train;
    private Date dt_start;
    private int id_station_start;
    private String train_name;
}
