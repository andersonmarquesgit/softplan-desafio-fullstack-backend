package com.softplan.restapi.api.enums;

public enum StatusEnum {

	New,
	Assigned,
	Resolved,
	Closed;
	
	public static StatusEnum getStatus(String status) {
		switch (status) {
			case "New": return New;
			case "Assigned": return Assigned;
			case "Resolved": return Resolved;
			case "Closed": return Closed;
			default : return New;
		}
	}
	
}
