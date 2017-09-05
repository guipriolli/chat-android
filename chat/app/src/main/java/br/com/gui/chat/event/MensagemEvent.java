package br.com.gui.chat.event;

import br.com.gui.chat.model.Mensagem;

public class MensagemEvent {

    public final Mensagem mensagem;

    public MensagemEvent(Mensagem mensagem) {
        this.mensagem = mensagem;
    }
}
