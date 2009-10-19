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
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;


public abstract class NonBlockingServer
{
	private int port;
	
	private ServerSocketChannel channel;
	
	public NonBlockingServer(int port)
	{
		if (port <= 0 || port > 65656) {
			throw new IllegalArgumentException("Invalid port number");
		}
		
		this.port = port;
		try {
			channel = ServerSocketChannel.open();
			channel.socket().bind(new InetSocketAddress(this.port));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void acceptConnections()
	{
		Selector selector = null;
		try {
			selector = Selector.open();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		try {
			channel.configureBlocking(false);
			channel.register(selector, SelectionKey.OP_ACCEPT);
		} catch (IOException e) {
			try {
				selector.close();
			} catch (IOException e1) {
			}
			throw new RuntimeException(e);
		}

		while (true) {
			try {
				selector.select();
				Iterator<SelectionKey> i = selector.selectedKeys().iterator();
				while (i.hasNext()) {
					SelectionKey key = i.next();
					if (key.isAcceptable()) {
						ServerSocketChannel server = (ServerSocketChannel) key.channel();
						SocketChannel serverChannel = server.accept();
						serverChannel.configureBlocking(false);
						channel.register(selector, SelectionKey.OP_READ);
					}
					
					if (key.isReadable()) {
						processClient((SocketChannel) key.channel());
					}
					
					i.remove();
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		// selector.close();
	}
	
	protected abstract void processClient(SocketChannel socket);
	// socket.close();
}
