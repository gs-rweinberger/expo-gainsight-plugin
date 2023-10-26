package expo.modules.gainsightpx

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.core.os.bundleOf
import com.gainsight.px.mobile.EngagementMetaData
import com.gainsight.px.mobile.GainsightPX
import com.gainsight.px.mobile.LogLevel
import com.gainsight.px.mobile.ScreenEventData
import com.gainsight.px.mobile.ValueMap
import expo.modules.kotlin.modules.Module
import expo.modules.kotlin.modules.ModuleDefinition
import expo.modules.kotlin.records.Field
import expo.modules.kotlin.records.Record
import expo.modules.kotlin.types.Enumerable
import java.util.Date
import java.util.concurrent.TimeUnit

enum class Status(val value: Int) : Enumerable {
    FAILURE(0),
    SUCCESS(1)
}

data class Response(
    @Field
    val status: Status = Status.SUCCESS,
    @Field
    val methodName: String? = null,
    @Field
    val params: Any? = null,
    @Field
    val exceptionMessage: String? = null
) : Record

@Suppress("unused")
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
) : Record {
    fun toNativeConfiguration(applicationContext: Context?, exceptionHandler: GainsightPX.ExceptionHandler): GainsightPX.Builder {
        val configBuilder = GainsightPX
            .Builder(applicationContext, this.apiKey, exceptionHandler)
        this.flushQueueSize?.let { value ->
            configBuilder.flushQueueSize(value)
        }
        this.maxQueueSize?.let { value ->
            configBuilder.maxQueueSize(value)
        }
        this.flushIntervalInSeconds?.let { value ->
            configBuilder.flushInterval(value.toLong(), TimeUnit.SECONDS)
        }
        this.enableLogs?.let { value ->
            if (value) {
                configBuilder.logLevel(LogLevel.VERBOSE)
            } else {
                configBuilder.logLevel(LogLevel.NONE)
            }
        }
        this.trackApplicationLifeCycleEvents?.let { value ->
            configBuilder.trackApplicationLifecycleEvents(value)
        }
        this.shouldTrackTapEvents?.let { value ->
            configBuilder.shouldTrackTapEvents(value)
        }
        this.proxy?.let { value ->
            configBuilder.proxy(value)
        }
        this.host?.let { value ->
            configBuilder.pxHost(value.value.lowercase())
        }
        this.reportTrackingIssues?.let { value ->
            configBuilder.shouldReportIssuesToServer(value)
        }
        this.androidIsTrackTapInAllLayouts?.let { value ->
            configBuilder.isTrackTapInAllLayouts(value)
        }
        this.androidCollectDeviceId?.let { value ->
            configBuilder.collectDeviceId(value)
        }
        return configBuilder
    }
}

@Suppress("unused")
enum class Gender(val value: String): Enumerable {
    NOT_SET("Not Set"),
    MALE("Male"),
    FEMALE("Female"),
    OTHER("Other")
}

