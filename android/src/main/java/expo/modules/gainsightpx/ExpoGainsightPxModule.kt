package expo.modules.gainsightpx

import com.gainsight.px.mobile.GainsightPX
import com.gainsight.px.mobile.LogLevel
import expo.modules.kotlin.modules.Module
import expo.modules.kotlin.modules.ModuleDefinition

class ExpoGainsightPxModule : Module() {
  override fun definition() = ModuleDefinition {
    Name("ExpoGainsightPx")

    Function("hello") {
      "Hello world! 👋"
    }

    Function("startInstance") { apiKey: String ->
      val config = GainsightPX
        .Builder(appContext.reactContext?.applicationContext, apiKey)
        .logLevel(LogLevel.DEBUG)
        .build()
      GainsightPX.setSingletonInstance(config)
    }

    Function("identify"){ userId: String ->
      GainsightPX.with().identify(userId)
    }

    Function("custom"){ eventName: String ->
      GainsightPX.with().custom(eventName)
    }
  }
}
