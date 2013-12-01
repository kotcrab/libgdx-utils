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

package pl.kotcrab.core;

import java.text.DecimalFormat;
import java.util.Arrays;

public class NumberUtils
{
	public static String intToString(int num, int digits)
	{
		assert digits > 0 : "Invalid number of digits";
		
		// create variable length array of zeros
		char[] zeros = new char[digits];
		Arrays.fill(zeros, '0');
		// format number as String
		DecimalFormat df = new DecimalFormat(String.valueOf(zeros));
		
		return df.format(num);
	}
}