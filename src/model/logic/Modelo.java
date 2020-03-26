package model.logic;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.sun.xml.internal.ws.util.StringUtils;

import Exception.InconsistenceException;
import model.data_structures.ILinearProbingHash;
import model.data_structures.ISeparateChainningHash;
import model.data_structures.LinearProbingHash;
import model.data_structures.Queue;
import model.data_structures.SeparateChainningHash;

/**
 * Definicion del modelo del mundo
 *
 */
public class Modelo {
	
	/**
	 * Linear probing hash
	 */
	private ILinearProbingHash<String, LinkedList<Feature>> linearProbingHash;
	
	/**
	 * Separate chainning hash
	 */
	private ISeparateChainningHash<String, LinkedList<Feature>> separateChainningHash;
	
	/**
	 * First feature
	 */
	private Feature firstFeature;
	
	/**
	 * Last feature
	 */
	private Feature lastFeature;
	
	/**
	 * Size
	 */
	private int size;
	
	/**
	 * Constructor del modelo del mundo con capacidad predefinida
	 */
	public Modelo()
	{
		linearProbingHash = new LinearProbingHash<>(10);
		separateChainningHash = new SeparateChainningHash<>(10);
	}
	
	/**
	 * Constructor del modelo del mundo con capacidad dada
	 * @param tamano
	 */
	public Modelo(int capacity )
	{
		linearProbingHash = new LinearProbingHash<>( capacity );
		separateChainningHash = new SeparateChainningHash<>( capacity );
	}
	
	/**
	 * Servicio de consulta de numero de elementos presentes en el modelo 
	 * @return numero de elementos presentes en el modelo
	 */
	public int size(){
		return size;
	}
	
	public Feature getFirstFeature(){
		return firstFeature;
	}
	
	public Feature getLastFeature(){
		return lastFeature;
	}
	
	public LinkedList<Feature> searchKeyOnLinearProbing( String key ){
		System.out.println("KeyToSearch: " + key);
		return linearProbingHash.get(key);
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

				loadMapElement(feature);

				if( firstFeature == null )
					firstFeature = feature;
				
				lastFeature = feature;
			}

		} catch (FileNotFoundException e) {
			System.out.println("ERROR! File not found\n\n");
			return false;
		}
		catch( InconsistenceException e ){
			System.out.println("ERROR! " + e.getMessage() + " \n\n");
			return false;
		}
		
		return true;

	}
	
	
	private void loadMapElement(Feature feature) throws InconsistenceException{
		
		String key = feature.getDate() + feature.getVehicleClass() + feature.getInfraction();

		LinkedList<Feature> linearProbingVal = linearProbingHash.get(key);
		LinkedList<Feature> separateChainningVal = separateChainningHash.get(key);
		
		if( linearProbingVal != null && separateChainningVal != null ){
			linearProbingVal.add(feature);
			separateChainningVal.add(feature);
		}
		else if( linearProbingVal == null && separateChainningVal == null ){
			LinkedList<Feature> val = new LinkedList<>();
			val.add(feature);
			linearProbingHash.put(key, val);
			separateChainningHash.put(key, val);
		}
		else{
			throw new InconsistenceException("INCONSISTENCE: Both hash maps not have the same data");
		}
		
		size++;
		
	}


}
