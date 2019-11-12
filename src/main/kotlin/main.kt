import uni.madani.model.graph.Vertex.Vertex
import uni.madani.model.graph.graph.Graph
import uni.madani.persist.filePersist.GMLParser
import java.nio.file.Path
import java.util.HashSet


fun dfs(graph: Graph): Pair<String, HashMap<Vertex, Int>> {

    val visitedVertices = HashSet<Vertex>()
    val stringBuilder = StringBuilder()
    val repeatedVertex = HashMap<Vertex, Int>()

    stringBuilder.append("dfs{\n")

    for (vertex in graph.verticesCollection)
        when {
            !visitedVertices.contains(vertex) -> {
                stringBuilder.append("dfsUnit{\n")
                stringBuilder.append(dfsUtil(graph, vertex, visitedVertices,repeatedVertex))
                stringBuilder.append("}\n")
            }
        }

    stringBuilder.append("}")
    return Pair(stringBuilder.toString(), repeatedVertex)
}


private fun dfsUtil(graph: Graph, processingVertex: Vertex, visitedVertices: HashSet<Vertex>,repeatedVertex : HashMap<Vertex, Int>): String {
    val stringBuilder = StringBuilder()

    visitedVertices.add(processingVertex)
    stringBuilder.append(processingVertex).append("\n")

    for (edge in processingVertex.out) {
        val vertex = graph.getVertex(edge.targetId)
        when {
            !visitedVertices.contains(vertex) -> stringBuilder.append(dfsUtil(graph, vertex, visitedVertices,repeatedVertex))
            repeatedVertex.containsKey(vertex) -> {
                val i = repeatedVertex[vertex]!!.toInt()
                repeatedVertex[vertex] = i + 1
            }
            vertex.values.getValue("type") == "process" -> {
                repeatedVertex[vertex] = 1
            }
            else -> {
                when {
                    vertex.values.getValue("instanceNumber").toInt() <= vertex.values.getValue("usedInstance").toInt() -> {
                        repeatedVertex[vertex] = 1
                    }
                    else -> {
                        repeatedVertex[vertex] = repeatedVertex[vertex]!!.toInt() + 1
                    }
                }
            }
        }

    }

    return stringBuilder.toString()
}


fun main() {
    val graph = GMLParser.getInstance().parsingFromFile(Path.of("graphs/graph.graph"))
    println(graph)
    println(dfs(graph))
}
