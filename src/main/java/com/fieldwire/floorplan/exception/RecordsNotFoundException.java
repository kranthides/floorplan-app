package com.fieldwire.floorplan.exception;

public class RecordsNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 2671571197542942959L;
	private String key;

    public RecordsNotFoundException(String key, String message) {
        super(message);
        this.key = key;
    }

}
