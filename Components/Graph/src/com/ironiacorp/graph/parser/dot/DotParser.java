/* Generated By:JavaCC: Do not edit this line. DotParser.java */
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

package com.ironiacorp.graph.parser.dot;

import java.util.*;
import com.ironiacorp.graph.model.*;

/**
 * Parser for graphs defined in the Graphviz format. Based on documentation
 * provided at:
 * - http://www.graphviz.org/doc/info/attrs.html
 * - http://www.graphviz.org/doc/info/lang.html
 */
public class DotParser implements DotParserConstants {
        public class DefaultNodePropertiesElement extends Element {};
        public class DefaultEdgePropertiesElement extends Element {};
        public class DefaultGraphPropertiesElement extends Element {};

  final public Graph parse() throws ParseException {
        Graph graph;
    graph = graphDef();
          {if (true) return graph;}
    throw new Error("Missing return statement in function");
  }

  final public Graph graphDef() throws ParseException {
        DefaultNodePropertiesElement defaultNodeProperties = null;
        DefaultEdgePropertiesElement defaultEdgeProperties = null;
        DefaultGraphPropertiesElement defaultGraphProperties = null;
        Graph graph = new Graph();
        List<Element> elements;
        Token t;
    switch (jj_nt.kind) {
    case STRICT:
      jj_consume_token(STRICT);
      break;
    default:
      jj_la1[0] = jj_gen;
      ;
    }
    switch (jj_nt.kind) {
    case GRAPH:
      jj_consume_token(GRAPH);
                                  graph.setType(Graph.GraphType.UNDIRECTED);
      break;
    case DIGRAPH:
      jj_consume_token(DIGRAPH);
                                    graph.setType(Graph.GraphType.DIRECTED);
      break;
    default:
      jj_la1[1] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    switch (jj_nt.kind) {
    case IDENTIFIER:
      t = jj_consume_token(IDENTIFIER);
                                graph.setLabel(t.image);
      break;
    default:
      jj_la1[2] = jj_gen;
      ;
    }
    elements = statementList();
                for (Element e : elements) {
                        if (e instanceof DefaultNodePropertiesElement) {
                                defaultNodeProperties = (DefaultNodePropertiesElement) e;
                        }
                        if (e instanceof DefaultEdgePropertiesElement) {
                                defaultEdgeProperties = (DefaultEdgePropertiesElement) e;
                        }
                        if (e instanceof DefaultGraphPropertiesElement) {
                                defaultGraphProperties = (DefaultGraphPropertiesElement) e;
                        }
                }

                for (Element e : elements) {
                        Map<String, Object> nodeAttributes = (defaultNodeProperties == null) ? null : defaultNodeProperties.getAttributes();
                        Map<String, Object> edgeAttributes = (defaultEdgeProperties == null) ? null : defaultEdgeProperties.getAttributes();
                        Map<String, Object> graphAttributes = (defaultGraphProperties == null) ? null : defaultGraphProperties.getAttributes();
                        if (e instanceof Node && nodeAttributes != null) {
                                for (String name : nodeAttributes.keySet()) {
                                        if (! e.containsAttribute(name)) {
                                                e.setAttribute(name, nodeAttributes.get(name));
                                        }
                                }
                        }
                        if (e instanceof Edge && edgeAttributes != null) {
                                for (String name : edgeAttributes.keySet()) {
                                        if (! e.containsAttribute(name)) {
                                                e.setAttribute(name, edgeAttributes.get(name));
                                        }
                                }
                        }
                        if (e instanceof Graph && graphAttributes != null) {
                                for (String name : graphAttributes.keySet()) {
                                        if (! e.containsAttribute(name)) {
                                                e.setAttribute(name, graphAttributes.get(name));
                                        }
                                }
                        }
                }

                graph.removeElement(defaultNodeProperties);
                graph.removeElement(defaultEdgeProperties);
                graph.removeElement(defaultGraphProperties);

                for (Element e : elements) {
                        graph.addElement(e);
                }

                {if (true) return graph;}
    throw new Error("Missing return statement in function");
  }

  final public List<Element> statementList() throws ParseException {
        List<Element> elements = new ArrayList<Element>();
        List<Element> statementElements;
    jj_consume_token(LBRACE);
    label_1:
    while (true) {
      switch (jj_nt.kind) {
      case GRAPH:
      case SUBGRAPH:
      case DEFAULT_PROPERTY_NODE:
      case DEFAULT_PROPERTY_EDGE:
      case NODEIDENT:
        ;
        break;
      default:
        jj_la1[3] = jj_gen;
        break label_1;
      }
      statementElements = statement();
      jj_consume_token(SEMICOLON);
                        elements.addAll(statementElements);
    }
    switch (jj_nt.kind) {
    case GRAPH:
    case SUBGRAPH:
    case DEFAULT_PROPERTY_NODE:
    case DEFAULT_PROPERTY_EDGE:
    case NODEIDENT:
      statementElements = statement();
                        elements.addAll(statementElements);
      break;
    default:
      jj_la1[4] = jj_gen;
      ;
    }
    jj_consume_token(RBRACE);
          {if (true) return elements;}
    throw new Error("Missing return statement in function");
  }

  final public List<Element> statement() throws ParseException {
        List<Element> elements = null;
        Element element = null;
    switch (jj_nt.kind) {
    case DEFAULT_PROPERTY_NODE:
      element = defaultNodeDef();
      break;
    case DEFAULT_PROPERTY_EDGE:
      element = defaultEdgeDef();
      break;
    case GRAPH:
      element = defaultGraphDef();
      break;
    case SUBGRAPH:
      element = subgraphDef();
      break;
    default:
      jj_la1[5] = jj_gen;
      if (jj_2_1(2147483647)) {
        elements = linkDef();
      } else if (jj_2_2(2147483647)) {
        elements = linkDef();
      } else {
        switch (jj_nt.kind) {
        case NODEIDENT:
          element = nodeDef();
          break;
        default:
          jj_la1[6] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      }
    }
                if (element != null) {
                        elements = new ArrayList<Element>(1);
                        elements.add(element);
                }

                {if (true) return elements;}
    throw new Error("Missing return statement in function");
  }

  final public List<Property> attributeList() throws ParseException {
        List<Property> properties = new ArrayList<Property>();
        Property property;
    jj_consume_token(LBRACKET);
    property = attributeDef();
                                    properties.add(property);
    label_2:
    while (true) {
      switch (jj_nt.kind) {
      case COMMA:
        ;
        break;
      default:
        jj_la1[7] = jj_gen;
        break label_2;
      }
      jj_consume_token(COMMA);
      property = attributeDef();
                                             properties.add(property);
    }
    jj_consume_token(RBRACKET);
          {if (true) return properties;}
    throw new Error("Missing return statement in function");
  }

  final public Property attributeDef() throws ParseException {
        Property property = null;
        Token name, value;
    name = jj_consume_token(IDENTIFIER);
    jj_consume_token(EQUALS);
    value = jj_consume_token(STRING);
                        property = new Property();
                        property.setName(name.image);
                        property.setValue(value.image.substring(1, value.image.length() - 1));
                        {if (true) return property;}
    throw new Error("Missing return statement in function");
  }

  final public Graph subgraphDef() throws ParseException {
        Graph graph = new Graph();
        List<Element> elements;
        Token name;
    jj_consume_token(SUBGRAPH);
    switch (jj_nt.kind) {
    case IDENTIFIER:
      name = jj_consume_token(IDENTIFIER);
                        graph.setLabel(name.image);
      break;
    default:
      jj_la1[8] = jj_gen;
      ;
    }
    elements = statement();
                for (Element e : elements) {
                        graph.addElement(e);
                }
                {if (true) return graph;}
    throw new Error("Missing return statement in function");
  }

  final public DefaultNodePropertiesElement defaultNodeDef() throws ParseException {
        DefaultNodePropertiesElement defaultProperties = new DefaultNodePropertiesElement();
        List<Property> properties;
    jj_consume_token(DEFAULT_PROPERTY_NODE);
    properties = attributeList();
                        for (Property property : properties) {
                                defaultProperties.setAttribute(property);
                        }
                        {if (true) return defaultProperties;}
    throw new Error("Missing return statement in function");
  }

  final public DefaultEdgePropertiesElement defaultEdgeDef() throws ParseException {
        DefaultEdgePropertiesElement defaultProperties = new DefaultEdgePropertiesElement();
        List<Property> properties;
    jj_consume_token(DEFAULT_PROPERTY_EDGE);
    properties = attributeList();
                        for (Property property : properties) {
                                defaultProperties.setAttribute(property);
                        }
                        {if (true) return defaultProperties;}
    throw new Error("Missing return statement in function");
  }

  final public DefaultGraphPropertiesElement defaultGraphDef() throws ParseException {
        DefaultGraphPropertiesElement defaultProperties = new DefaultGraphPropertiesElement();
        List<Property> properties;
    jj_consume_token(GRAPH);
    properties = attributeList();
                        for (Property property : properties) {
                                defaultProperties.setAttribute(property);
                        }
                        {if (true) return defaultProperties;}
    throw new Error("Missing return statement in function");
  }

  final public Node nodeDef() throws ParseException {
        Node node = new Node();
        List<Property> properties;
        Token t = null;
    t = jj_consume_token(NODEIDENT);
                          node.setLabel(t.image);
    properties = attributeList();
                for (Property property : properties) {
                        node.setAttribute(property);
                }
                {if (true) return node;}
    throw new Error("Missing return statement in function");
  }

  final public List<Element> linkDef() throws ParseException {
        List<Element> edges = new ArrayList<Element>();
        List<Property> properties;
        DirectedEdge directedEdge = null;
        Edge edge = null;
        Node node1 = null;
        Node node2 = null;
        Token srcNodeName;
        Token destNodeName;
    srcNodeName = jj_consume_token(NODEIDENT);
                node1 = new Node();
                node1.setLabel(srcNodeName.image);
    label_3:
    while (true) {
      switch (jj_nt.kind) {
      case EDGE:
        jj_consume_token(EDGE);
                                         edge = new Edge();
        break;
      case DIRECTED_EDGE:
        jj_consume_token(DIRECTED_EDGE);
                                                  directedEdge = new DirectedEdge(); edge = directedEdge;
        break;
      default:
        jj_la1[9] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      destNodeName = jj_consume_token(NODEIDENT);
                        node2 = new Node();
                        node2.setLabel(destNodeName.image);
                        if (directedEdge != null) {
                                directedEdge.addNode(node1, DirectedEdge.NodeType.SOURCE);
                                directedEdge.addNode(node2, DirectedEdge.NodeType.DEST);
                        } else {
                                edge.addNode(node1);
                                edge.addNode(node2);
                        }
                        node1 = node2;
                        node2 = null;
                        edges.add(edge);
      switch (jj_nt.kind) {
      case EDGE:
      case DIRECTED_EDGE:
        ;
        break;
      default:
        jj_la1[10] = jj_gen;
        break label_3;
      }
    }
    properties = attributeList();
                for (Element element : edges) {
                        Edge e = (Edge) element;
                        for (Property property : properties) {
                                e.setAttribute(property);
                        }
                }
                {if (true) return edges;}
    throw new Error("Missing return statement in function");
  }

  private boolean jj_2_1(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_1(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(0, xla); }
  }

  private boolean jj_2_2(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_2(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(1, xla); }
  }

  private boolean jj_3_2() {
    if (jj_scan_token(NODEIDENT)) return true;
    if (jj_scan_token(EDGE)) return true;
    return false;
  }

  private boolean jj_3_1() {
    if (jj_scan_token(NODEIDENT)) return true;
    if (jj_scan_token(DIRECTED_EDGE)) return true;
    return false;
  }

  /** Generated Token Manager. */
  public DotParserTokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private Token jj_scanpos, jj_lastpos;
  private int jj_la;
  private int jj_gen;
  final private int[] jj_la1 = new int[11];
  static private int[] jj_la1_0;
  static {
      jj_la1_init_0();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x2000,0xc0,0x20000,0x10740,0x10740,0x740,0x10000,0x4000000,0x20000,0x1800,0x1800,};
   }
  final private JJCalls[] jj_2_rtns = new JJCalls[2];
  private boolean jj_rescan = false;
  private int jj_gc = 0;

  /** Constructor with InputStream. */
  public DotParser(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public DotParser(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new DotParserTokenManager(jj_input_stream);
    token = new Token();
    token.next = jj_nt = token_source.getNextToken();
    jj_gen = 0;
    for (int i = 0; i < 11; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    token.next = jj_nt = token_source.getNextToken();
    jj_gen = 0;
    for (int i = 0; i < 11; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor. */
  public DotParser(java.io.Reader stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new DotParserTokenManager(jj_input_stream);
    token = new Token();
    token.next = jj_nt = token_source.getNextToken();
    jj_gen = 0;
    for (int i = 0; i < 11; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    token.next = jj_nt = token_source.getNextToken();
    jj_gen = 0;
    for (int i = 0; i < 11; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor with generated Token Manager. */
  public DotParser(DotParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    token.next = jj_nt = token_source.getNextToken();
    jj_gen = 0;
    for (int i = 0; i < 11; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(DotParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    token.next = jj_nt = token_source.getNextToken();
    jj_gen = 0;
    for (int i = 0; i < 11; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken = token;
    if ((token = jj_nt).next != null) jj_nt = jj_nt.next;
    else jj_nt = jj_nt.next = token_source.getNextToken();
    if (token.kind == kind) {
      jj_gen++;
      if (++jj_gc > 100) {
        jj_gc = 0;
        for (int i = 0; i < jj_2_rtns.length; i++) {
          JJCalls c = jj_2_rtns[i];
          while (c != null) {
            if (c.gen < jj_gen) c.first = null;
            c = c.next;
          }
        }
      }
      return token;
    }
    jj_nt = token;
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }

  static private final class LookaheadSuccess extends java.lang.Error { }
  final private LookaheadSuccess jj_ls = new LookaheadSuccess();
  private boolean jj_scan_token(int kind) {
    if (jj_scanpos == jj_lastpos) {
      jj_la--;
      if (jj_scanpos.next == null) {
        jj_lastpos = jj_scanpos = jj_scanpos.next = token_source.getNextToken();
      } else {
        jj_lastpos = jj_scanpos = jj_scanpos.next;
      }
    } else {
      jj_scanpos = jj_scanpos.next;
    }
    if (jj_rescan) {
      int i = 0; Token tok = token;
      while (tok != null && tok != jj_scanpos) { i++; tok = tok.next; }
      if (tok != null) jj_add_error_token(kind, i);
    }
    if (jj_scanpos.kind != kind) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) throw jj_ls;
    return false;
  }


/** Get the next Token. */
  final public Token getNextToken() {
    if ((token = jj_nt).next != null) jj_nt = jj_nt.next;
    else jj_nt = jj_nt.next = token_source.getNextToken();
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;
  private int[] jj_lasttokens = new int[100];
  private int jj_endpos;

  private void jj_add_error_token(int kind, int pos) {
    if (pos >= 100) return;
    if (pos == jj_endpos + 1) {
      jj_lasttokens[jj_endpos++] = kind;
    } else if (jj_endpos != 0) {
      jj_expentry = new int[jj_endpos];
      for (int i = 0; i < jj_endpos; i++) {
        jj_expentry[i] = jj_lasttokens[i];
      }
      jj_entries_loop: for (java.util.Iterator<?> it = jj_expentries.iterator(); it.hasNext();) {
        int[] oldentry = (int[])(it.next());
        if (oldentry.length == jj_expentry.length) {
          for (int i = 0; i < jj_expentry.length; i++) {
            if (oldentry[i] != jj_expentry[i]) {
              continue jj_entries_loop;
            }
          }
          jj_expentries.add(jj_expentry);
          break jj_entries_loop;
        }
      }
      if (pos != 0) jj_lasttokens[(jj_endpos = pos) - 1] = kind;
    }
  }

  /** Generate ParseException. */
  public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[30];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 11; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 30; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    jj_endpos = 0;
    jj_rescan_token();
    jj_add_error_token(0, 0);
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

  private void jj_rescan_token() {
    jj_rescan = true;
    for (int i = 0; i < 2; i++) {
    try {
      JJCalls p = jj_2_rtns[i];
      do {
        if (p.gen > jj_gen) {
          jj_la = p.arg; jj_lastpos = jj_scanpos = p.first;
          switch (i) {
            case 0: jj_3_1(); break;
            case 1: jj_3_2(); break;
          }
        }
        p = p.next;
      } while (p != null);
      } catch(LookaheadSuccess ls) { }
    }
    jj_rescan = false;
  }

  private void jj_save(int index, int xla) {
    JJCalls p = jj_2_rtns[index];
    while (p.gen > jj_gen) {
      if (p.next == null) { p = p.next = new JJCalls(); break; }
      p = p.next;
    }
    p.gen = jj_gen + xla - jj_la; p.first = token; p.arg = xla;
  }

  static final class JJCalls {
    int gen;
    Token first;
    int arg;
    JJCalls next;
  }

}