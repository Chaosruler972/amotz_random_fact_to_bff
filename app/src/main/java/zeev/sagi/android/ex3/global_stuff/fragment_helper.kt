@file:Suppress("PackageName", "unused", "MemberVisibilityCanPrivate")

package zeev.sagi.android.ex3.global_stuff

import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import zeev.sagi.android.ex3.R
import java.util.*
import android.provider.ContactsContract.CommonDataKinds




@Suppress("ClassName", "FunctionName")
object fragment_helper
{
    /*
    get view by id, for array adapter usage
 */

    fun get_view_by_id(convertView: View, id: Int): View = convertView.findViewById(id) // grabs the correpsonding view by id from layout

    /*
           checks single premission
        */
    @Suppress("FunctionName")
    fun check_premission(name:String,context: Context):Boolean =  ContextCompat.checkSelfPermission(context,name) == PackageManager.PERMISSION_GRANTED


    /*
    check all premissions
     */
    @Suppress("FunctionName")
    fun check_premissions(premission_array:Vector<String>,context: Context):Boolean
    {
        premission_array.forEach {
            if(!check_premission(it,context))
                return false
        }
        return true
    }

    /*
      requests entire premission
   */
    @Suppress("FunctionName", "unused")
    fun request_premissions(act: AppCompatActivity, premission_array:Vector<String>)=  ActivityCompat.requestPermissions(act, premission_array.toTypedArray(), act.resources.getInteger(R.integer.premission_request_code))


    fun get_contact_by_id(id:String,context: Context): ArrayList<String> {
        val phones = ArrayList<String>()

        val cursor = context.contentResolver.query(
                CommonDataKinds.Phone.CONTENT_URI, null,
                CommonDataKinds.Phone.NUMBER + " = ?",
                arrayOf(id), null)

        while (cursor.moveToNext()) {
            phones.add(cursor.getString(cursor.getColumnIndex(CommonDataKinds.Phone.PHOTO_URI)))
        }

        cursor.close()
        return phones
    }
}