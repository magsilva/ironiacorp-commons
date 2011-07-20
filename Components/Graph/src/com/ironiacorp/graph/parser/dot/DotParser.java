/* Generated By:JavaCC: Do not edit this line. DotParser.java */
package com.ironiacorp.graph.parser.dot;

import java.util.*;
import java.util.regex.Matcher;
import java.io.*;
import com.ironiacorp.graph.model.*;
import com.ironiacorp.graph.rendering.*;

public class DotParser implements DotParserConstants {
                private Graph graph;

                private Node findLabel(String x)
                {
                                if (vnode == null)      {
                                                return null;
                                }
                                Iterator i = vnode.iterator();
                                while (i.hasNext()) {
                                                Node ret = (GVFNode) i.next();
                                                if (x.equals(ret.getSource())) {
                                                                return ret;
                                                }
                                }
                                return null;
                }

                private Edge findEdge(String orig, String dest)
                {
                                if (vlink == null)
                                {
                                                return null;
                                }
                                Iterator i = vlink.iterator();
                                while (i.hasNext())
                                {
                                                GVFLink ret = (GVFLink) i.next();
                                                if (orig.equals(ret.getSourceNode().getSource()) && dest.equals(ret.getDestinationNode().getSource()))
                                                {
                                                                return ret;
                                                }
                                }
                                return null;
                }

                private void setPositionPreCheck()
                {
                                RenderingDescription rd = graph.getRenderingDescription();
                                BoundingBox bb = rd.getBoundingBox();
                                if (bb == null || bb.getHeight() == 0 || bb.getWidth() == 0) {
                                                throw new IllegalArgumentException("The graph height and width must be set before adding nodes or links");
                                }
                }

                private void setPosition(String label, Position pos)
                {
                                RenderingDescription rd = graph.getRenderingDescription();
                                BoundingBox bb = rd.getBoundingBox();
                                GVFNode node = findLabel(label);
                                if (node != null) {
                                                setPositionPreCheck();
                                                node.moveTo((int) pos.getX(), (int) (bb.getHeight() - pos.getY()));
                                }
                }

                private void setLinkPosition(String l1, String l2, List < Position > pos)
                {
                                RenderingDescription rd = graph.getRenderingDescription();
                                BoundingBox bb = rd.getBoundingBox();
                                GVFLink link = findLink(l1, l2);
                                if (link == null) {
                                                return;
                                }
                                Iterator < Position > i = pos.iterator();
                                while (i.hasNext()) {
                                                setPositionPreCheck();
                                                Position p = i.next();
                                                link.addPoint((int) p.getX(), (int) (bb.getHeight() - p.getY()));
                                }
                }

                private void setBoundingBox(BoundingBox bb)
                {
                                RenderingDescription rd = graph.getRenderingDescription();
                                if (rd == null) {
                                  rd = new RenderingDescription();
                                  graph.setRenderingDescription(rd);
                                }
                                rd.setBoundingBox(bb);
                }

  final public void parse() throws ParseException {
    jj_consume_token(DIGRAPH);
    jj_consume_token(IDENTIFIER);
    jj_consume_token(LBRACE);
    label_1:
    while (true) {
      switch (jj_nt.kind) {
      case GRAPH:
      case NODE:
      case DIRECTED_EDGE:
      case LBRACKET:
      case NODEIDENT:
        ;
        break;
      default:
        break label_1;
      }
      switch (jj_nt.kind) {
      case GRAPH:
        graphDef();
        break;
      case NODE:
        defaultNode();
        break;
      default:
        if (jj_2_1(2147483647)) {
          linkDef();
        } else {
          switch (jj_nt.kind) {
          case LBRACKET:
          case NODEIDENT:
            nodeDef();
            break;
          default:
            jj_consume_token(-1);
            throw new ParseException();
          }
        }
      }
      jj_consume_token(SEMICOLON);
    }
    jj_consume_token(RBRACE);
  }

  final public void defaultNode() throws ParseException {
    jj_consume_token(NODE);
    atributeList();
  }

