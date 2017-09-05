package br.com.gui.chat.component;

import br.com.gui.chat.activity.MainActivity;
import br.com.gui.chat.adapter.MensagemAdapter;
import br.com.gui.chat.module.ChatModule;
import dagger.Component;

@Component(modules = ChatModule.class)
public interface ChatComponent {

    void inject(MainActivity activity);

    void inject(MensagemAdapter adapter);
}
