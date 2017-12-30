@file:Suppress("PackageName")

package zeev.sagi.android.ex3.data_structure_max_heap

import zeev.sagi.android.ex3.dataclass.contact
import java.util.*
import kotlin.collections.HashMap


@Suppress("unused", "MemberVisibilityCanPrivate")
/**
 * MaxHeap implemented using Array
 */
class MaxHeap(heap: Array<contact?>)
{

    var heap: Array<contact?>
        internal set
    var size: Int = 0
        internal set
    var hashmap:HashMap<String,contact> = HashMap()
    init {
        this.size = heap.size
        this.heap = Arrays.copyOf(heap, size)
    }

    /**
     * Makes the array {@param a} satisfy the max heap property starting from
     * {@param index} till the end of array.
     *
     *
     * See [me.ramswaroop.arrays.sorting.HeapSort.maxHeapify] for a modified
     * version of maxHeapify.
     *
     *
     * Time complexity: O(log n).
     *
     * @param index
     */
    fun maxHeapify(index: Int)
    {
        var largest = index
        val leftIndex = 2 * index + 1
        val rightIndex = 2 * index + 2

        if (leftIndex < size && heap[index]!=null && heap[leftIndex]!=null && heap[index]!! < heap[leftIndex]!!) {
            largest = leftIndex
        }
        if (rightIndex < size && heap[largest]!=null && heap[rightIndex]!=null && heap[largest]!! < heap[rightIndex]!!) {
            largest = rightIndex
        }

        if (largest != index) {
            swap(index, largest)
            maxHeapify(largest)
        }
    }

    /**
     * Converts array {@param a} in to a max heap.
     *
     *
     * Time complexity: O(n) and is not O(n log n).
     */
    fun buildMaxHeap() {
        for (i in size / 2 - 1 downTo 0) {
            maxHeapify(i)
        }
    }

    /**
     * Insert a new element into the heap satisfying
     * the heap property.
     *
     * Time complexity: O(log n) where 'n' is total no. of
     * elements in heap or O(h) where 'h' is the height of
     * heap.
     *
     * @param elem
     */
    fun insert(elem: contact)
    {
        if(hashmap.containsKey(elem.phone_no))
        {
            hashmap[elem.phone_no]!!.calls+=elem.calls
            buildMaxHeap()
        }
        else
        {
            // increase heap size
            heap = Arrays.copyOf(heap, size + 1)
            var i = size
            var parentIndex = Math.floor(((i - 1) / 2).toDouble()).toInt()
            // move up through the heap till you find the right position
            while (i > 0 && elem > heap[parentIndex]) {
                heap[i] = heap[parentIndex]
                i = parentIndex
                parentIndex = Math.floor(((i - 1) / 2).toDouble()).toInt()
            }
            heap[i] = elem
            size++
            hashmap.put(elem.phone_no,elem)
        }
    }

    fun findMax(): contact? {
        return if (size == 0)
        {
            null
        }
        else
        {
            heap[0]
        }
    }

    fun extractMax(): contact? {
        if (size == 0)
            return null
        val max = heap[0]
        heap[0] = heap[size - 1]
        size--
        maxHeapify(0)
        return max
    }



    private fun swap(firstIndex: Int, secondIndex: Int) {
        val temp = heap[firstIndex]
        heap[firstIndex] = heap[secondIndex]
        heap[secondIndex] = temp
    }

}