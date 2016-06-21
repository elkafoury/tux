/*******************************************************************************
 * JavaJACK - A JACK bridge for Java.
 *     Copyright (C) 2011  Chuck Ritola
 * 	chuck@ritolaaudio.com 
 * 
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package com.ritolaaudio.javajack.client;

import com.ritolaaudio.jack.JackLibrary.JackSessionEventType;

public enum SessionEventType
	{
	Save,
	SaveAndQuit,
	SaveTemplate,
	Invalid;

	public static SessionEventType denumerate(int type)
		{
		switch(type)
			{
			case JackSessionEventType.JackSessionSave:
				{
				return Save;
				}
			case JackSessionEventType.JackSessionSaveAndQuit:
				{
				return SaveAndQuit;
				}
			case JackSessionEventType.JackSessionSaveTemplate:
				{
				return SaveTemplate;
				}
			}//end switch()
		throw(new RuntimeException("int value "+type+" not recognized as an enum value."));
		}//end denumerate()

	public static int enumerate(SessionEventType eventType)
		{
		switch(eventType)
			{
			case Save:
				return JackSessionEventType.JackSessionSave;
			case SaveAndQuit:
				return JackSessionEventType.JackSessionSaveAndQuit;
			case SaveTemplate:
				return JackSessionEventType.JackSessionSaveTemplate;
			}//end switch
		throw(new RuntimeException("Tried to enumerate an invalid enum."));
		}
	}
