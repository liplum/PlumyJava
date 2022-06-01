package plumy.pathkt

class UnlinkedPointerException(val vert: IVertex<*>) : RuntimeException("$vert")
class NoDestinationException(val bfs: BFS<*, *>) : RuntimeException("$bfs")