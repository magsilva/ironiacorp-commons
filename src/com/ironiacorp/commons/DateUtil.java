/*
Copyright (C) 2005 Camila Kozlowski Della Corte <camilakoz@gmail.com>

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

	http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package com.ironiacorp.commons;

import java.text.DateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil
{
	/**
	 * Cria um calendario para obter a data e a hora do sistema
	 * 
	 * @return um objeto tipo date
	 */
	public static Date getCurrentDate()
	{
		GregorianCalendar calendar = new GregorianCalendar();
		Date data = calendar.getTime();
		return data;
	}

	/**
	 * Formata a data de um objeto tipo Date para o seguinte formato dd/mm/yyyy
	 * 
	 * @param data
	 *            Objeto tipo Date
	 * @return String contendo a data formatada
	 */
	public static String formatDate(Date data)
	{
		/* Cria um objeto date_format contendo o formato dd/mm/yyyy */
		DateFormat date_format = DateFormat.getDateInstance();
		String string_date = date_format.format(data);

		return string_date;
	}

	/**
	 * Formata a hora de um objeto tipo date para o seguinte formato hh:mm:ss
	 * 
	 * @param data
	 *            Objeto tipo Date
	 * @return String contendo a hora formatada
	 */
	public static String formatTime(Date data)
	{
		/* Cria um objeto time_format contendo o formato hh:mm:ss */
		DateFormat time_format = DateFormat.getTimeInstance();
		String string_time = time_format.format(data);

		return string_time;
	}
}