data class User(
    @Field
    val id: String,
    @Field
    val email: String?,
    @Field
    val userHash: String?,
    @Field
    val gender: Gender?,
    @Field
    val lastName: String?,
    @Field
    val firstName: String?,
    @Field
    val signUpDate: Any?,
    @Field
    val title: String?,
    @Field
    val role: String?,
    @Field
    val subscriptionId: String?,
    @Field
    val phone: String?,
    @Field
    val organization: String?,
    @Field
    val organizationEmployees: String?,
    @Field
    val organizationRevenue: String?,
    @Field
    val organizationIndustry: String?,
    @Field
    val organizationSicCode: Int?,
    @Field
    val organizationDuns: Long?,
    @Field
    val accountId: String?,
    @Field
    val firstVisitDate: Any?,
    @Field
    val score: Any?,
    @Field
    val sfdcContactId: String?,
    @Field
    val customAttributes: Map<String, Any>?,
    @Field
    val countryCode: String?,
    @Field
    val countryName: String?,
    @Field
    val stateCode: String?,
    @Field
    val stateName: String?,
    @Field
    val city: String?,
    @Field
    val street: String?,
    @Field
    val continent: String?,
    @Field
    val postalCode: String?,
    @Field
    val regionName: String?,
    @Field
    val timeZone: String?,
    @Field
    val latitude: Double?,
    @Field
    val longitude: Double?
) : Record {
        fun toNativeUser() : com.gainsight.px.mobile.User {
            val gainsightUser = com.gainsight.px.mobile.User(this.id)
            this.email?.let { email ->
                gainsightUser.putEmail(email)
            }
            this.userHash?.let { hash ->
                gainsightUser.putIdentifyIdHash(hash)
            }
            this.gender?.let { gender ->
                when (gender) {
                    Gender.NOT_SET -> gainsightUser.putGender(com.gainsight.px.mobile.User.Gender.NOT_SET)
                    Gender.MALE -> gainsightUser.putGender(com.gainsight.px.mobile.User.Gender.MALE)
                    Gender.FEMALE -> gainsightUser.putGender(com.gainsight.px.mobile.User.Gender.FEMALE)
                    Gender.OTHER -> gainsightUser.putGender(com.gainsight.px.mobile.User.Gender.OTHER)
                }
            }
            this.lastName?.let { lastName ->
                gainsightUser.putLastName(lastName)
            }
            this.firstName?.let { firstName ->
                gainsightUser.putFirstName(firstName)
            }
            this.signUpDate?.let { signUpDate ->
                when (signUpDate) {
                    is Double -> gainsightUser.putSignUpDate(signUpDate.toLong())
                    is Long -> gainsightUser.putSignUpDate(signUpDate)
                    is String -> gainsightUser.putSignUpDate(signUpDate)
                    is Date -> gainsightUser.putSignUpDate(signUpDate)
                    else -> { /*Invalid value type*/}
                }
            }
            this.title?.let { title ->
                gainsightUser.putTitle(title)
            }
            this.role?.let { role ->
                gainsightUser.putRole(role)
            }
            this.subscriptionId?.let { subscriptionId ->
                gainsightUser.putSubscriptionId(subscriptionId)
            }
            this.phone?.let { phone ->
                gainsightUser.putPhone(phone)
            }
            this.organization?.let { organization ->
                gainsightUser.putOrganizationName(organization)
            }
            this.organizationEmployees?.let { organizationEmployees ->
                gainsightUser.putOrganizationEmployees(organizationEmployees)
            }
            this.organizationRevenue?.let { organizationRevenue ->
                gainsightUser.putOrganizationRevenue(organizationRevenue)
            }
            this.organizationIndustry?.let { organizationIndustry ->
                gainsightUser.putOrganizationIndustry(organizationIndustry)
            }
            this.organizationSicCode?.let { organizationSicCode ->
                gainsightUser.putOrganizationSicCode(organizationSicCode.toString())
            }
            this.organizationDuns?.let { organizationDuns ->
                gainsightUser.putOrganizationDuns(organizationDuns)
            }
            this.accountId?.let { accountId ->
                gainsightUser.putAccountId(accountId)
            }
            this.firstVisitDate?.let { firstVisitDate ->
                when (firstVisitDate) {
                    is Double -> gainsightUser.putFirstVisitDate(firstVisitDate.toLong())
                    is Long -> gainsightUser.putFirstVisitDate(firstVisitDate)
                    is String -> gainsightUser.putFirstVisitDate(firstVisitDate)
                    is Date -> gainsightUser.putFirstVisitDate(firstVisitDate)
                    else -> { /*Invalid value type*/}
                }
            }
            this.score?.let { score ->
                when (score) {
                    is Int -> gainsightUser.putScore(score)
                    is Double -> gainsightUser.putScore(score.toInt())
                    else -> { /*Invalid value type*/}
                }
            }
            this.sfdcContactId?.let { sfdcContactId ->
                gainsightUser.putSfdcContactId(sfdcContactId)
            }
            this.customAttributes?.let { customAttributes ->
                gainsightUser.putCustomAttributes(customAttributes)
            }
            this.countryCode?.let { countryCode ->
                gainsightUser.putCountryCode(countryCode)
            }
            this.countryName?.let { countryName ->
                gainsightUser.putCountryName(countryName)
            }
            this.stateCode?.let { stateCode ->
                gainsightUser.putStateCode(stateCode)
            }
            this.stateName?.let { stateName ->
                gainsightUser.putStateName(stateName)
            }
            this.city?.let { city ->
                gainsightUser.putCity(city)
            }
            this.street?.let { street ->
                gainsightUser.putStreet(street)
            }
            this.continent?.let { continent ->
                gainsightUser.putContinentCode(continent)
            }
            this.postalCode?.let { postalCode ->
                gainsightUser.putPostalCode(postalCode)
            }
            this.regionName?.let { regionName ->
                gainsightUser.putRegionName(regionName)
            }
            this.timeZone?.let { timeZone ->
                gainsightUser.putTimeZone(timeZone)
            }
            this.latitude?.let { latitude ->
                gainsightUser.putLatitude(latitude)
            }
            this.longitude?.let { longitude ->
                gainsightUser.putLongitude(longitude)
            }
            return gainsightUser
        }
}

