package com.liujun.createhealthcard.entity;

import lombok.Data;

/**
 * HeathCard class
 *
 * @author LiuJun
 * @date 2020-07-05
 */
@Data
public class HeathCard {
    private String he_city = "珠海";
    private String he_workState = "1";
    private String he_healthState = "1";
    private String he_temperature ;
    private String he_clockNote = "本人及同住人一切平安，无同住人从境外回国情况，本人及同住人员2日内无搭乘火车、飞机出行的情况。";
    private String organNum = "19903";

    public HeathCard(String he_temperature) {
        this.he_temperature = he_temperature;
    }
}
