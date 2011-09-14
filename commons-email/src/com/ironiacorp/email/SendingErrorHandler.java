package com.ironiacorp.email;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SendingErrorHandler
{
	public enum SendingErrorReason {
		UNKNOWN_USER(
			"User unknown",
			"unknown   user",
			"User   unknown",
			"unknown user",
			"Address out-of-date",
			"Address rejected",
			"Address   rejected",
			"This user doesn't have a yahoo.com account",
			"The email account that you tried to reach does not exist",
			"The email account that you   tried to reach does not exist",
			"The email account   that you tried to reach does not exist",
			"The email   account that you tried to reach does not exist",
			"mailbox unavailable",
			"Recipient address rejected",
			"Recipient unknown",
			"Relay access denied",
			"mailbox   unavailable",
			"Sorry, no mailbox here by that name.",
			"Sorry, no mailbox here by   that name."
		),
		INVALID_ADDRESS(
			"bad recipient address syntax"
		),
		OVER_QUOTA(
			"Over quota",
			"input/output error",
			"The users mailfolder is over the allowed quota (size)",
			"The users mailfolder is over the allowed quota (size)",
			"Error while   writing to",
			"Error while writing to",
			"Mailbox quota exceeded",
			"Mailbox   quota exceeded",
			"mailfolder is over the allowed quota",
			"mailbox is full"
		),
		HOST_NOT_FOUND(
			"Host or domain name not found",
			"Host   or domain name not found",
			"Host not found",
			"Host or   domain name not found"
		),
		SPAMMER(
				"Listed at APEWS-L2"
		);
		
		Pattern[] patterns;
		
		private SendingErrorReason(String... patterns)
		{
			this.patterns = new Pattern[patterns.length];
			for (int i = 0; i < patterns.length; i++) {
				this.patterns[i] = Pattern.compile(patterns[i], Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
			}
		}
	}


	
	public static void main(String[] args) throws Exception
	{
		File inputFile = new File("/home/magsilva/Unsaved Document 1");
		FileReader reader = new FileReader(inputFile);
		BufferedReader breader = new BufferedReader(reader);
		Pattern pattern = Pattern.compile(Email.EMAIL_PATTERNS[0]);
		String line = null;
		String email = null;
		StringBuilder errorMessageBuffer = new StringBuilder();
		String errorMessage = null;
		SendingErrorReason error = null;
		Map<String, Set<SendingErrorReason>> globalErrors = new HashMap<String, Set<SendingErrorReason>>();
		
		while ((line = breader.readLine()) != null) {
			if (line.trim().isEmpty()) {
				errorMessage = errorMessageBuffer.toString();
				try {
					for (SendingErrorReason e : SendingErrorReason.values()) {
						for (Pattern p : e.patterns) {
							Matcher m = p.matcher(errorMessage);
							if (m.find()) {
								error = e;
								errorMessage = null;
							}
						}
					}
				} catch (NullPointerException npe) {}
				
				if (email != null) {
					Set<SendingErrorReason> errors; 
					if (! globalErrors.containsKey(email)) {
						errors = new HashSet<SendingErrorReason>();
						globalErrors.put(email, errors);
					} else {
						errors = globalErrors.get(email);
					}
					
					if (error != null) {
						errors.add(error);
					} else {
						System.out.print(email);
						System.out.print(": ");
						System.out.print("Unknown reason");
						System.out.println(errorMessageBuffer.toString());
					}
				}
				
				email = null;
				errorMessageBuffer.setLength(0);
				error = null;
			} else {
				Matcher matcher = pattern.matcher(line);
				if (matcher.find()) {
					email = line.substring(matcher.start(), matcher.end());
				}
				errorMessageBuffer.append(line);
			}
		}
		
		for (String key : globalErrors.keySet()) {
			System.out.print(key);
			System.out.print(": ");
			for (SendingErrorReason ser : globalErrors.get(key)) {
				System.out.print(ser);
				System.out.print(" ");
			}
			System.out.println();
		}
	}
}
