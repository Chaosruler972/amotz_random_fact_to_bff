@file:Suppress("unused", "ClassName", "RemoveEmptyClassBody")

package zeev.sagi.android.ex3

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import android.util.Log
import android.widget.Toast
import zeev.sagi.android.ex3.data_structure_max_heap.MaxHeap
import zeev.sagi.android.ex3.dataclass.contact
import zeev.sagi.android.ex3.global_stuff.fragment_helper
import java.util.*


@Suppress("LocalVariableName")
class bff_finder {
    @SuppressLint("MissingPermission")
    @Suppress("FunctionName")
    fun compute_bff(context: Context):contact?
    {
        Looper.prepare()
        @Suppress("CanBeVal")
        var return_value:contact? = null
        if(!fragment_helper.check_premission(Manifest.permission.READ_CALL_LOG,context)
        ||
           !fragment_helper.check_premission(Manifest.permission.READ_CONTACTS,context))
        {
            Toast.makeText(context,context.getString(R.string.insufficnet_premissions),Toast.LENGTH_SHORT).show()
            return return_value
        }

        Log.d("Bff","Got pass the permission check stage")
        val cursor = context.contentResolver.query(
                android.provider.CallLog.Calls.CONTENT_URI, null, null, null, null)
        val vector:Vector<contact?> = Vector()
        vector.setSize(cursor.count)
        Log.d("Bff","Went through the SQLite stage, size is ${cursor.count}")
        val heap = MaxHeap(vector.toTypedArray())
        Log.d("Bff","Built heap")
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
            Log.d("Bff","Iterated again")
        }
        Log.d("Bff","Done")
        cursor.close()
        return_value = heap.findMax()
        return return_value
    }
}