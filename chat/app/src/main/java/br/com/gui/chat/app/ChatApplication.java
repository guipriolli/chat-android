package br.com.gui.chat.app;

import android.app.Application;

import br.com.gui.chat.component.ChatComponent;
import br.com.gui.chat.component.DaggerChatComponent;
import br.com.gui.chat.module.ChatModule;

//Classe auxiliar para a injeção de dependência do Dagger em toda a aplicação
public class ChatApplication extends Application {

    private ChatComponent component;

    @Override
    public void onCreate() {
        component = DaggerChatComponent.builder()
                .chatModule(new ChatModule(this))
                .build();
    }

    public ChatComponent getComponent() {
        return component;
    }
}
