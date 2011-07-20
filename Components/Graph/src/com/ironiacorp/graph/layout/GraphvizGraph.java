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

package com.ironiacorp.graph.layout;

import java.util.Iterator;

import com.ironiacorp.graph.model.DirectedEdge;
import com.ironiacorp.graph.model.Edge;
import com.ironiacorp.graph.model.Element;
import com.ironiacorp.graph.model.Graph;
import com.ironiacorp.graph.model.DirectedEdge.NodeType;
import com.ironiacorp.graph.model.Graph.GraphType;
import com.ironiacorp.graph.model.Node;

public class GraphvizGraph
{
	/**
	 * Returns the graph's source description in dot language.
	 * 
	 * @return Source of the graph in dot language.
	 */
	public String convert(Graph graph)
	{
		StringBuilder sb = new StringBuilder();
		if (graph.getType() == GraphType.DIRECTED) {
			sb.append("digraph G {\n");		
		} else {
			sb.append("graph G {\n");
		}

		for (Element element : graph.getElements()) {
			if (element instanceof Node) {
				sb.append("\t");
				sb.append(element.getId());
				sb.append(" ");
				sb.append("[");
				sb.append("label=");
				sb.append("\"");
				sb.append(element.getLabel());
				sb.append("\"");
				sb.append("];\n");
			} else if (element instanceof Edge) {
				Edge edge = (Edge) element;
				sb.append("\t");
				if (graph.getType() == GraphType.DIRECTED) {
					if (edge instanceof DirectedEdge) {
						DirectedEdge directedEdge = (DirectedEdge) element;
						sb.append("{");
						for (Node nodeSrc : directedEdge.getEdges(NodeType.SOURCE)) {
							sb.append(nodeSrc.getId());
							sb.append(" ");
						}
						sb.append("}");
						sb.append(" -> ");
						sb.append("{");
						for (Node nodeDest : directedEdge.getEdges(NodeType.DEST)) {
							sb.append(nodeDest.getId());
							sb.append(" ");
						}
						sb.append("}");
						sb.append(" ");
						sb.append("[");
						sb.append("label=");
						sb.append("\"");
						sb.append(element.getLabel());
						sb.append("\"");
						sb.append("];\n");
					} else {
						Iterator<Node> i = edge.getNodes().iterator();
						sb.append("\t");
						while (i.hasNext()) {
							Node node = i.next();
							sb.append(node.getId());
							if (i.hasNext()) {
								sb.append(" -- ");
							}
						}
						sb.append(" ");
						sb.append("[");
						sb.append("label=");
						sb.append("\"");
						sb.append(element.getLabel());
						sb.append("\"");
						sb.append("];\n");
					}
				}
			}
		}
		
		sb.append("}");
		
		return sb.toString();
	}
	
	// TODO: Fix parsin
	/*
	public Graph convert(String graph)
	{
		String result = getDotLayout(getDotSource());

		File f = new File(result);
		try {
			DotParser dt = new DotParser(vNodes, vLinks, new FileInputStream(f));
			dt.parse();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			f.delete();
		}
	}
	*/
}