  final public void atributeList() throws ParseException {
    jj_consume_token(LBRACKET);
    atribute();
    label_2:
    while (true) {
      switch (jj_nt.kind) {
      case COMMA:
        ;
        break;
      default:
        break label_2;
      }
      jj_consume_token(COMMA);
      atribute();
    }
    jj_consume_token(RBRACKET);
  }

  final public void atribute() throws ParseException {
    jj_consume_token(IDENTIFIER);
    jj_consume_token(EQUALS);
    jj_consume_token(STRING);
  }

  final public void graphDef() throws ParseException {
                BoundingBox bb = null;
    jj_consume_token(GRAPH);
    jj_consume_token(LBRACKET);
    switch (jj_nt.kind) {
    case BBOX:
      bb = boundingBox();
                                                                setBoundingBox(bb);
      break;
    default:
      ;
    }
    label_3:
    while (true) {
      switch (jj_nt.kind) {
      case COMMA:
        ;
        break;
      default:
        break label_3;
      }
      jj_consume_token(COMMA);
      switch (jj_nt.kind) {
      case BBOX:
        bb = boundingBox();
                                                                                setBoundingBox(bb);
        break;
      default:
        ;
      }
    }
    jj_consume_token(RBRACKET);
  }

  final public void nodeDef() throws ParseException {
                Position p = null;
                String l = null;
                Token t = null;
    switch (jj_nt.kind) {
    case NODEIDENT:
      t = jj_consume_token(NODEIDENT);
                                                l = t.image;
      break;
    default:
      ;
    }
    jj_consume_token(LBRACKET);
    switch (jj_nt.kind) {
    case IDENTIFIER:
      atribute();
      break;
    default:
      switch (jj_nt.kind) {
      case POS:
        p = nodePosition();
                                                                setPosition(l, p);
        break;
      default:
        ;
      }
    }
    label_4:
    while (true) {
      switch (jj_nt.kind) {
      case COMMA:
        ;
        break;
      default:
        break label_4;
      }
      jj_consume_token(COMMA);
      switch (jj_nt.kind) {
      case IDENTIFIER:
        atribute();
        break;
      default:
        switch (jj_nt.kind) {
        case POS:
          p = nodePosition();
                                                                                setPosition(l, p);
          break;
        default:
          ;
        }
      }
    }
    jj_consume_token(RBRACKET);
  }