data class Account(
    @Field
    val id: String,
    @Field
    val name: String?,
    @Field
    val trackedSubscriptionId: String?,
    @Field
    val industry: String?,
    @Field
    val numberOfEmployees: Any?,
    @Field
    val sicCode: Int?,
    @Field
    val website: String?,
    @Suppress("SpellCheckingInspection", "SpellCheckingInspection") @Field
    val naicsCode: String?,
    @Field
    val plan: String?,
    @Field
    val sfdcId: String?,
    @Field
    val customAttributes: Map<String, Any>?,
    @Field
    val countryCode: String?,
    @Field
    val countryName: String?,
    @Field
    val stateCode: String?,
    @Field
    val stateName: String?,
    @Field
    val city: String?,
    @Field
    val street: String?,
    @Field
    val continent: String?,
    @Field
    val postalCode: String?,
    @Field
    val regionName: String?,
    @Field
    val timeZone: String?,
    @Field
    val latitude: Double?,
    @Field
    val longitude: Double?
) : Record {
    fun toNativeAccount() : com.gainsight.px.mobile.Account {
        val gainsightAccount = com.gainsight.px.mobile.Account(this.id)
        this.name?.let { name ->
            gainsightAccount.putName(name)
        }
        this.trackedSubscriptionId?.let { trackedSubscriptionId ->
            gainsightAccount.putSubscriptionId(trackedSubscriptionId)
        }
        this.industry?.let { industry ->
            gainsightAccount.putIndustry(industry)
        }
        this.numberOfEmployees?.let { numberOfEmployees ->
            when (numberOfEmployees) {
                is Long -> gainsightAccount.putEmployees(numberOfEmployees)
                is Int -> gainsightAccount.putEmployees(numberOfEmployees.toLong())
                is Double -> gainsightAccount.putEmployees(numberOfEmployees.toLong())
                else -> { /*Invalid value type*/}
            }
        }
        this.sicCode?.let { sicCode ->
            gainsightAccount.putSicCode(sicCode.toString())
        }
        this.website?.let { website ->
            gainsightAccount.putWebsite(website)
        }
        this.naicsCode?.let { code ->
            gainsightAccount.putNaicsCode(code)
        }
        this.plan?.let { plan ->
            gainsightAccount.putPlan(plan)
        }
        this.sfdcId?.let { sfdcId ->
            gainsightAccount.putSfdcContactId(sfdcId)
        }
        this.customAttributes?.let { customAttributes ->
            gainsightAccount.putCustomAttributes(customAttributes)
        }
        this.countryCode?.let { countryCode ->
            gainsightAccount.putCountryCode(countryCode)
        }
        this.countryName?.let { countryName ->
            gainsightAccount.putCountryName(countryName)
        }
        this.stateCode?.let { stateCode ->
            gainsightAccount.putStateCode(stateCode)
        }
        this.stateName?.let { stateName ->
            gainsightAccount.putStateName(stateName)
        }
        this.city?.let { city ->
            gainsightAccount.putCity(city)
        }
        this.street?.let { street ->
            gainsightAccount.putStreet(street)
        }
        this.continent?.let { continent ->
            gainsightAccount.putContinent(continent)
        }
        this.postalCode?.let { postalCode ->
            gainsightAccount.putPostalCode(postalCode)
        }
        this.regionName?.let { regionName ->
            gainsightAccount.putRegionName(regionName)
        }
        this.timeZone?.let { timeZone ->
            gainsightAccount.putTimeZone(timeZone)
        }
        this.latitude?.let { latitude ->
            gainsightAccount.putLatitude(latitude)
        }
        this.longitude?.let { longitude ->
            gainsightAccount.putLongitude(longitude)
        }
        return gainsightAccount
    }
}

