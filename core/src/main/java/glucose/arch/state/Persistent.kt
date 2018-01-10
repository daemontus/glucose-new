package glucose.arch.state

import android.os.Bundle
import android.os.Parcelable
import android.util.SparseArray
import java.io.Serializable

/**
 * Delegate providers for various persistent state properties.
 */
object Persistent {

    val Boolean = boolean()
    val Int = int()
    val Long = long()
    val Float = float()
    val Double = double()
    val String = string()
    val Bundle = bundle()

    fun boolean(default: Boolean = false) = StatePropertyLoader(Bundlers.Boolean, default)
    fun int(default: Int = 0) = StatePropertyLoader(Bundlers.Int, default)
    fun long(default: Long = 0) = StatePropertyLoader(Bundlers.Long, default)
    fun float(default: Float = 0.0f) = StatePropertyLoader(Bundlers.Float, default)
    fun double(default: Double = 0.0) = StatePropertyLoader(Bundlers.Double, default)
    fun string(default: String = "") = StatePropertyLoader(Bundlers.String, default)
    fun bundle(default: Bundle = Bundle()) = StatePropertyLoader(Bundlers.Bundle, default)

    fun <P: Parcelable> parcelable(default: P)
            = StatePropertyLoader(Bundlers.parcelable(), default)

    fun <P: Parcelable> parcelableArrayList(default: ArrayList<P>)
            = StatePropertyLoader<ArrayList<P>>(Bundlers.parcelableArrayList(), default)

    fun <P: Parcelable> parcelableSparseArray(default: SparseArray<P>)
            = StatePropertyLoader<SparseArray<P>>(Bundlers.parcelableSparseArray(), default)

    fun <S: Serializable> serializable(default: S, clazz: Class<S>)
            = StatePropertyLoader(Bundlers.serializable(clazz), default)

}