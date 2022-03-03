package com.phone.processor.service;

import java.util.Map;

public interface IPhoneProcessorService {

	Map<String, Integer> coutryCounter(Map<String, String> coutryCodes, String[] args);
	
}
