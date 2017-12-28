@file:Suppress("unused", "ClassName", "RemoveEmptyClassBody")

package zeev.sagi.android.ex3

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import zeev.sagi.android.ex3.data_structure_max_heap.BinaryHeap
import zeev.sagi.android.ex3.dataclass.contact
import zeev.sagi.android.ex3.global_stuff.fragment_helper


@Suppress("LocalVariableName")
class bff_finder {
    @SuppressLint("MissingPermission")
    @Suppress("FunctionName")
    fun compute_bff(context: Context):contact?
    {
        @Suppress("CanBeVal")
        var return_value:contact? = null
        if(!fragment_helper.check_premission(Manifest.permission.READ_CALL_LOG,context)
        ||
           !fragment_helper.check_premission(Manifest.permission.READ_CONTACTS,context))
        {
            Toast.makeText(context,context.getString(R.string.insufficnet_premissions),Toast.LENGTH_SHORT).show()
            return return_value
        }


        val cursor = context.contentResolver.query(
                android.provider.CallLog.Calls.CONTENT_URI, null, null, null, null)
        val heap = BinaryHeap(cursor.count)
        while(cursor.moveToNext())
        {
            val number_column = cursor.getColumnIndex(android.provider.CallLog.Calls.NUMBER)
            val name_column = cursor.getColumnIndex(android.provider.CallLog.Calls.CACHED_NAME)
            //val calls_column = cursor.getColumnIndex(android.provider.CallLog.Calls._COUNT)

            val cont = contact(
                    cursor.getString(number_column),
                    cursor.getString(name_column),
                    1
            )
            heap.insert(cont)
        }
        cursor.close()
        return_value = heap.findMax()
        return return_value
    }
}