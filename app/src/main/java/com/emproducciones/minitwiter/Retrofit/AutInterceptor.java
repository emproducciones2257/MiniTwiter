package com.emproducciones.minitwiter.Retrofit;

import com.emproducciones.minitwiter.Common.Constantes;
import com.emproducciones.minitwiter.Common.SharedPreferencesManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AutInterceptor implements Interceptor {

    String token = SharedPreferencesManager.getSomeStringValues(Constantes.PREF_TOKEN);

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request().newBuilder().addHeader("Authorization","Bearer " + token).build();
        return chain.proceed(request);
    }
}