const val kOnEngagementEvent = "onEngagementEvent"
const val kOnExceptionThrown = "onExceptionThrown"

class EngagementsListener(val cb: (name: String, body: Bundle?) -> Unit): GainsightPX.EngagementCallback {
    override fun onCallback(metaData: EngagementMetaData?): Boolean {
        metaData?.let {
            this.cb(kOnEngagementEvent, bundleOf("engagementId" to metaData.engagementId,
                "engagementName" to metaData.engagementName,
                "screenName" to metaData.scope?.screenName(),
                "screenClass" to metaData.scope?.screenClass(),
                "actionText" to metaData.actionText,
                "actionData" to metaData.actionData,
                "actionType" to metaData.actionType,
                "params" to metaData.params)
            )
            return true
        }
        return false
    }
}

class ExpoGainsightPxModule : Module() {

    private val engagementListener: EngagementsListener = EngagementsListener(::sendEvent)
    private var engagementEnabled: Boolean = false

    override fun definition() = ModuleDefinition {
        Name("ExpoGainsightPx")

        Events(kOnEngagementEvent, kOnExceptionThrown)

        Property("enabled")
            .get {
                return@get GainsightPX.with().enabled()
            }
            .set { newValue: Boolean ->
                GainsightPX.with().setEnable(newValue)
            }

        Property("engagementEnable")
            .get {
                return@get engagementEnabled
            }
            .set { newValue: Boolean ->
                GainsightPX.with().enableEngagements(newValue)
                engagementEnabled = newValue
            }

        fun Map<String, Any>.toBundle(): Bundle {
            val args = Bundle()
            this.let { map ->
                if (map.isNotEmpty()) {
                    map.forEach { (key, value) ->
                        when (value) {
                            is Int -> args.putInt(key, value)
                            is Long -> args.putLong(key, value)
                            is ValueMap -> args.putBundle(key, value.toBundle())
                            is String -> args.putString(key, value)
                            is Exception -> args.putString(key, value.message)
                            else -> Log.i("PXExpo","Need conversion: ${value.javaClass.name}")
                        }
                    }
                }
            }
            return args
        }

        val reportError = { method: String?, params: ValueMap?, message: String? ->
            Log.i("PXExpo", "$method-Error: message- $message, params- $params")
            val args = params?.toBundle()
            sendEvent(kOnExceptionThrown, bundleOf(
                "method" to method,
                "params" to args,
                "message" to message))
        }

        Function("startInstance") { configuration: Configuration ->
            try {
                val configBuilder = configuration.toNativeConfiguration(appContext.reactContext?.applicationContext, reportError)
                configBuilder.engagementCallback(engagementListener)
                val instance = configBuilder.build()
                GainsightPX.setSingletonInstance(instance)
                configuration.enable?.let { value ->
                    instance.setEnable(value)
                }
                return@Function responseSuccess("startInstance", configuration)
            } catch (ex: Throwable) {
                return@Function responseFailure("startInstance", configuration, ex.message)
            }
        }

        Function("identify") { user: User, account: Account? ->
            try {
                val gainsightAccount = account?.toNativeAccount()
                val gainsightUser = user.toNativeUser()
                GainsightPX.with().identify(gainsightUser, gainsightAccount, reportError)
                return@Function responseSuccess("identify", mapOf("user" to user, "account" to account))
            } catch (ex: Throwable) {
                return@Function responseFailure("identify", mapOf("user" to user, "account" to account), ex.message)
            }
        }

        Function("unidentify") {
            GainsightPX.with().reset()
            return@Function responseSuccess("unidentify", null)
        }

        Function("custom") { eventName: String, properties: Map<String, Any>? ->
            try {
                GainsightPX.with().custom(eventName, properties, reportError)
                return@Function responseSuccess("custom", mapOf("eventName" to eventName, "properties" to properties))
            } catch (ex: Throwable) {
                return@Function responseFailure("custom", mapOf("eventName" to eventName, "properties" to properties), ex.message)
            }
        }

        Function("screen") { screenName: String, screenClass: String?, properties: Map<String, Any>? ->
            try {
                val screenData = ScreenEventData(screenName)
                screenClass?.let { screenData.putScreenClass(it) }
                properties?.let { screenData.putProperties(it) }
                GainsightPX.with().screen(screenData, reportError)
                return@Function responseSuccess("screen", mapOf("screenName" to screenName, "screenClass" to screenClass, "properties" to properties))
            } catch (ex: Throwable) {
                return@Function responseFailure("screen", mapOf("screenName" to screenName, "screenClass" to screenClass, "properties" to properties), ex.message)
            }
        }

        Function("addGlobalContext") { key: String, value: Any ->
            try {
                GainsightPX.with().globalContext[key] = value
                return@Function responseSuccess("addGlobalContext", mapOf("key" to key, "value" to value))
            } catch (ex: Throwable) {
                return@Function responseFailure("addGlobalContext", mapOf("key" to key, "value" to value), ex.message)
            }
        }

        Function("hasGlobalContextKey") { key: String ->
            try {
                return@Function GainsightPX.with().globalContext?.hasKey(key) ?: false
            } catch (ex: Throwable) {
                return@Function false
            }
        }

        Function("removeGlobalContext") { key: String ->
            try {
                GainsightPX.with().globalContext?.removeKey(key)
                return@Function responseSuccess("removeGlobalContext", mapOf("key" to key))
            } catch (ex: Throwable) {
                return@Function responseFailure("removeGlobalContext", mapOf("key" to key), ex.message)
            }
        }

        Function("flush") {
            GainsightPX.with().flush()
            return@Function responseSuccess("flush", null)
        }

        Function("hardReset") {
            try {
                GainsightPX.with().shutdown()
                return@Function responseSuccess("hardReset", null)
            } catch (ex: Throwable) {
                return@Function responseFailure("hardReset", null, ex.message)
            }
        }

        Function("enterEditing") { deepLink: String? ->
            try {
                deepLink?.let {
                    val uri = Uri.parse(deepLink)
                    val intent = Intent()
                    intent.data = uri
                    GainsightPX.with().enterEditingMode(intent)
                }
                return@Function responseSuccess("enterEditing", mapOf("deepLink" to deepLink))
            } catch (ex: Throwable) {
                return@Function responseFailure("enterEditing", mapOf("deepLink" to deepLink), ex.message)

            }
        }

        Function("exitEditing") {
            try {
                GainsightPX.with().exitEditingMode()
                return@Function responseSuccess("exitEditing", null)
            } catch (ex: Throwable) {
                return@Function responseFailure("exitEditing", null, ex.message)
            }
        }
    }

