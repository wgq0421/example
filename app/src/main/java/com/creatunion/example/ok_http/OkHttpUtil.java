package com.creatunion.example.ok_http;

import okhttp3.OkHttpClient;

/**
 * Created by wuguoqiang on 16/9/19.
 */
public class OkHttpUtil {

    private static OkHttpUtil mInstance;
    private OkHttpClient mOkHttpClient;

    public OkHttpUtil(OkHttpClient okHttpClient) {
        if (okHttpClient == null) {
            mOkHttpClient = new OkHttpClient();
        } else {
            mOkHttpClient = okHttpClient;
        }
    }

    public static OkHttpUtil initClient(OkHttpClient okHttpClient) {
        if (mInstance == null) {
            synchronized (OkHttpUtil.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpUtil(okHttpClient);
                }
            }
        }
        return mInstance;
    }

    public static OkHttpUtil getInstance() {
        return initClient(null);
    }

//    public String doGet(String url, Map<String, String> mapToNameValue) throws IOException {
//        StringBuilder sb = new StringBuilder();
//        if (mapToNameValue != null && mapToNameValue.size() > 0) {
//            sb.append(url).append("?");
//            for (String str : mapToNameValue.keySet()) {
//                final String value = mapToNameValue.get(str);
//                sb.append(str + "=" + value + "&");
//            }
//            url = sb.toString().substring(0, sb.toString().length() - 1);
//        }
//        //创建一个Request
//        final Request request = new Request.Builder()
//                .url(url)
//                .build();
//        //new call
//        Call call = mOkHttpClient.newCall(request);
//        //请求加入调度
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String result = response.body().string();
//            }
//        });
//    }
}