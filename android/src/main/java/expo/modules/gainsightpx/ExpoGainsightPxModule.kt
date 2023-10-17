package expo.modules.gainsightpx

import com.gainsight.px.mobile.GainsightPX
import com.gainsight.px.mobile.LogLevel
import expo.modules.kotlin.modules.Module
import expo.modules.kotlin.modules.ModuleDefinition
import expo.modules.kotlin.records.Field
import expo.modules.kotlin.records.Record
import expo.modules.kotlin.types.Enumerable
import java.util.concurrent.TimeUnit

enum class CallbackStatus(val value: Int) : Enumerable {
    FAILURE(0),
    SUCCESS(1)
}

data class Callback(
    @Field
    val status: CallbackStatus = CallbackStatus.SUCCESS,
    @Field
    val methodName: String? = null,
    @Field
    val params: Any? = null,
    @Field
    val exceptionMessage: String? = null
) : Record

enum class PXHost(val value: String) : Enumerable {
    US("us"),
    EU("eu"),
    US2("us2")
}

data class Configuration(
    @Field
    val apiKey: String = "",
    @Field
    val flushQueueSize: Int? = null,
    @Field
    val maxQueueSize: Int? = null,
    @Field
    val flushIntervalInSeconds: Int? = null,
    @Field
    val enableLogs: Boolean? = null,
    @Field
    val trackApplicationLifeCycleEvents: Boolean? = null,
    @Field
    val shouldTrackTapEvents: Boolean? = null,
    @Field
    val enable: Boolean? = null,
    @Field
    val proxy: String? = null,
    @Field
    val host: PXHost? = null,
    @Field
    val reportTrackingIssues: Boolean? = null,
    @Field
    val androidIsTrackTapInAllLayouts: Boolean? = null,
    @Field
    val androidCollectDeviceId: Boolean? = null
) : Record

class ExpoGainsightPxModule : Module() {
    override fun definition() = ModuleDefinition {
        Name("ExpoGainsightPx")

        Function("startInstance") { configuration: Configuration ->
            try {
                val configBuilder = GainsightPX
                    .Builder(appContext.reactContext?.applicationContext, configuration.apiKey)
                configuration.flushQueueSize?.let { value ->
                    configBuilder.flushQueueSize(value)
                }
                configuration.maxQueueSize?.let { value ->
                    configBuilder.maxQueueSize(value)
                }
                configuration.flushIntervalInSeconds?.let { value ->
                    configBuilder.flushInterval(value.toLong(), TimeUnit.SECONDS)
                }
                configuration.enableLogs?.let { value ->
                    if (value) {
                        configBuilder.logLevel(LogLevel.DEBUG)
                    } else {
                        configBuilder.logLevel(LogLevel.NONE)
                    }
                }
                configuration.trackApplicationLifeCycleEvents?.let { value ->
                    configBuilder.trackApplicationLifecycleEvents(value)
                }
                configuration.shouldTrackTapEvents?.let { value ->
                    configBuilder.shouldTrackTapEvents(value)
                }
                configuration.proxy?.let { value ->
                    configBuilder.proxy(value)
                }
                configuration.host?.let { value ->
                    configBuilder.pxHost(value.value.lowercase())
                }
                configuration.reportTrackingIssues?.let { value ->
                    configBuilder.shouldReportIssuesToServer(value)
                }
                configuration.androidIsTrackTapInAllLayouts?.let { value ->
                    configBuilder.isTrackTapInAllLayouts(value)
                }
                configuration.androidCollectDeviceId?.let { value ->
                    configBuilder.collectDeviceId(value)
                }
                GainsightPX.setSingletonInstance(configBuilder.build())
                configuration.enable?.let { value ->
                    GainsightPX.with().setEnable(value)
                }
                return@Function responseSuccess("startInstance", configuration)
            } catch (ex: Throwable) {
                return@Function responseFailure("startInstance", configuration, ex.message)
            }
        }

        Function("identify") { userId: String ->
            GainsightPX.with().identify(userId)
        }

        Function("custom") { eventName: String ->
            GainsightPX.with().custom(eventName)
        }
    }

    private fun responseFailure(methodName: String, parameters: Any?, message: String?): Callback {
        return Callback(
            status = CallbackStatus.FAILURE,
            methodName = methodName,
            params = parameters,
            exceptionMessage = message
        )
    }

    private fun responseSuccess(methodName: String, parameters: Any?): Callback {
        return Callback(
            status = CallbackStatus.SUCCESS,
            methodName = methodName,
            params = parameters
        )
    }
}
