package com.ironiacorp.io;
/*
This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

Copyright (C) 2009 Marco Aurelio Graciotto Silva <magsilva@ironiacorp.com>
*/



import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class NonBlockingClient
{
	public static final int MAX_TRIES = 10;
	
	public static final int TIMEOUT = 1000;
	
	private int maxTries = MAX_TRIES;
	
	private int timeout = TIMEOUT;
	
	public SocketChannel connect(String host, int port)
	{
		SocketChannel channel = null;
		boolean connected = false;
		
		try {
			channel = SocketChannel.open();
			channel.configureBlocking(false);
			connected = channel.connect(new InetSocketAddress(host, port));

			if (! connected) {
				int tries = 0;
				while (! connected && tries < maxTries) {
					try {
						Thread.sleep(timeout);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					connected = channel.finishConnect();
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
				
		
		return channel;
	}
}
