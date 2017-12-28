@file:Suppress("ClassName", "unused")

package zeev.sagi.android.ex3.dataclass


@Suppress("MemberVisibilityCanPrivate")
class contact(var phone_no:String, var name:String?, var calls:Int)
{
    operator fun inc():contact
    {
        this.calls++
        return this
    }

    fun equals(other: contact): Boolean = (this.phone_no == other.phone_no)

    operator fun compareTo(other: contact):Int
    {
        @Suppress("CascadeIf")
        return if(this.calls > other.calls)
            1
        else if(this.calls == other.calls)
            0
        else // case this.calls < other.calls
            -1
    }

    override fun toString(): String =
            "Name: ${name?:""}\n Number: $phone_no \n Calls: $calls"

}