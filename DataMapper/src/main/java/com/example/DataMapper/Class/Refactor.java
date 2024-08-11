package com.example.DataMapper.Class;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor // Add if Construction Without Argument is NECESSARY
public class Refactor {
	private String ID;
	private String VALUE;
	private int INDEX;
	private String TIMESTAMP;
	
	public Refactor(String ID, String VALUE, int INDEX, String TIMESTAMP) {
		this.ID = ID;
		this.VALUE = VALUE;
		this.INDEX = INDEX;
		this.TIMESTAMP = TIMESTAMP;
	}
}
