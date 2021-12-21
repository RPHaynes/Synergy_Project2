package com.revature.shms.enums;

import javax.persistence.Entity;

public enum Amenities {
	TWIN_BED("TWIN_BED"),
	SOFA_BED("SOFA_BED"),
	QUEEN_BED("QUEEN_BED"),
	KING_BED("KING_BED"),
	SHOWER_BATH("SHOWER_BATH"),
	FULL_BATH("FULL_BATH"),
	SPA_BATH("SPA_BATH"),
	GOOD_VIEW("GOOD_VIEW"),
	FAIR_VIEW("FAIR_VIEW"),
	NO_VIEW("NO_VIEW"),
	FULL_KITCHEN("FULL_KITCHEN"),
	SMALL_KITCHEN("SMALL_KITCHEN"),
	ADA("ADA");
	String value;
	Amenities(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}
}
