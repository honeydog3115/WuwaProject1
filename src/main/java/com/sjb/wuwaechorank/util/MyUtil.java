package com.sjb.wuwaechorank.util;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class MyUtil {
    private <T> Object getOrNull(List<T> list, int index, String methodName) {
        return list.size() <= index ? null : list.get(index);
    }
}
