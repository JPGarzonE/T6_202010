package view;

import java.util.LinkedList;

import model.logic.Feature;
import model.logic.Modelo;

public class View 
{
	    /**
	     * Metodo constructor
	     */
	    public View()
	    {
	    	
	    }
	    
		public void printMenu()
		{
			System.out.println("1. Requerimiento 1 (Carga de datos)");
			System.out.println("2. Requerimiento 2 (Consultar comparendo por ID)");
			System.out.println("3. Requerimiento 3 (Consultar los comparendos con un ID en un rango)");
			System.out.println("4. Exit");
			System.out.println("Dar el numero de opcion a resolver, luego oprimir tecla Return: (e.g., 1):");
		}

		public void printMessage(String mensaje) {

			System.out.println(mensaje);
		}
		
		public void printFeature(Feature feature){
			
			if(feature == null){
				System.out.println("No hay info de este comparendo o no existe.");
			}
			else{
				System.out.println("\nCOMPARENDO:");
				System.out.println("\n\tOBJECTID: " + feature.getObjectId());
				System.out.println("\n\tFECHA_HORA: " + feature.getDate());
				System.out.println("\n\tINFRACCION: " + feature.getInfraction());
				System.out.println("\n\tCLASE_VEHI: " + feature.getVehicleClass());
				System.out.println("\n\tTIPO_SERVI: " + feature.getServiceType());
				System.out.println("\n\tLOCALIDAD: " + feature.getLocality());
				System.out.println("\n");
			}
			
		}
		
		public void printGeneralFeaturesInfo( Feature minValue, Feature maxValue, int featuresNumber ){
			
			System.out.println("\nDATOS GENERALES:");
			System.out.println("\n-----------------------------------\n\n");
			
			System.out.println("\nCOMPATENDO CON VALOR MINIMO:");
			printFeature( minValue );
			
			System.out.println("\nCOMPARENDO CON VALOR MAXIMO:");
			printFeature( maxValue );
			
			System.out.println("\nNUMERO TOTAL DE COMPARENDOS: " + featuresNumber);
			System.out.println("\n");
			
		}
}