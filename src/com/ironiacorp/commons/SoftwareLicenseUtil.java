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

Copyright (C) 2005 Marco Aurelio Graciotto Silva <magsilva@gmail.com>
*/

package com.ironiacorp.commons;

/**
 * Some constants.
 */
public final class SoftwareLicenseUtil
{
	// TODO: deprecated licenses
	// TODO: read licenses from files
	// TODO: get licenses listing in runtime.
	
	/**
	 * We really don't want an instance of this class, so we create this
	 * private constructor.
	 */
	private SoftwareLicenseUtil()
	{
	}
	
	int DEFAULT_SEED = 37;
	
	/**
	 * Approved open source licenses. The ones listed here are approved by the
	 * Open Source Initiative (http://www.opensource.org).
	 */
	String[] OSI_APPROVED_LICENSES = {
			"Academic Free License\n" +
			"Adaptive Public License\n" +
			"Apache Software License\n" +
			"Apache License, 2.0\n" +
			"Apple Public Source License\n" +
			"Artistic license\n" +
			"Attribution Assurance Licenses\n" +
			"New BSD license\n" +
			"Computer Associates Trusted Open Source License 1.1\n" +
			"Common Development and Distribution License\n" +
			"Common Public License 1.0\n" +
			"CUA Office Public License Version 1.0\n" +
			"EU DataGrid Software License\n" +
			"Eclipse Public License\n" +
			"Educational Community License\n" +
			"Eiffel Forum License\n" +
			"Eiffel Forum License V2.0\n" +
			"Entessa Public License\n" +
			"Fair License\n" +
			"Frameworx License\n" +
			"GNU General Public License (GPL)\n" +
			"GNU Library or \"Lesser\" General Public License (LGPL)\n" +
			"Lucent Public License (Plan9)\n" +
			"Lucent Public License Version 1.02\n" +
			"IBM Public License\n" +
			"Intel Open Source License\n" +
			"Historical Permission Notice and Disclaimer\n" +
			"Jabber Open Source License\n" +
			"MIT license\n" +
			"MITRE Collaborative Virtual Workspace License (CVW License)\n" +
			"Motosoto License\n" +
			"Mozilla Public License 1.0 (MPL)\n" +
			"Mozilla Public License 1.1 (MPL)\n" +
			"NASA Open Source Agreement 1.3\n" +
			"Naumen Public License\n" +
			"Nethack General Public License\n" +
			"Nokia Open Source License\n" +
			"OCLC Research Public License 2.0\n" +
			"Open Group Test Suite License\n" +
			"Open Software License\n" +
			"PHP License\n" +
			"Python license (CNRI Python License)\n" +
			"Python Software Foundation License\n" +
			"Qt Public License (QPL)\n" +
			"RealNetworks Public Source License V1.0\n" +
			"Reciprocal Public License\n" +
			"Ricoh Source Code Public License\n" +
			"Sleepycat License\n" +
			"Sun Industry Standards Source License (SISSL)\n" +
			"Sun Public License\n" +
			"Sybase Open Watcom Public License 1.0\n" +
			"University of Illinois/NCSA Open Source License\n" +
			"Vovida Software License v. 1.0\n" +
			"W3C License\n" +
			"wxWindows Library License\n" +
			"X.Net License\n" +
			"Zope Public License\n" +
			"zlib/libpng license\n"
	};
}
