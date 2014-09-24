package br.com.caelum.cadastro.util;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import br.com.caelum.fragment.MapaFragment;

import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.model.LatLng;

public class AtualizadorDeLocalizacao implements 
GooglePlayServicesClient.ConnectionCallbacks, LocationListener {

	private MapaFragment mapa;
	private LocationClient client;
	
	//Construtor - Usa o LocationClient para buscar a posicao atual do device
	public AtualizadorDeLocalizacao(Context context, MapaFragment mapa){
		this.mapa = mapa;
		this.client = new LocationClient(context, this, null);
		this.client.connect();
	}
	
	//Registrar esta classe para receber atualizacoes
	@Override
	public void onConnected(Bundle dataBundle){
		LocationRequest request = LocationRequest.create();
		request.setInterval(2000); //2 em 2 segundos - tempo minimo
		request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		request.setSmallestDisplacement(50); //50 em 50 metros - distancia minima
		
		this.client.requestLocationUpdates(request, (com.google.android.gms.location.LocationListener) this);
	}
	
	@Override
	public void onDisconnected() { }
	
	@Override
	public void onLocationChanged(Location location) {
		double latitude = location.getLatitude();
		double longitude = location.getLongitude();
		
		LatLng local = new LatLng(latitude, longitude);
		
		this.mapa.centralizaNo(local);
	}
	
	
	//Metodo para desregretizar o LocationClient:
	public void cancela() {
		this.client.removeLocationUpdates((com.google.android.gms.location.LocationListener) this);
		this.client.disconnect();
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

}
