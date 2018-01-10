package glucose.arch.state

import android.os.Bundle
import android.support.test.runner.AndroidJUnit4
import glucose.arch.LifecycleException
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@RunWith(AndroidJUnit4::class)
class StateOwnerDelegateTest {

    @Test
    fun lifecycleTest() {
        val delegate = StateOwnerDelegate("test")

        assertFalse(delegate.hasState)
        assertFailsWith<LifecycleException> {
            delegate.writeToBundle(Bundle())
        }

        val state = object : State {
            var value: String? = null
            override fun readFromBundle(data: Bundle) {
                value = data.getString("key")
            }
            override fun writeToBundle(data: Bundle) {
                data.putString("key", value)
            }
        }

        delegate.registerState(state)

        delegate.initFromBundle(Bundle().apply { putString("key", "value") })

        assertTrue(delegate.hasState)
        assertFailsWith<LifecycleException> {
            delegate.readFromBundle(Bundle())
        }

        assertFailsWith<LifecycleException> {
            delegate.registerState(object : State {
                override fun readFromBundle(data: Bundle) = error("Should not be called")
                override fun writeToBundle(data: Bundle) = error("Should not be called")
            })
        }

        assertEquals("value", state.value)

        val output = delegate.saveToBundle()

        assertEquals("value", output.getString("key"))
    }

}