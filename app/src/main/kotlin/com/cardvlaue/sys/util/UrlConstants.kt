package com.cardvlaue.sys.util

object UrlConstants {

    private val BASE_URL = "http://www.cvbaoli.com/penguin/"

    private val NOT_TITLE = "?isApp=1"

    /**
     * 融资攻略
     */
    val STRATEGY = BASE_URL + "new/m/more/question"

    /**
     * 公司简介
     */
    val INTRODUCTION = BASE_URL + "new/m/more/aboutUs" + NOT_TITLE

    /**
     * 资质荣誉
     */
    val HONOUR = BASE_URL + "new/m/more/honor" + NOT_TITLE

    /**
     * 联系方式
     */
    val CONTACT = BASE_URL + "new/m/more/contactUs" + NOT_TITLE

}