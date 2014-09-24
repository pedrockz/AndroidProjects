package br.com.caelum.cadastro;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import br.com.caelum.cadastro.modelo.Prova;
import br.com.caelum.fragment.DetalhesProvaFragment;
import br.com.caelum.fragment.ListaProvasFragment;

public class ProvasActivity extends FragmentActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.provas);
		
		//Vinculando o Fragment da listagem ao FrameLayout:
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		
		if(isTablet()){
			transaction
				.replace(R.id.provas_lista, new ListaProvasFragment())
			    .replace(R.id.provas_view, new DetalhesProvaFragment());
		} else {
			transaction.replace(R.id.provas_view, new ListaProvasFragment());
		}
		transaction.commit();
	}
	
	public void selecionaProva(Prova prova){
		Bundle argumentos = new Bundle();
		argumentos.putSerializable("prova", prova);
		
		DetalhesProvaFragment detalhesProva = new DetalhesProvaFragment();
		detalhesProva.setArguments(argumentos);
		
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		
		transaction.replace(R.id.provas_view, detalhesProva);
		
		if(!isTablet())
			transaction.addToBackStack(null);
		
		transaction.commit();
	}
	
	public boolean isTablet(){
		return getResources().getBoolean(R.bool.isTablet);
	}
	
}
