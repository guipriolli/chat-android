package br.com.gui.chat.callback;


import android.content.Context;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import br.com.gui.chat.event.MensagemEvent;
import br.com.gui.chat.model.Mensagem;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListenerCallback implements Callback<Mensagem> {

    private Context context;
    private EventBus eventBus;

    public ListenerCallback(EventBus eventBus, Context context) {
        this.eventBus = eventBus;
        this.context = context;
    }

    @Override
    public void onResponse(Call<Mensagem> call, Response<Mensagem> response) {
        //Se retornou com sucesso
        if(response.isSuccessful()) {
            Mensagem mensagem = response.body();

            eventBus.post(new MensagemEvent(mensagem));
        }
    }

    @Override
    public void onFailure(Call<Mensagem> call, Throwable t) {
    }
}
