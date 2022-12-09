/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.drilldawn.util;

/**
 *
 * @author Work
 */
public class DataShareSingleton {

    private static DataShareSingleton dataShareSingleton_instance = null;
    private static final Object LOCK = new Object();

    private DataShareSingleton() {
        //empty private constructor
        //anything that needs to be initialized at instance creation can be done here
    }

    public DataShareSingleton getInstance() {
        
        if (dataShareSingleton_instance == null) {
            //make this thread safe
            synchronized(LOCK){
                if (dataShareSingleton_instance == null) {
                    dataShareSingleton_instance = new DataShareSingleton();
                }                
            }            
        }

        return dataShareSingleton_instance;
    }

    /**
     * TO share any data between the fxml pages or between any 2 classes
     * Create the data property that needs to be shared here 
     * (eg. anySharableDataObject). This object can be set from any fxml 
     * using the setAnySharableDataObject(..) method anf can be accessed 
     * from the other fxml using the getAnySharableDataObject() method.
     * 
     * To call these methods use
     * DataShareSingleton.getInstance.setAnySharableDataObject(object)
     * DataShareSingleton.getInstance.getAnySharableDataObject()
     * 
     * You can add more data variables to this class to make them accessible 
     * throughout the project.
     */
    private Object anySharableDataObject;

	public Object getAnySharableDataObject() {
		return anySharableDataObject;
	}

	public void setAnySharableDataObject(Object anySharableDataObject) {
		this.anySharableDataObject = anySharableDataObject;
	}
    
    
}
