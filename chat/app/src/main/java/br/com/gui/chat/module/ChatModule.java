package br.com.gui.chat.module;


import android.app.Application;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import br.com.gui.chat.service.ChatService;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

//Classe que faz toda a injeção de dependência por meio do Dagger
@Module
public class ChatModule {

    private final Application app;

    public ChatModule(Application app) {
        this.app = app;
    }

    @Provides
    public ChatService getChatService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.15.20:8080/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        ChatService chatService = retrofit.create(ChatService.class);

        return chatService;
    }

    @Provides
    public Picasso getPicasso() {
        return new Picasso.Builder(app).build();
    }

    @Provides
    public EventBus getEventBus() {
        return EventBus.builder().build();
    }

    @Provides
    public InputMethodManager inputMethodManager() {
        InputMethodManager inputMethodManager = (InputMethodManager) app.getSystemService(Context.INPUT_METHOD_SERVICE);
        return inputMethodManager;
    }
}
