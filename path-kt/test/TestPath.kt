import org.junit.jupiter.api.Test
import plumy.pathkt.*
import java.util.*

class TestPathKt {
    @Test
    fun `test easy bfs path finder`() {
        //create a graph
        val graph = Graph().apply {
            Node("root").apply {
                sub("sub1") {
                    sub("sub1-sub1")
                    sub("sub1-sub2") {
                        sub("sub1-sub2-1")
                    }
                }
                sub("sub2")
                sub("sub3") {
                    sub("sub3-1")
                    sub("sub3-2")
                    sub("sub3-3")
                }
            }
        }
        LinkedList<Graph.Node>().apply {
            graph.collectAllNodes(this)
            assert(this.size == graph.nodes.size)
            assert(this.containsAll(graph.nodes))
            println(this)
        }
        val start = graph.nodes.find { it.name == "sub3-3" }!!
        val end = graph.nodes.find { it.name == "sub1-sub2-1" }!!
        graph.findPath(start, end) { _, path ->
            val str = path.joinToString("->")
            println(str)
            assert(str == "sub3-3->sub3->root->sub1->sub1-sub2->sub1-sub2-1")
            true
        }
    }
}

class Graph : BFS<Graph.Node, LinkedPath<Graph.Node>>
by EasyBFS(::Pointer, ::LinkedPath) {
    private var id = 0
    val nodes = ArrayList<Node>()
    lateinit var root: Node
    fun collectAllNodes(list: MutableList<Node>) {
        forEachVertices(root) {
            list.add(it)
        }
    }

    class Pointer : BFS.IPointer<Graph.Node> {
        override lateinit var self: Node
        override var previous: BFS.IPointer<Node>? = null
    }

    inner class Node(val name: String) : IVertex<Node> {
        init {
            if (id == 0) root = this
            nodes.add(this)
            id++
        }

        override val linkedVertices = ArrayList<Node>()
        override fun toString() = name
    }

    inline fun Node.sub(name: String, genSub: Node.() -> Unit = {}): Node {
        val node = Node(name)
        this.linkedVertices.add(node)
        node.linkedVertices.add(this)
        node.genSub()
        return this
    }
}
