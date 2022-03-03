package com.phone.processor;

import java.util.Map;

import com.phone.processor.mapper.CoutryCodeMapper;
import com.phone.processor.service.IPhoneProcessorService;
import com.phone.processor.service.impl.PhoneProcessorServiceImpl;

public class MainApplication {

	private static final String COUNTRY_CODES_FILE = "coutryCodes.txt";
	private static final String SEPARATOR = ":";
	
	private static Map<String, String> coutryCodes;
	
	
	public static void main(String[] args) {

		CoutryCodeMapper mapper = new CoutryCodeMapper();		
		coutryCodes = mapper.fileToObject(COUNTRY_CODES_FILE);
		
		IPhoneProcessorService service = new PhoneProcessorServiceImpl();
		Map<String, Integer> outputMap = service.coutryCounter(coutryCodes, args);		
				
		outputMap.forEach((key, value) -> System.out.println(key + SEPARATOR + value));
	}


}
