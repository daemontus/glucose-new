package glucose.arch.state

import android.os.Bundle

/**
 * A simple abstraction over putting/obtaining data from bundles, since the Bundle interface
 * is a bit tricky...
 */
interface Bundler<T: Any> {
    fun Bundle.putValue(key: String, value: T)
    fun Bundle.getValue(key: String, default: T): T
}