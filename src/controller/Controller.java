package controller;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

import model.logic.Feature;
import model.logic.Modelo;
import view.View;

public class Controller {

	/* Instancia del Modelo*/
	private Modelo modelo;
	
	/* Instancia de la Vista*/
	private View view;
	
	static final String DATA_PATH = "./data/Comparendos_DEI_2018_Bogotá_D.C_small.geojson";
	
	/**
	 * Crear la vista y el modelo del proyecto
	 * @param capacidad tamaNo inicial del arreglo
	 */
	public Controller ()
	{
		view = new View();
		modelo = new Modelo();
	}
		
	public void run() 
	{
		loadFeatures();
		
		Scanner lector = new Scanner(System.in);
		boolean fin = false;

		while( !fin ){
			view.printMenu();

			int option = lector.nextInt();
			switch(option){
				case 1:
					view.printMessage("Fecha:");
					String date = lector.next();
					view.printMessage("Clase de vehiculo");
					String vehicle = lector.next();
					view.printMessage("Infracci�n");
					String infraction = lector.next();
					String key = (date + vehicle + infraction).trim();
					view.printMessage("Buscando en Linear Probing...");
					LinkedList<Feature> features = modelo.searchKeyOnLinearProbing(key);
					Iterator<Feature> iterator = features.iterator();
					
					while( iterator.hasNext() )
						view.printFeature(iterator.next());
					
					break;
				case 2:
					view.printMessage("Fecha:");
					String date1 = lector.next();
					view.printMessage("Clase de vehiculo");
					String vehicle1 = lector.next();
					view.printMessage("Infracci�n");
					String infraction1 = lector.next();
					String key1 = (date1 + vehicle1 + infraction1).trim();
					view.printMessage("Buscando en Separate Chainning...");
					LinkedList<Feature> features1 = modelo.searchKeyOnSeparateChainning(key1);
					Iterator<Feature> iterator1 = features1.iterator();
					
					while( iterator1.hasNext() )
						view.printFeature(iterator1.next());
					break;
				case 3: 
					view.printMessage("--------- \n Hasta pronto !! \n---------"); 
					lector.close();
					fin = true;
					break;	

				default: 
					view.printMessage("--------- \n Opcion Invalida !! \n---------");
					break;
			}
		}
		
	}	
	
	private void loadFeatures(){
		view.printMessage("--------- \nCargando datos de comparendos...");
	    modelo = new Modelo();
	    if( modelo.loadDataList(DATA_PATH) ){
		    Feature firstFeature = modelo.getFirstFeature();
		    Feature lastFeature = modelo.getLastFeature();
		    int featuresNumber = modelo.size();
		    view.printGeneralFeaturesInfo(firstFeature, lastFeature, featuresNumber);
	    }
	}
}
