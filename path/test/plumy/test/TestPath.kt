package plumy.test

import org.junit.jupiter.api.Test
import plumy.path.BFS
import plumy.path.GenericPath
import plumy.path.IPath
import plumy.path.Vertex
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
        val finder = BSF()
        val allNodes = ArrayList<Node>()
        //test iterate all nodes
        finder.eachVertices(root) {
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
        finder.curDestination = end
        finder.findPath(start) { _, path -> allPaths.add(path) }
        assert(allPaths.size == 1)
        val pathString = allPaths[0].joinToString("->")
        println(pathString)
        assert(pathString == "sub3-3->sub3->direct->sub1->sub1-sub2->sub1-sub2-1")
    }
}

class Node(val name: String) : Vertex<Node> {
    constructor() : this(id++.toString())

    val links = ArrayList<Node>()
    var pointer: BFS.Pointer<Node>? = null
    override fun getLinkedVertices() = links
    operator fun plusAssign(sub: Node) {
        links.add(sub)
    }

    companion object {
        private var id = 0
    }

    override fun toString() = name
}

inline fun Node.sub(name: String? = null, genSub: Node.() -> Unit = {}): Node {
    val node = if (name != null) Node(name) else Node()
    this += node
    node += this
    node.genSub()
    return this
}

class BSF : BFS<GenericPath<Node>, Node> {
    val queue = LinkedList<Node>()
    val seen = HashSet<BFS.Pointer<Node>>()
    lateinit var curDestination: Node
    override fun reset() {
        seen.clear()
        queue.clear()
    }

    override fun popCacheStack(): Node? =
        if (queue.isEmpty()) null
        else queue.pop()

    override fun pushCacheStack(newVertex: Node) {
        queue.add(newVertex)
    }

    override fun createPath(): GenericPath<Node> = GenericPath()
    override fun isDestination(origin: Node, vert: Node): Boolean =
        vert == curDestination

    override fun tryLinkNewPointer(linked: Node, itsPrevious: BFS.Pointer<Node>?): Boolean {
        if (linked.pointer == null) {
            linked.pointer = BFS.Pointer(linked, itsPrevious)
            return true
        }
        return false
    }

    override fun getLinkedPointer(vert: Node): BFS.Pointer<Node> =
        vert.pointer!!
}