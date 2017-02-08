package com.xyz.crms.controller.manager;

import java.sql.Timestamp;
import java.util.Date;

abstract class AbstractTableManager {
	protected Facade facade;
	
	AbstractTableManager(Facade facade) {
		this.facade = facade;
	}
	
	protected Timestamp toTimestamp(Date date) {
		return new Timestamp(date.getTime());
	}
}
