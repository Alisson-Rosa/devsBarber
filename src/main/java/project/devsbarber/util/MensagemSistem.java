package project.devsbarber.util;

import java.util.ResourceBundle;

public class MensagemSistem {

    private static final ResourceBundle MSG_SISTEM = ResourceBundle.getBundle("static.menssagens.msg");

    public static String getMessage(String mensagem){
        if(MSG_SISTEM.containsKey(mensagem)){
            return MSG_SISTEM.getString(mensagem);
        }

        return "";
    }
}