  final public List < Position > linkPosition() throws ParseException {
                Token t = null;
                List < Position > positions = new ArrayList < Position > ();
    jj_consume_token(POS);
    jj_consume_token(EQUALS);
    if (jj_2_2(2)) {
      t = jj_consume_token(STRING);
                                                String lineBreak = Matcher.quoteReplacement("\u005cn");
                                                String carriageReturn = Matcher.quoteReplacement("\u005cr");
                                                String endLineSlash = Matcher.quoteReplacement("\u005c\u005c");
                                                String quote = Matcher.quoteReplacement("\u005c"");
                                                String token = t.image.replaceAll(lineBreak, "").replaceAll(carriageReturn, "").replaceAll(endLineSlash, "").replaceAll(quote, "").substring(2);
                                                StringTokenizer st = new StringTokenizer(token, " ");
                                                while (st.hasMoreTokens())
                                                {
                                                                String s = st.nextToken();
                                                                String[] sps = s.split(",");
                                                                Position p = new Position(Double.valueOf(sps[0]), Double.valueOf(sps[1]));
                                                                positions.add(p);
                                                }
    } else {
      ;
    }
                                {if (true) return positions;}
    throw new Error("Missing return statement in function");
  }

  final public Position nodePosition() throws ParseException {
                Token t = null;
    jj_consume_token(POS);
    jj_consume_token(EQUALS);
    t = jj_consume_token(STRING);
                                String[] sps = t.image.replaceAll("\u005c"", "").split(",");
                                Position p = new Position(Double.valueOf(sps[0]), Double.valueOf(sps[1]));
                                {if (true) return p;}
    throw new Error("Missing return statement in function");
  }

  final public BoundingBox boundingBox() throws ParseException {
                Token t = null;
    jj_consume_token(BBOX);
    jj_consume_token(EQUALS);
    t = jj_consume_token(STRING);
                                BoundingBox box = null;
                                String [ ] values = t.image.replaceAll("\u005c"", "").split(",");
                                double x = Double.valueOf(values [ 0 ]);
                                double y = Double.valueOf(values [ 1 ]);
                                double width = Double.valueOf(values [ 2 ]);
                                double height = Double.valueOf(values [ 3 ]);
                                {if (true) return new BoundingBox(x, y, width, height);}
    throw new Error("Missing return statement in function");
  }

  final public void linkDef() throws ParseException {
                String s1 = null;
                String s2 = null;
                Token t1 = null;
                Token t2 = null;
                List<Position> p = null;
    switch (jj_nt.kind) {
    case NODEIDENT:
      t1 = jj_consume_token(NODEIDENT);
                                                s1 = t1.image;
      break;
    default:
      ;
    }
    jj_consume_token(DIRECTED_EDGE);
    switch (jj_nt.kind) {
    case NODEIDENT:
      t2 = jj_consume_token(NODEIDENT);
                                                s2 = t2.image;
      break;
    default:
      ;
    }
    jj_consume_token(LBRACKET);
    switch (jj_nt.kind) {
    case IDENTIFIER:
      atribute();
      break;
    default:
      switch (jj_nt.kind) {
      case POS:
        p = linkPosition();
                                                                setLinkPosition(s1, s2, p);
        break;
      default:
        ;
      }
    }
    label_5:
    while (true) {
      switch (jj_nt.kind) {
      case COMMA:
        ;
        break;
      default:
        break label_5;
      }
      jj_consume_token(COMMA);
      switch (jj_nt.kind) {
      case IDENTIFIER:
        atribute();
        break;
      default:
        switch (jj_nt.kind) {
        case POS:
          p = linkPosition();
                                                                                setLinkPosition(s1, s2, p);
          break;
        default:
          ;
        }
      }
    }
    jj_consume_token(RBRACKET);
  }

  private boolean jj_2_1(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_1(); }
    catch(LookaheadSuccess ls) { return true; }
  }

  private boolean jj_2_2(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_2(); }
    catch(LookaheadSuccess ls) { return true; }
  }

  private boolean jj_3_2() {
    if (jj_scan_token(STRING)) return true;
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
  }

  /** Constructor. */
  public DotParser(java.io.Reader stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new DotParserTokenManager(jj_input_stream);
    token = new Token();
    token.next = jj_nt = token_source.getNextToken();
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    token.next = jj_nt = token_source.getNextToken();
  }

  /** Constructor with generated Token Manager. */
  public DotParser(DotParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    token.next = jj_nt = token_source.getNextToken();
  }

  /** Reinitialise. */
  public void ReInit(DotParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    token.next = jj_nt = token_source.getNextToken();
  }

  private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken = token;
    if ((token = jj_nt).next != null) jj_nt = jj_nt.next;
    else jj_nt = jj_nt.next = token_source.getNextToken();
    if (token.kind == kind) {
      return token;
    }
    jj_nt = token;
    token = oldToken;
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
    if (jj_scanpos.kind != kind) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) throw jj_ls;
    return false;
  }


/** Get the next Token. */
  final public Token getNextToken() {
    if ((token = jj_nt).next != null) jj_nt = jj_nt.next;
    else jj_nt = jj_nt.next = token_source.getNextToken();
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

  /** Generate ParseException. */
  public ParseException generateParseException() {
    Token errortok = token.next;
    int line = errortok.beginLine, column = errortok.beginColumn;
    String mess = (errortok.kind == 0) ? tokenImage[0] : errortok.image;
    return new ParseException("Parse error at line " + line + ", column " + column + ".  Encountered: " + mess);
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

}
