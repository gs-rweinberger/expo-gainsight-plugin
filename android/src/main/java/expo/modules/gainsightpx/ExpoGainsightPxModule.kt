package expo.modules.gainsightpx

import android.content.Context
import com.gainsight.px.mobile.GainsightPX
import com.gainsight.px.mobile.LogLevel
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
    fun toNativeConfiguration(applicationContext: Context?): GainsightPX.Builder {
        val configBuilder = GainsightPX
            .Builder(applicationContext, this.apiKey)
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

data class User(
    @Field
    val ide: String,
    @Field
    val email: String?,
    @Suppress("SpellCheckingInspection") @Field
    val usem: String?,
    @Field
    val userHash: String?,
    @Field
    val gender: String?,
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
    val organizationSicCode: String?,
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
            val gainsightUser = com.gainsight.px.mobile.User(this.ide)
            this.email?.let { email ->
                gainsightUser.putEmail(email)
            }
            this.usem?.let { email ->
                gainsightUser.putEmail(email)
            }
            this.userHash?.let { hash ->
                gainsightUser.putIdentifyIdHash(hash)
            }
            this.gender?.let { gender ->
                gainsightUser.putGender(com.gainsight.px.mobile.User.Gender.getGender(gender.uppercase()))
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
                gainsightUser.putOrganizationSicCode(organizationSicCode)
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
    val sicCode: String?,
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
            gainsightAccount.putSicCode(sicCode)
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

class ExpoGainsightPxModule : Module() {
    override fun definition() = ModuleDefinition {
        Name("ExpoGainsightPx")

        Function("startInstance") { configuration: Configuration ->
            try {
                val configBuilder = configuration.toNativeConfiguration(appContext.reactContext?.applicationContext)
                GainsightPX.setSingletonInstance(configBuilder.build())
                configuration.enable?.let { value ->
                    GainsightPX.with().setEnable(value)
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
                GainsightPX.with().identify(gainsightUser, gainsightAccount)
                return@Function responseSuccess("identify", mapOf("user" to user, "account" to account))
            } catch (ex: Throwable) {
                return@Function responseFailure("identify", mapOf("user" to user, "account" to account), ex.message)
            }
        }

        Function("custom") { eventName: String ->
            GainsightPX.with().custom(eventName)
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
