package com.phone.processor.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.phone.processor.service.IPhoneProcessorService;

public class PhoneProcessorServiceImpl implements IPhoneProcessorService {

	private static final String PORTUGAL = "Portugal";

	@Override
	public Map<String, Integer> coutryCounter(Map<String, String> coutryCodes, String[] args) {
		Map<String, Integer> outputMap = new HashMap<>();

		if (receivedFileIsValid(args)) {

			Path file = Paths.get(args[0]);

			try (InputStream in = Files.newInputStream(file);
					BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {

				String number = null;
				while ((number = reader.readLine()) != null) {

					if (numberIsShort(number)) {
						outputMap.merge(PORTUGAL, 1, Integer::sum);

					} else if (numberIsLong(number)) {
						outputMap.merge(getCoutry(number, coutryCodes), 1, Integer::sum);
					}
				}
			} catch (IOException x) {
				System.err.println(x);
			}

		} else {
			System.out.println("Your file path is not valid! Please check it and put the path correctly!");
			return outputMap;
		}

		return outputMap;
	}

	private boolean numberIsShort(String number) {
		
		if(numberIsNotValid(number)) return false;

		if (number.length() >= 4 && number.length() <= 6 
				&& !number.contains(" ")
				&& !number.substring(0, 1).contains("0")) {
			return true;

		} else {
			return false;
		}
	}

	private boolean numberIsLong(String number) {

		if(numberIsNotValid(number)) return false;
		
		// Removing '+' char or 0 if present
		if(number.substring(0, 1).equals("+") && !number.substring(1,2).equals(" ")) {
			number = number.substring(1);
		} else if(number.substring(0, 2).equals("00") && !number.substring(2, 3).equals(" ")) {
			number = number.substring(2);
		} else {
			return false;
		}
		
		number = number.replaceAll(" ", "");
		
		if (number.length() >= 9 && number.length() <= 14 
				&& !number.contains(" ")
				&& !number.substring(0, 1).contains("0")) {
			return true;

		} else {
			return false;
		}
	}

	private boolean numberIsNotValid(String number) {

		number = number.replaceAll(" ", "");		
		return !(number.matches("\\+[0-9]+$")
				|| number.matches("[0-9]+"));
	}

	private String getCoutry(String number, Map<String, String> coutryCodes) {
		
		Optional<String> code = null;
		if(number.substring(0, 1).equals("+")) {
			
			if(coutryCodes.containsValue(number.substring(1, 4))) {
				code = findKey(coutryCodes,number.substring(1, 4));
			} else if(coutryCodes.containsValue(number.substring(1, 3))) {
				code = findKey(coutryCodes,number.substring(1, 3));
			} else if(coutryCodes.containsValue(number.substring(1, 2))) {
				code = findKey(coutryCodes,number.substring(1, 2));
			} else {
				return "";
			}
			
		} else if(number.substring(0, 2).equals("00")) {
			
			if(coutryCodes.containsValue(number.substring(2, 5))) {
				code = findKey(coutryCodes,number.substring(2, 5));
			} else if(coutryCodes.containsValue(number.substring(2, 4))) {
				code = findKey(coutryCodes,number.substring(2, 4));
			} else if(coutryCodes.containsValue(number.substring(2, 3))) {
				code = findKey(coutryCodes,number.substring(2, 3));
			} else {
				return "";
			}
			
		}
		return code.get();
	}

	private boolean receivedFileIsValid(String[] args) {
		return args.length != 0 && (args[0] != null || args[0].isEmpty());
	}
	
	private Optional<String> findKey(Map<String, String> coutryCodes, String internationalCode){
	    return coutryCodes
	        .entrySet()
	        .stream()
	        .filter(e -> e.getValue().equals(internationalCode))
	        .map(Map.Entry::getKey)
	        .findFirst();
	}

}
