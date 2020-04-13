package model.logic;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import model.data_structures.IRedBlackBST;
import model.data_structures.RedBlackBST;

/**
 * Definicion del modelo del mundo
 *
 */
public class Modelo {
	
	/**
	 * Red black tree
	 */
	private IRedBlackBST<Integer, Feature> redBlackBST;
	
	/**
	 * First feature
	 */
	private Feature minValue;
	
	/**
	 * Last feature
	 */
	private Feature maxValue;
	
	/**
	 * Constructor del modelo del mundo con capacidad predefinida
	 */
	public Modelo()
	{
		redBlackBST = new RedBlackBST<>();
	}
	
	/**
	 * Servicio de consulta de numero de elementos presentes en el modelo 
	 * @return numero de elementos presentes en el modelo
	 */
	public int size(){
		return redBlackBST.size();
	}
	
	public int height() {
		return redBlackBST.height();
	}
	public Feature getMinValue(){
		return minValue;
	}
	
	public Feature getMaxValue(){
		return maxValue;
	}
	
	public Feature searchByID( int objectID ){
		return redBlackBST.get( objectID );
	}
	
	public Iterator<Feature> searchByIDRange( int initID, int endID ){
		return redBlackBST.valuesInRange(initID, endID);
	}
	
	public boolean loadDataList(String path) {
		if( loadGson(path) )
			return true;
		else	
			return false;
	}

	private boolean loadGson(String path) {

		try {
			System.out.println(path);
			JsonReader reader = new JsonReader(new FileReader(path));
			JsonElement featuresElement = JsonParser.parseReader(reader).getAsJsonObject().get("features");
			JsonArray jsonFeaturesArray = featuresElement.getAsJsonArray();

			for (JsonElement element : jsonFeaturesArray) {

				String elemType = element.getAsJsonObject().get("type").getAsString();

				JsonElement elemProperties = element.getAsJsonObject().get("properties");

				int elemId = elemProperties.getAsJsonObject().get("OBJECTID").getAsInt();
				String elemDate = elemProperties.getAsJsonObject().get("FECHA_HORA").getAsString();
				String elemDetectionMethod = elemProperties.getAsJsonObject().get("MEDIO_DETECCION").getAsString();
				String elemVehicleClass = elemProperties.getAsJsonObject().get("CLASE_VEHICULO").getAsString();
				String elemServiceType = elemProperties.getAsJsonObject().get("TIPO_SERVICIO").getAsString();
				String elemInfraction = elemProperties.getAsJsonObject().get("INFRACCION").getAsString();
				String elemInfractionReason = elemProperties.getAsJsonObject().get("DES_INFRACCION").getAsString();
				String elemLocality = elemProperties.getAsJsonObject().get("LOCALIDAD").getAsString();
				String elemTown = elemProperties.getAsJsonObject().get("MUNICIPIO").getAsString();
				
				JsonElement elemGeometry = element.getAsJsonObject().get("geometry");

				String elemGeomType = elemGeometry.getAsJsonObject().get("type").getAsString();
				JsonArray elemGeomCoordinates = elemGeometry.getAsJsonObject().get("coordinates").getAsJsonArray();
				ArrayList<Integer> elemCoordinates = new ArrayList<Integer>();

				for (JsonElement elemCoord : elemGeomCoordinates) {
					int actualCoord = elemCoord.getAsInt();
					elemCoordinates.add(actualCoord);
				}

				elemVehicleClass = elemVehicleClass.startsWith("AUTOM") ? "AUTOMOVIL" : elemVehicleClass;
				
				Feature feature = new Feature(elemType, elemId, elemDate, elemDetectionMethod, elemVehicleClass,
						elemServiceType, elemInfraction, elemInfractionReason, elemLocality, elemTown, elemGeomType,
						elemCoordinates);

				loadElement(feature);

				if( minValue == null )
					minValue = feature;
				else if( minValue.getObjectId() > feature.getObjectId() )
					minValue = feature;
				
				if( maxValue == null )
					maxValue = feature;
				else if( maxValue.getObjectId() < feature.getObjectId() )
					maxValue = feature;
				
			}

		} catch (FileNotFoundException e) {
			System.out.println("ERROR! File not found\n\n");
			return false;
		}
		
		return true;

	}
	
	
	private void loadElement(Feature feature){
		
		redBlackBST.put( feature.getObjectId(), feature );
		
	}


}
