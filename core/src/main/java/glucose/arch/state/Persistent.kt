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

    fun boolean(default: Boolean = false) = StatePropertyLoader(::StateProperty, Bundlers.Boolean, default)
    fun int(default: Int = 0) = StatePropertyLoader(::StateProperty, Bundlers.Int, default)
    fun long(default: Long = 0) = StatePropertyLoader(::StateProperty, Bundlers.Long, default)
    fun float(default: Float = 0.0f) = StatePropertyLoader(::StateProperty, Bundlers.Float, default)
    fun double(default: Double = 0.0) = StatePropertyLoader(::StateProperty, Bundlers.Double, default)
    fun string(default: String = "") = StatePropertyLoader(::StateProperty, Bundlers.String, default)
    fun bundle(default: Bundle = Bundle()) = StatePropertyLoader(::StateProperty, Bundlers.Bundle, default)

    fun <P: Parcelable> parcelable(default: P)
            = StatePropertyLoader(::StateProperty, Bundlers.parcelable(), default)

    fun <P: Parcelable> parcelableArrayList(default: ArrayList<P>)
            = StatePropertyLoader<ArrayList<P>>(::StateProperty, Bundlers.parcelableArrayList(), default)

    fun <P: Parcelable> parcelableSparseArray(default: SparseArray<P>)
            = StatePropertyLoader<SparseArray<P>>(::StateProperty, Bundlers.parcelableSparseArray(), default)

    fun <S: Serializable> serializable(default: S, clazz: Class<S>)
            = StatePropertyLoader<S>(::StateProperty, Bundlers.serializable(clazz), default)

}