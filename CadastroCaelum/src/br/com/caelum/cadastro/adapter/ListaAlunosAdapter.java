package br.com.caelum.cadastro.adapter;

import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.caelum.cadastro.R;
import br.com.caelum.cadastro.modelo.Aluno;

public class ListaAlunosAdapter extends BaseAdapter {
	private final List<Aluno> alunos;
	private final Activity activity;
	
	public ListaAlunosAdapter(Activity activity, List<Aluno> alunos){
		this.activity = activity;
		this.alunos = alunos;
	}

	@Override
	public long getItemId(int posicao) {
		return alunos.get(posicao).getId();
	}
	
	@Override
	public Object getItem(int posicao) {
		return alunos.get(posicao);
	}
	
	@Override
	public int getCount() {
		return alunos.size();
	}

	//Faz com que esse Adapter explique que o ListView deve usar o layout item.xml
	@Override
	public View getView(int posicao, View convertView, ViewGroup parent) {
		// LayoutInflater - criar uma View a partir do xml de layout item.xml
		View view = activity.getLayoutInflater().inflate(R.layout.item, null);
		
		Aluno aluno = alunos.get(posicao);
		
		//mudar a cor de fundo da view no caso da posicao da linha ser um numero par:
		if(posicao % 2 == 0){
			view.setBackgroundColor(activity.getResources().getColor(R.color.linha_par));
		}
		
		//Para procurar algum elemento visual dentro da view, invoca-se findViewById a partir da View:
		TextView nome = (TextView) view.findViewById(R.id.nome);
		nome.setText(aluno.getNome());
		
		//Buscar o ImageView e colocar a foto do aluno atraves do metodo setImageBitmap:
		Bitmap bm;
		//Obter o Bitmap a partir da String armazenada no atributo caminhoDaFoto da classe Aluno
		if(aluno.getCaminhoFoto() != null){
			bm = BitmapFactory.decodeFile(aluno.getCaminhoFoto());
		}else{
			bm = BitmapFactory.decodeResource(activity.getResources(), R.drawable.ic_no_image);
		}
		
		bm = Bitmap.createScaledBitmap(bm, 100, 100, true);
		
		ImageView foto = (ImageView) view.findViewById(R.id.foto);
		foto.setImageBitmap(bm);
		
		//Telefone:
		TextView telefone = (TextView) view.findViewById(R.id.telefone);
		if(telefone != null)
			telefone.setText(aluno.getTelefone());
		
		//Site
		TextView site = (TextView) view.findViewById(R.id.site);
		if(site != null)
			site.setText(aluno.getSite());
		
		return view;
	}
}
