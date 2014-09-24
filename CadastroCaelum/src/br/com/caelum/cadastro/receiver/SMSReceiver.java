package br.com.caelum.cadastro.receiver;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;
import br.com.caelum.cadastro.R;
import br.com.caelum.cadastro.dao.AlunoDAO;

public class SMSReceiver extends BroadcastReceiver {

	//Lidar com a chegada de uma mensagem interna no Android:
	@Override
	public void onReceive(Context context, Intent intent){
		//Toast.makeText(context, "Chegou um SMS! =D", Toast.LENGTH_LONG).show();
		
		// *** Descobrir quem mandou o SMS ***
		
		//Buscar as mensagens SMS recebidas:
		Bundle bundle = intent.getExtras();
		Object[] messages = (Object[]) bundle.get("pdus");
		
		//Pegar apenas a mensagem mais recente - indice zero do array
		byte[] message = (byte[]) messages[0]; //a mensagem vem na forma de um array de bytes
		//Criacao de um objeto do tipo SmsMessage com os dados do array de bytes, para que seja
		//mais facil acessar o telefone que originou a chamada:
		SmsMessage sms = SmsMessage.createFromPdu(message);
		String telefone = sms.getDisplayOriginatingAddress();
		//Invocar o metodo getDisplayOriginatingAddress do objeto SmsMessage para descobrir o
		//telefone que originou a mensagem
		AlunoDAO dao = new AlunoDAO(context);
		if(dao.isAluno(telefone)){
			Toast.makeText(context, "SMS recebido do aluno de telefone: " + telefone, Toast.LENGTH_LONG).show();
			//tocar um .mp3 quando chegar a mensagem
			MediaPlayer mp = MediaPlayer.create(context, R.raw.msg);
			mp.start();
		}
		dao.close();
	}
	
}
