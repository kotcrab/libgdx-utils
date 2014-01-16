/*******************************************************************************
 * Copyright 2013 Pawel Pastuszak
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package pl.kotcrab.core.utils;

import java.util.Arrays;

public class NumberUtils
{
	/**
	 * Converts int to string.
	 * 
	 * Examples: intToString(1, 4) returns 0001 intToString(999, 5) returns 00999 intToString(123, 2) returns 123
	 * 
	 * @param num
	 *            Number that you want to convert
	 * @param digits
	 *            How many digit output numer should have
	 * @return Converted number as string
	 */
	public static String intToString(int num, int digits)
	{
		String snum = String.valueOf(num);
		
		char[] zeros;
		if(snum.length() > digits)
			zeros = new char[snum.length()];
		else
			zeros = new char[digits];
		
		Arrays.fill(zeros, '0');
		
		return (new String(zeros) + num).substring(snum.length());
	}
	
	// better but don't work with gwt
	// WARNING: DECIMALFORMAT SOMETIMES WAS CREATING NATIVE ERRORS (ON NEXUS 4) THAT CRASHED DEVICE DON'T USE IT
	// public static String intToString(int num, int digits)
	// {
	// assert digits > 0 : "Invalid number of digits";
	//
	// // create variable length array of zeros
	// char[] zeros = new char[digits];
	// Arrays.fill(zeros, '0');
	// // format number as String
	// DecimalFormat df = new DecimalFormat(String.valueOf(zeros));
	//
	// return df.format(num);
	// }
}