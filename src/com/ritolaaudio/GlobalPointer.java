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
package com.ritolaaudio;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

import com.sun.jna.Pointer;

/**
 * A Remote (RMI) stubbable partial wrapper of the JNA Pointer class providing remote
 * capabilities to most Pointer operations.<br><br>
 * Performing an instanceof operation against the LocallyAllocatedGlobalPointer class 
 * determines if the GlobalPointer was created on the local machine. If this is the case,
 * it can be casted to LocallyAllocatedGlobalPointer for calling getPointer() and consequently
 * obtaining a ByteBuffer to perform fast NIO operations.<br><br>
 * 
 * If this is backed by an RMI stub, the slow array methods are nearly unavoidable, 
 * save you create a class to pump a it as a ByteBuffer over a SocketChannel. wink wink.<br><br>
 * 
 * This has no relation to com.ochafik's GlobalPointer class, which serves a different purpose.
 * @author Chuck Ritola
 *
 */
public interface GlobalPointer extends Remote, Serializable
	{//RemoteStub
	/**
	 * Zero out a swath of memory.
	 * @param size	The number of bytes to zero out.
	 */
	void 	clear(long size) throws RemoteException;
	/**
	 * Get a byte at the given offset
	 * @param offset	Index following the pointer's memory location in bytes
	 * @return			Byte at given offset.
	 * @throws RemoteException
	 */
	byte 	getByte(long offset) throws RemoteException;
	/**
	 * Get an array of bytes at the given offset.
	 * @param offset offset in bytes
	 * @param arraySize
	 * @return
	 * @throws RemoteException
	 */
	byte[] 	getByteArray(long offset, int arraySize) throws RemoteException;
	/**
	 * Get char at the given offset.
	 * @param offset offset in bytes
	 * @return
	 * @throws RemoteException
	 */
	char 	getChar(long offset) throws RemoteException;
	/**
	 * Get a char array at the given offset of the given size.
	 * @param offset offset in bytes
	 * @param arraySize
	 * @return
	 * @throws RemoteException
	 */
	char[] 	getCharArray(long offset, int arraySize) throws RemoteException;
	/**
	 * Returns double at the given offset
	 * @param offset offset in bytes
	 * @return
	 * @throws RemoteException
	 */
	double 	getDouble(long offset) throws RemoteException;
	/**
	 * Returns double array get given offset and size.
	 * @param offset offset in bytes
	 * @param arraySize
	 * @return
	 * @throws RemoteException
	 */
	double[] 	getDoubleArray(long offset, int arraySize) throws RemoteException;
	/**
	 * Returns float at given offset
	 * @param offset
	 * @return
	 * @throws RemoteException
	 */
	float 	getFloat(long offset) throws RemoteException;
	/**
	 * Returns float array at given offset and size.
	 * @param offset offset in bytes
	 * @param arraySize
	 * @return
	 * @throws RemoteException
	 */
	float[] 	getFloatArray(long offset, int arraySize) throws RemoteException;
	/**
	 * Returns int at given offset
	 * @param offset offset in bytes
	 * @return
	 * @throws RemoteException
	 */
	int 	getInt(long offset) throws RemoteException;
	/**
	 * Returns int array at given offset of given size.
	 * @param offset offset in bytes
	 * @param arraySize
	 * @return
	 * @throws RemoteException
	 */
	int[] 	getIntArray(long offset, int arraySize) throws RemoteException;
	/**
	 * 
	 * @param offset offset in bytes
	 * @return
	 * @throws RemoteException
	 */
	long 	getLong(long offset) throws RemoteException;
	/**
	 * Returns an array of type long at the given offset with the given size.
	 * @param offset offset in bytes
	 * @param arraySize
	 * @return
	 * @throws RemoteException
	 */
	long[] 	getLongArray(long offset, int arraySize)  throws RemoteException;
	
	/**
	 * Sets a byte at the given offset to the specified value.
	 * @param offset offset in bytes
	 * @param value
	 * @throws RemoteException
	 */
	void 	setByte(long offset, byte value) throws RemoteException;
	/**
	 * Sets a char at the given offset to the specified value.
	 * @param offset
	 * @param value
	 * @throws RemoteException
	 */
	void 	setChar(long offset, char value) throws RemoteException;
	/**
	 * Sets a double at the given offset to the specified value.
	 * @param offset offset in bytes
	 * @param value
	 * @throws RemoteException
	 */
	void 	setDouble(long offset, double value) throws RemoteException;
	/**
	 * Sets a float at the given offset to the specified value.
	 * @param offset offset in bytes
	 * @param value
	 * @throws RemoteException
	 */
	void 	setFloat(long offset, float value) throws RemoteException;
	/**
	 * Sets an int at the given offset to the specified value.
	 * @param offset offset in bytes
	 * @param value
	 * @throws RemoteException
	 */
	void 	setInt(long offset, int value) throws RemoteException;
	/**
	 * Sets a long at the given offset to the specified value.
	 * @param offset
	 * @param value
	 * @throws RemoteException
	 */
	void 	setLong(long offset, long value) throws RemoteException;
	/**
	 * Sets a swath of memory of the given length at the given offset to the specified value.
	 * @param offset offset in bytes
	 * @param length
	 * @param value
	 * @throws RemoteException
	 */
	void 	setMemory(long offset, long length, byte value) throws RemoteException;
	/**
	 * Sets a short value at the specified offset.
	 * @param offset offset in bytes
	 * @param value
	 * @throws RemoteException
	 */
	void 	setShort(long offset, short value) throws RemoteException;
	/**
	 * Overwrites a String at the specified offset.
	 * @param offset offset in bytes
	 * @param value
	 * @throws RemoteException
	 */
	void 	setString(long offset, String value) throws RemoteException;
	/**
	 * Overwrites a String at the specified offset. For the time being 'wide' does nothing.
	 * @param offset offset in bytes
	 * @param value
	 * @param wide	Ignored
	 * @throws RemoteException
	 */
	void 	setString(long offset, String value, boolean wide) throws RemoteException;
	
	/**
	 * The local implementation of GlobalPointer. Instances of LocallyAllocatedGlobalPointer can expose the actual Pointer
	 * object normally hidden in GlobalPointer by using this class' getPointer() method.
	 * @author Chuck Ritola
	 *
	 */
	public static class LocallyAllocatedGlobalPointer implements GlobalPointer
		{
		/**
		 * 
		 */
		private static final long serialVersionUID = 6643766512452827886L;
		private transient volatile Pointer ptr;
		
		public LocallyAllocatedGlobalPointer(Pointer pointerToWrap)
			{ptr=pointerToWrap;}
		
		@Override
		public void clear(long size) throws RemoteException
			{ptr.clear(size);}

		@Override
		public byte getByte(long offset) throws RemoteException
			{return ptr.getByte(offset);}

		@Override
		public byte[] getByteArray(long offset, int arraySize)
				throws RemoteException
			{return ptr.getByteArray(offset, arraySize);}

		@Override
		public char getChar(long offset) throws RemoteException
			{return ptr.getChar(offset);}

		@Override
		public char[] getCharArray(long offset, int arraySize)
				throws RemoteException
			{return ptr.getCharArray(offset, arraySize);}

		@Override
		public double getDouble(long offset) throws RemoteException
			{return ptr.getDouble(offset);}

		@Override
		public double[] getDoubleArray(long offset, int arraySize)
				throws RemoteException
			{return ptr.getDoubleArray(offset, arraySize);}

		@Override
		public float getFloat(long offset) throws RemoteException
			{return ptr.getFloat(offset);}

		@Override
		public float[] getFloatArray(long offset, int arraySize)
				throws RemoteException
			{return ptr.getFloatArray(offset, arraySize);}

		@Override
		public int getInt(long offset) throws RemoteException
			{return ptr.getInt(offset);}

		@Override
		public int[] getIntArray(long offset, int arraySize)
				throws RemoteException
			{return ptr.getIntArray(offset, arraySize);}

		@Override
		public long getLong(long offset) throws RemoteException
			{return ptr.getLong(offset);}

		@Override
		public long[] getLongArray(long offset, int arraySize)
				throws RemoteException
			{return ptr.getLongArray(offset, arraySize);}

		@Override
		public void setByte(long offset, byte value) throws RemoteException
			{ptr.setByte(offset, value);}

		@Override
		public void setChar(long offset, char value) throws RemoteException
			{ptr.setChar(offset, value);}

		@Override
		public void setDouble(long offset, double value) throws RemoteException
			{ptr.setDouble(offset, value);}

		@Override
		public void setFloat(long offset, float value) throws RemoteException
			{ptr.setFloat(offset, value);}

		@Override
		public void setInt(long offset, int value) throws RemoteException
			{ptr.setInt(offset, value);}

		@Override
		public void setLong(long offset, long value) throws RemoteException
			{ptr.setLong(offset, value);}

		@Override
		public void setMemory(long offset, long length, byte value)
				throws RemoteException
			{ptr.setMemory(offset, length, value);}

		@Override
		public void setShort(long offset, short value) throws RemoteException
			{ptr.setShort(offset, value);}

		@Override
		public void setString(long offset, String value) throws RemoteException
			{ptr.setString(offset, value);}

		@Override
		public void setString(long offset, String value, boolean wide)
				throws RemoteException
			{ptr.setString(offset, value, wide);}
		
		public Pointer getPointer(){return ptr;}
		}//end class GlobalPointerImpl
	}//end GlobalPointer
