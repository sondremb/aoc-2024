class Day09 : Day {
    override fun part1(input: List<String>): String {
        val numbers = parseInput(input)
        // file, empty, file, empty...
        val files = numbers
            .filterIndexed { index, _ -> index % 2 == 0 }
            .mapIndexed { i, v -> File(i, v) }
        val empties = numbers.filterIndexed { index, _ -> index % 2 == 1 }.toList().iterator()
        var total = 0L
        var backfill = files.reversed().iterator()
        var currentIndex = 0
        var backfillFile = backfill.next()
        var remainingFileSize = backfillFile.size
        var fileIterator = files.iterator()
        var file = fileIterator.next()
        while (file.id < backfillFile.id && fileIterator.hasNext()) {
            repeat(file.size) {
                total += file.id * currentIndex
                currentIndex++
            }
            file = fileIterator.next()
            var spaces = if (empties.hasNext()) empties.next() else 0
            while (spaces > 0) {
                if (remainingFileSize == 0) {
                    backfillFile = backfill.next()
                    remainingFileSize = backfillFile.size
                }
                if (backfillFile.id <= file.id) break
                val backfillSize = minOf(remainingFileSize, spaces)
                repeat(backfillSize) {
                    total += backfillFile.id * currentIndex
                    currentIndex++
                    spaces--
                    remainingFileSize--
                }
            }
        }
        if (file.id == backfillFile.id) {
            repeat(remainingFileSize) {
                total += file.id * currentIndex
                currentIndex++
            }
        }
        return total.toString()
    }

    private fun parseInput(input: List<String>): List<Int> {
        return input.flatMap { row -> row.toCharArray().map(Char::digitToInt) }
    }

    override fun part2(input: List<String>): String {
        val numbers = parseInput(input)
        var head = Block(null, 0)
        var current = head
        numbers.forEachIndexed { index, size ->
            current.next = Block(if (index % 2 == 0) index / 2 else null, size)
            current.next!!.prev = current
            current = current.next!!
        }
        var currentFile = current
        while (currentFile.prev != null) {
            if (!currentFile.isFile()) {
                currentFile = currentFile.prev!!
                continue
            }
            var currentEmpty = head
            while (currentEmpty.id != currentFile.id) {
                if (!currentEmpty.isEmpty()) {
                    currentEmpty = currentEmpty.next!!
                    continue
                }
                if (currentEmpty.size >= currentFile.size) {
                    // move file to start of empty
                    var newFile = Block(currentFile.id, currentFile.size)
                    currentEmpty.insertBefore(newFile)
                    currentEmpty.size -= currentFile.size
                    if (currentEmpty.size == 0) {
                        currentEmpty.remove()
                    }

                    currentFile.id = null

                    break
                }
                currentEmpty = currentEmpty.next!!
            }
            currentFile = currentFile.prev!!
        }
        var something: Block? = head
        var currentIndex = 0
        var total = 0L
        while (something != null) {
            repeat(something.size) {
                total += (something?.id ?: 0) * currentIndex
                currentIndex++
            }
            something = something.next
        }
        return total.toString()
    }

    private fun print(head: Block) {
        var current = head
        var str = ""
        while (current.next != null) {
            repeat(current.size) {
                str += "${current.id ?: "."} "
            }
            current = current.next!!
        }
        repeat(current.size) {
            str += "${current.id ?: "."} "
        }
        println(str)
    }

    private data class File(val id: Int, var size: Int)
    private data class Block(var id: Int?, var size: Int) {
        var next: Block? = null
        var prev: Block? = null
        fun isFile() = id != null
        fun isEmpty() = id == null
        override fun toString(): String {
            return "Block(id=$id, size=$size, next=${next?.id}, prev=${prev?.id})"
        }

        fun insertBefore(block: Block) {
            block.prev = prev
            block.next = this
            prev?.next = block
            prev = block
        }

        fun remove() {
            prev?.next = next
            next?.prev = prev
        }
    }
}