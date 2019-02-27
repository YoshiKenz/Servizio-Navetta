package model.geoUtil;

import java.util.Comparator;

import persistence.persistentModel.Fermata;

public class FermataComparator implements Comparator<Fermata>{
	
	private double lat,lng;
	
	public FermataComparator(double lat,double lng) {
		this.lat = lat;
		this.lng = lng;
	}

	@Override
	public int compare(Fermata o1, Fermata o2) {
		//negativo il primo è minore
		double n1,n2;
		
		n1 = GeoUtil.distance(lat, lng, o1.getLatitudine(), o1.getLongitudine());//4
		n2 = GeoUtil.distance(lat, lng, o2.getLatitudine(), o2.getLongitudine());//5
		//voglio che il più piccolo sia 4
		
		return (int) Math.signum(n1 - n2);
	}
	

}
