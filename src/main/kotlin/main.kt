

import uni.madani.model.graph.Vertex.Vertex
import uni.madani.model.graph.graph.Graph
import uni.madani.persist.filePersist.GMLParser
import java.nio.file.Path
import java.util.HashSet


fun dfs(graph: Graph): String {

    val visitedVertices = HashSet<Vertex>()
    val stringBuilder = StringBuilder()
    stringBuilder.append("dfs{\n")

    for (vertex in graph.verticesCollection)
        if (!visitedVertices.contains(vertex)) {
            stringBuilder.append("dfsUnit{\n")
            stringBuilder.append(dfsUtil(graph, vertex, visitedVertices))
            stringBuilder.append("}\n")
        }

    stringBuilder.append("}")
    return stringBuilder.toString()
}

private fun dfsUtil(graph: Graph, processingVertex: Vertex, visitedVertices: HashSet<Vertex>): String {
    val stringBuilder = StringBuilder()

    visitedVertices.add(processingVertex)
    stringBuilder.append(processingVertex).append("\n")

    for (edge in processingVertex.out) {
        val vertex = graph.getVertex(edge.targetId)
        if (!visitedVertices.contains(vertex)) {
            stringBuilder.append(dfsUtil(graph, vertex, visitedVertices))
        }
    }

    return stringBuilder.toString()
}


fun main() {
        val graph2 = GMLParser.getInstance().parsingFromFile(Path.of("graphs/graph.graph"))
        println(graph2)
    }
