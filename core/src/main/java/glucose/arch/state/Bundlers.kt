package glucose.arch.state

import android.os.Bundle
import android.os.Parcelable
import android.util.SparseArray

internal object Bundlers {

    // TODO: Add remaining bundlers.
    // Byte, Char, Short, BooleanArray, ByteArray, CharArray, ShortArray, IntArray, LongArray
    // FloatArray, DoubleArray, IntArrayList, Array<parcelable>, Serializable, CharSequence
    // ArrayList<CharSequence>, ArrayList<String>, Array<CharSequence>, Array<String>

    val Boolean = object : Bundler<Boolean> {
        override fun Bundle.putValue(key: String, value: Boolean) = putBoolean(key, value)
        override fun Bundle.getValue(key: String, default: Boolean): Boolean = getBoolean(key, default)
    }

    val Int = object : Bundler<Int> {
        override fun Bundle.putValue(key: String, value: Int) = putInt(key, value)
        override fun Bundle.getValue(key: String, default: Int): Int = getInt(key, default)
    }

    val Long = object : Bundler<Long> {
        override fun Bundle.putValue(key: String, value: Long) = putLong(key, value)
        override fun Bundle.getValue(key: String, default: Long): Long = getLong(key, default)
    }

    val Float = object : Bundler<Float> {
        override fun Bundle.putValue(key: String, value: Float) = putFloat(key, value)
        override fun Bundle.getValue(key: String, default: Float): Float = getFloat(key, default)
    }

    val Double = object : Bundler<Double> {
        override fun Bundle.putValue(key: String, value: Double) = putDouble(key, value)
        override fun Bundle.getValue(key: String, default: Double): Double = getDouble(key, default)
    }

    val String = object : Bundler<String> {
        override fun Bundle.putValue(key: String, value: String) = putString(key, value)
        override fun Bundle.getValue(key: String, default: String): String = getString(key, default)
    }

    val Bundle = object : Bundler<Bundle> {
        override fun Bundle.putValue(key: String, value: Bundle) = putBundle(key, value)
        override fun Bundle.getValue(key: String, default: Bundle): Bundle = getBundle(key) ?: default
    }

    fun <P: Parcelable> parcelable() = object : Bundler<P> {
        override fun Bundle.putValue(key: String, value: P) = putParcelable(key, value)
        override fun Bundle.getValue(key: String, default: P): P = getParcelable(key) ?: default
    }

    fun <P: Parcelable> parcelableArrayList() = object : Bundler<ArrayList<P>> {
        override fun Bundle.putValue(key: String, value: ArrayList<P>) = putParcelableArrayList(key, value)
        override fun Bundle.getValue(key: String, default: ArrayList<P>): ArrayList<P> = getParcelableArrayList(key) ?: default
    }

    fun <P: Parcelable> parcelableSparseArray() = object : Bundler<SparseArray<P>> {
        override fun Bundle.putValue(key: String, value: SparseArray<P>) = putSparseParcelableArray(key, value)
        override fun Bundle.getValue(key: String, default: SparseArray<P>): SparseArray<P> = getSparseParcelableArray(key) ?: default
    }

}