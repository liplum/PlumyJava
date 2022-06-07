package plumy.pathkt

class UnlinkedPointerException(val vert: IVertex<*>) : RuntimeException("$vert")
class NoDestinationException(val bfs: VertContainer<*, *>) : RuntimeException("$bfs")