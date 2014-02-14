package com.ironiacorp.errorhandler;

import java.util.Stack;

/**
 * This class is based upon the counterpart CallStack class from Java Tools
 * (http://mpii.de/yago-naga/javatools), which is licensed under the Creative
 * Commons Attribution License (http://creativecommons.org/licenses/by/3.0) by 
 * the YAGO-NAGA team (see http://mpii.de/yago-naga).
 *
 * This class represents the current position of a program, i.e. the stack of
 * methods that have been called together with the line numbers.
 */


public class CallStack
{
	/**
	 * Holds the call stack
	 */
	private Stack<StackTraceElement> callstack;
  
	/**
	 * Constructs a call stack from the current program position (without the constructor call)
	 */
	public CallStack()
	{
		callstack = new Stack<StackTraceElement>();
		// Alternatives: Thread.currentThread().getStackTrace(), new Throwable().getStackTrace()
		StackTraceElement[] ste = new Throwable.getStackTrace();
		for (int i = ste.length - 1; i != 0; i--) {
			callstack.push(ste[i]);
		}

		/* The code below gets all threads
		ThreadMXBean bean = ManagementFactory.getThreadMXBean();
		ThreadInfo[] infos = bean.dumpAllThreads(true, true);
		for (ThreadInfo info : infos) {
  			StackTraceElement[] elems = info.getStackTrace();
			// Print out elements, etc.
		}
		*/
	}
  
  /** Returns TRUE if the two call stacks have the same elements*/
  public boolean equals(Object o) {
    return(o instanceof CallStack && ((CallStack)o).callstack.equals(callstack));
  }
  
  /** Returns a nice String for a Stacktraceelement*/
  public static String toString(StackTraceElement e) {
    String cln=e.getClassName();
    if(cln.lastIndexOf('.')!=-1) cln=cln.substring(cln.lastIndexOf('.')+1);
    return(cln+"."+e.getMethodName()+'('+e.getLineNumber()+')');    
  }
  
  /** Returns "method(line)->method(line)->..." */
  public String toString() {
    StringBuilder s=new StringBuilder();
    for(int i=0;i<callstack.size()-1;i++) {
      s.append(toString(callstack.get(i))).append("->");
    }
    s.append(toString(callstack.get(callstack.size()-1)));
    return(s.toString());
  }

  /** Gives the calling position as a StackTraceElement */
  public StackTraceElement top() {
    return(callstack.peek());
  }

  /** Gives the calling position */
  public static StackTraceElement here() {
    CallStack p=new CallStack();
    p.callstack.pop();
    return(p.callstack.peek());
  }
  
  /** Returns the callstack */
  public Stack<StackTraceElement> getCallstack() {
    return callstack;
  }

  /** Pops the top level of this callstack, returns this callstack */
  public CallStack ret() {
    callstack.pop();
    return(this);
  }

  /** Test routine */
  public static void main(String[] args) {
    D.p(new CallStack());
    D.p(here());
  }

}
