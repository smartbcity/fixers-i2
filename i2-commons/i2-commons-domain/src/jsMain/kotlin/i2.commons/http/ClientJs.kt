package i2.commons.http

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise
import kotlin.js.Promise

@JsExport
@JsName("ClientJs")
abstract class ClientJs{

    protected fun <T> doCall(fnc: suspend () -> Any): Promise<T> = GlobalScope.promise {
        val result = fnc()
        JSON.parse(JSON.stringify(result))
    }
}
