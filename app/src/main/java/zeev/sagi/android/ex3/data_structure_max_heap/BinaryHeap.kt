@file:Suppress("PackageName")

package zeev.sagi.android.ex3.data_structure_max_heap

import zeev.sagi.android.ex3.dataclass.contact
import java.util.NoSuchElementException

@Suppress("unused", "MemberVisibilityCanPrivate")
/**
 * MaxHeap implemented using Array
 */
class BinaryHeap
/**
 * This will initialize our heap with default size.
 */


(capacity: Int) {
    private val heap: Array<contact>
    private var heapSize: Int = 0
    private val d = 2
    private val map:HashMap<String,contact> = HashMap()
    /**
     * This will check if the heap is empty or not
     * Complexity: O(1)
     */
    val isEmpty: Boolean
        get() = heapSize == 0

    /**
     * This will check if the heap is full or not
     * Complexity: O(1)
     */
    val isFull: Boolean
        get() = heapSize == heap.size

    init {
        heapSize = 0
        heap = Array(capacity+1,fun (_:Int):contact = contact("0",null,0) )
    }


    private fun parent(i: Int): Int {
        return (i - 1) / d
    }

    private fun kthChild(i: Int, k: Int): Int {
        return d * i + k
    }

    /**
     * This will insert new element in to heap
     * Complexity: O(log N)
     * As worst case scenario, we need to traverse till the root
     */
    fun insert(x: contact) {
        if (isFull)
            throw NoSuchElementException("Heap is full, No space to insert new element")
        val key:String = x.phone_no
        if(map[key]!=null)
        {
            map[key]!!.calls++
            heapifyDown(0)
            heapifyUp(heapSize - 1)
        }
        else
        {
            map[key] = x
            heap[heapSize++] = x
            heapifyUp(heapSize - 1)
        }
    }

    /**
     * This will delete element at index x
     * Complexity: O(log N)
     *
     */
    fun delete(x: Int): contact {
        if (isEmpty)
            throw NoSuchElementException("Heap is empty, No element to delete")
        val key = heap[x]
        heap[x] = heap[heapSize - 1]
        heapSize--
        map.remove(key.phone_no)
        heapifyDown(x)
        return key
    }

    /**
     * This method used to maintain the heap property while inserting an element.
     *
     */
    private fun heapifyUp(i: Int) {
        @Suppress("NAME_SHADOWING")
        var i = i
        val temp = heap[i]
        while (i > 0 && temp > heap[parent(i)]) {
            heap[i] = heap[parent(i)]
            i = parent(i)
        }
        heap[i] = temp
    }

    /**
     * This method used to maintain the heap property while deleting an element.
     *
     */
    private fun heapifyDown(i: Int) {
        @Suppress("NAME_SHADOWING")
        var i = i
        var child: Int
        val temp = heap[i]
        while (kthChild(i, 1) < heapSize) {
            child = maxChild(i)
            if (temp < heap[child])
            {
                heap[i] = heap[child]
            } else
                break

            i = child
        }
        heap[i] = temp
    }

    private fun maxChild(i: Int): Int {
        val leftChild = kthChild(i, 1)
        val rightChild = kthChild(i, 2)

        return if (heap[leftChild] > heap[rightChild]) leftChild else rightChild
    }

    /**
     * This method used to print all element of the heap
     *
     */
    fun printHeap() {
        print("\nHeap = ")
        for (i in 0 until heapSize)
            print(heap[i].toString() + " ")
        println()
    }

    /**
     * This method returns the max element of the heap.
     * complexity: O(1)
     */
    fun findMax(): contact {
        if (isEmpty)
            throw NoSuchElementException("Heap is empty.")
        return heap[0]
    }


}