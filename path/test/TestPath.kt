import org.junit.jupiter.api.Test
import plumy.path.*
import java.util.*

class TestPath {
    @Test
    fun `test find path in graph`() {
        //create a graph
        val root = Node("root")
        root.sub("direct") {
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
        val graph = Graph()
        val allNodes = ArrayList<Node>()
        //test iterate all nodes
        BFS.forEachVertices(graph, root) {
            allNodes.add(it)
        }
        val allNodesString = allNodes.toString()
        println(allNodes)
        assert(allNodesString == "[root, direct, sub1, sub2, sub3, sub1-sub1, sub1-sub2, sub3-1, sub3-2, sub3-3, sub1-sub2-1]")
        allNodes.forEach { it.pointer = null }
        val start = allNodes.find { it.name == "sub3-3" }!!
        val end = allNodes.find { it.name == "sub1-sub2-1" }!!
        val allPaths = ArrayList<IPath<Node>>()
        // test find
        graph.curDestination = end
        BFS.findPaths(graph, start) { _, path ->
            println(path.path.joinToString("->"))
            allPaths.add(path)
            true
        }
    }
}

class Node(val name: String) : Vertex<Node> {
    constructor() : this(id++.toString())

    val links = ArrayList<Node>()
    var pointer: VertContainer.Pointer<Node>? = null
    override fun getLinkedVertices() = links
    operator fun plusAssign(sub: Node) {
        links.add(sub)
    }

    companion object {
        private var id = 0
    }

    override fun toString() = name
    fun connectTo(node: Node) {
        this.linkedVertices.add(node)
        node.linkedVertices.add(this)
    }
}

inline fun Node.sub(name: String? = null, genSub: Node.() -> Unit = {}): Node {
    val node = if (name != null) Node(name) else Node()
    this += node
    node += this
    node.genSub()
    return this
}

class Graph : VertContainer<LinkedPath<Node>, Node> {
    val queue = LinkedList<Node>()
    val seen = HashSet<VertContainer.Pointer<Node>>()
    lateinit var curDestination: Node
    override fun reset() {
        seen.clear()
        queue.clear()
    }

    override fun popCache(): Node =
        throw NotImplementedError("BFS doesn't need to implement this.")

    override fun pushCache(newVertex: Node) {
        queue.add(newVertex)
    }

    override fun createPath(): LinkedPath<Node> = LinkedPath()
    override fun isDestination(origin: Node, vert: Node): Boolean =
        vert == curDestination

    override fun tryLinkNewPointer(linked: Node, itsPrevious: VertContainer.Pointer<Node>?): Boolean {
        if (linked.pointer == null) {
            linked.pointer = VertContainer.Pointer(linked, itsPrevious)
            return true
        }
        return false
    }

    override fun getLinkedPointer(vert: Node): VertContainer.Pointer<Node> =
        vert.pointer!!

    override fun pollCache(): Node? =
        if (queue.isEmpty()) null
        else queue.poll()
}