    private fun responseFailure(methodName: String, parameters: Any?, message: String?): Response {
        return Response(
            status = Status.FAILURE,
            methodName = methodName,
            params = parameters,
            exceptionMessage = message
        )
    }

    private fun responseSuccess(methodName: String, parameters: Any?): Response {
        return Response(
            status = Status.SUCCESS,
            methodName = methodName,
            params = parameters
        )
    }
}

/*
type CallBackFn = (...args: any[]) => Promise<Response>;

async function runAsync(runnable: CallBackFn): Promise<Response> {
  return new Promise<Response>((resolve, reject) => {
    try {
    runnable().then(result => {
      if (result.status == Status.SUCCESS) {
        resolve(result);
      } else {
        reject(result);
      }
    }).catch(error => {
      const result: Response = {
        status: Status.FAILURE,
        methodName: error.userInfo.methodName,
        params: error.userInfo.params,
        exceptionMessage: error.message
      };
      reject(result);
      });
  } catch (error) {
    const result: Response = {
      status: Status.FAILURE,
      methodName: error.userInfo.methodName,
      params: error.userInfo.params,
      exceptionMessage: error.message
    };
    reject(result);
  }});
}

export async function startInstance(configuration: Configuration): Promise<Response> {
  return runAsync(() =>{
    return ExpoGainsightPxModule.startInstance(configuration);
  });
}
*/