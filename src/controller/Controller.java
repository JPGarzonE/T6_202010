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
	
	static final String DATA_PATH = "./data/Comparendos_DEI_2018_Bogotá_D.C.geojson";
	
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
		Scanner lector = new Scanner(System.in);
		boolean fin = false;

		while( !fin ){
			view.printMenu();

			int option = lector.nextInt();
			switch(option){
				case 1:
					loadFeatures();
					break;
				
				case 2:
					view.printMessage("ObjectID a buscar: ");
					int objectID = lector.nextInt();
					view.printMessage("--------- \nBuscando comparendo...");
					Feature feature = modelo.searchByID( objectID );
					view.printMessage("------------------\n RESULTADO:\n");
					view.printFeature(feature);
					break;
					
				case 3:
					view.printMessage("Desde (ID): ");
					int initID = lector.nextInt();
					view.printMessage("Hasta (ID): ");
					int endID = lector.nextInt();
					view.printMessage("--------- \nBuscando comparendo...");
					
					Iterator<Feature> iterator = modelo.searchByIDRange(initID, endID);
					
					while( iterator.hasNext() )
						view.printFeature( iterator.next() );

					break;	
					
				case 4: 
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
		    Feature firstFeature = modelo.getMinValue();	
		    Feature lastFeature = modelo.getMaxValue();
		    int featuresNumber = modelo.size();
		    view.printGeneralFeaturesInfo(firstFeature, lastFeature, featuresNumber);
	    }
	}
}
