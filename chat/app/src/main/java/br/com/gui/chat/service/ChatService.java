package br.com.gui.chat.service;

import br.com.gui.chat.model.Mensagem;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

//Interface para auxiliar no acesso aos serviços externos por meio do Retrofit
public interface ChatService {

    @POST("polling")
    Call<Void> enviar(@Body Mensagem mensagem);

    @GET("polling")
    Call<Mensagem> listener();

}
