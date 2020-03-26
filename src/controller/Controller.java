package controller;

import java.util.Scanner;

import model.logic.Feature;
import model.logic.Modelo;
import view.View;

public class Controller {

	/* Instancia del Modelo*/
	private Modelo modelo;
	
	/* Instancia de la Vista*/
	private View view;
	
	static final String DATA_PATH = "./data/comparendos_dei_2018_Bogotá_D.C_small.geojson";
	
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
					break;
							
				case 2: 
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
