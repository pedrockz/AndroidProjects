package br.com.caelum.fragment;

import java.util.Arrays;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import br.com.caelum.cadastro.ProvasActivity;
import br.com.caelum.cadastro.R;
import br.com.caelum.cadastro.modelo.Prova;

public class ListaProvasFragment extends Fragment{

	private ListView listViewProvas;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		
		View layoutProvas = inflater.inflate(R.layout.provas_lista, container, false);
		
		this.listViewProvas = (ListView)layoutProvas.findViewById(R.id.lista_provas);
		
		//inflando o layout
		Prova prova1 = new Prova("20/03/2012", "Matematica");
		prova1.setTopicos(Arrays.asList("Algebra linear", "Ingegral", "Diferencial"));
		
		Prova prova2 = new Prova("25/03/2012", "Portugues");
		prova2.setTopicos(Arrays.asList("Complemento Nominal", "Oracoes Subordinadas"));
		
		List<Prova> provas = Arrays.asList(prova1, prova2);
		
		this.listViewProvas.setAdapter(new ArrayAdapter<Prova>( 
				getActivity(), android.R.layout.simple_list_item_1, provas));
		
		//Imprimir a prova selecionada no evento de clique no ListView
		this.listViewProvas.setOnItemClickListener(
				new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> adapter, View view,
							int posicao, long id) {
						Prova selecionada = (Prova) adapter.getItemAtPosition(posicao);
						ProvasActivity calendarioProvas = (ProvasActivity) getActivity();
						calendarioProvas.selecionaProva(selecionada);
					}
				});
		
		return layoutProvas;
	}
}
