package com.jerry.up.lala.boot.vo;


import lombok.Data;

/**
 * <p>Description: http 请求发送
 *
 * @author FMJ
 * @date 2018/12/21 15:26
 */
@Data
public class HttpSendVO {

    private String httpUrl;

    private Integer count;

}