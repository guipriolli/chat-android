package br.com.gui.chat.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import br.com.gui.chat.R;
import br.com.gui.chat.model.Mensagem;
import butterknife.BindView;
import butterknife.ButterKnife;

//Adapter da ListView de mensagens
public class MensagemAdapter extends BaseAdapter {

    private int idCliente;
    private List<Mensagem> mensagens;
    private Activity activity;

    @BindView(R.id.texto)
    TextView texto;
    @BindView(R.id.avatar_mensagem)
    ImageView avatar;

    @Inject
    Picasso picasso;

    public MensagemAdapter(int idCliente, List<Mensagem> mensagens, Activity activity) {
        this.mensagens = mensagens;
        this.activity = activity;
        this.idCliente = idCliente;
    }

    @Override
    public int getCount() {
        Log.i("getCount", String.valueOf(mensagens.size()));
        return mensagens.size();
    }

    @Override
    public Mensagem getItem(int i) {
        return mensagens.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View linha = activity.getLayoutInflater().inflate(R.layout.mensagem, viewGroup, false);

        ButterKnife.bind(this, linha);

        Mensagem mensagem = getItem(i);

        picasso.with(activity)
                .load("https://api.adorable.io/avatars/285/" + mensagem.getId() + ".png")
                .into(avatar);

        if(mensagem.getId() != idCliente) {
            linha.setBackgroundColor(Color.CYAN);
        }

        texto.setText(mensagem.getTexto());

        return linha;
    }
}
