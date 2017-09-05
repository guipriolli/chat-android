package br.com.gui.chat.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import br.com.gui.chat.R;
import br.com.gui.chat.adapter.MensagemAdapter;
import br.com.gui.chat.app.ChatApplication;
import br.com.gui.chat.callback.EnviarCallback;
import br.com.gui.chat.callback.ListenerCallback;
import br.com.gui.chat.event.MensagemEvent;
import br.com.gui.chat.component.ChatComponent;
import br.com.gui.chat.model.Mensagem;
import br.com.gui.chat.service.ChatService;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class MainActivity extends AppCompatActivity {

    private int idCliente = new Random().nextInt();
    private List<Mensagem> mensagens;

    //Mapeia as views por meio do ButterKnife
    @BindView(R.id.texto_enviar)
    EditText editText;
    @BindView(R.id.btn_enviar)
    Button button;
    @BindView(R.id.mensagens)
    ListView listViewMensagens;
    @BindView(R.id.avatar_usuario)
    ImageView avatar;

    //Injeção de dependências por meio do Dagger
    @Inject
    ChatService chatService;
    @Inject
    Picasso picasso; //A powerful image downloading and caching library for Android
    @Inject
    EventBus eventBus;
    @Inject
    InputMethodManager inputMethodManager;

    private ChatComponent component;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Injeção de dependências por meio do Dagger
        ChatApplication app = (ChatApplication) getApplication();
        component = app.getComponent();
        component.inject(this);

        //Faz a injeção de dependências das views
        ButterKnife.bind(this);

        //Busca a foto e coloca na ImageView
        picasso.with(this)
                .load("https://api.adorable.io/avatars/285/" + idCliente + ".png")
                .into(avatar);

        //Recuperando as mensagens, caso haja alguma salva no estado da instância
        if (savedInstanceState != null) {
            mensagens = (List<Mensagem>) savedInstanceState.getSerializable("mensagens");
        } else {
            mensagens = new ArrayList<>();
        }

        //Monta o Adapter da ListView de mensagens
        MensagemAdapter adapter = new MensagemAdapter(idCliente, mensagens, this);
        listViewMensagens.setAdapter(adapter);

        Call<Mensagem> call = chatService.listener();
        call.enqueue(new ListenerCallback(eventBus, this));

        eventBus.register(this);
    }

    //Clique do botão enviar setado pelo ButterKnife
    @OnClick(R.id.btn_enviar)
    public void enviaMensagem() {
        chatService.enviar(new Mensagem(idCliente, editText.getText().toString())).enqueue(new EnviarCallback());

        editText.getText().clear();
        inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    //Atualiza as mensagens no app
    @Subscribe
    public void colocaNaLista(MensagemEvent mensagemEvent) {
        mensagens.add(mensagemEvent.mensagem);
        MensagemAdapter adapter = new MensagemAdapter(idCliente, mensagens, this);
        listViewMensagens.setAdapter(adapter);
    }

    //Ouve o servidor para buscar novas mensagens
    @Subscribe
    public void listen() {
        Call<Mensagem> call = chatService.listener();
        call.enqueue(new ListenerCallback(eventBus, this));
    }

    @Override
    protected void onStop() {
        super.onStop();

        eventBus.unregister(this);
    }

    //Salva o estado da instância da aplicação
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //Salva as mensagens
        outState.putSerializable("mensagens", (ArrayList<Mensagem>) mensagens);
    }

}
