package com.liujun.createhealthcard.entity;

import lombok.Data;

/**
 * Header class
 *
 * @author LiuJun
 * @date 2020-07-18
 */
@Data
public class Header {
    private String code;
    private String message;
    private String status;
    private String data;
}
