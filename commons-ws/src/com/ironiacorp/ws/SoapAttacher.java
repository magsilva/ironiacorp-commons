/*
 * Copyright (C) 2011 Marco Aurélio Graciotto Silva <magsilva@ironiacorp.com>
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
 */

package com.ironiacorp.ws;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;

import javax.activation.DataHandler;
import javax.xml.soap.AttachmentPart;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;


public class SoapAttacher
{
	public void attach(SOAPMessage message, URL attachment, String id)
	{
		DataHandler dataHandler = new DataHandler(attachment);
		AttachmentPart att = message.createAttachmentPart(dataHandler);
		att.setContentId(id);
		message.addAttachmentPart(att);
	}
	
	public void attach(SOAPMessage message, URL attachment, String mimetype, String id) 
	{
		AttachmentPart att = message.createAttachmentPart(attachment, mimetype);
		att.setContentId(id);
		message.addAttachmentPart(att);
	}
	
	public void detach(SOAPMessage message, String id, File outputFilename)
	{
		Iterator<AttachmentPart> atts = message.getAttachments();
		while (atts.hasNext()) {
			AttachmentPart att = atts.next();
			if (att.getContentId().equals(id)) {
				InputStream is = null;
				try {
					is = att.getDataHandler().getInputStream();
				} catch (IOException e) {
					throw new RuntimeException(e);
				} catch (SOAPException e) {
					throw new RuntimeException(e);
				}
				FileOutputStream os = null;
				try {
					os = new FileOutputStream(outputFilename);
				} catch (FileNotFoundException e) {
					throw new RuntimeException(e);
				}
				byte[] buffer = new byte[4096];
				int read = 0;
				try {
					while ((read = is.read(buffer, 0, buffer.length)) != -1) {
						os.write(buffer, 0, read);
					}
					os.flush();
					os.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
				return;
			}
		}
	}
}