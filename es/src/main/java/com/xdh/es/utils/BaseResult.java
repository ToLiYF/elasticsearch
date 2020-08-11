package com.xdh.es.utils;

import com.xdh.es.model.BookModel;

public class BaseResult<T> {
    public  static BaseResult ok() {
        return new BaseResult("成功");
    }

    public BaseResult(String s) {
    }

    public static BaseResult error() {
        return new BaseResult("失败");

    }

    public static BaseResult error(String s) {
        return new BaseResult(s);
    }

    public static BaseResult ok(Page<BookModel> page) {
        return new BaseResult("成功");
    }

    public static BaseResult ok(BookModel book) {
        return new BaseResult("成功");
    }
}
