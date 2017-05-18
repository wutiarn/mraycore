package ru.mray.core.service

import com.google.common.net.HostAndPort
import com.orbitz.consul.Consul
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.env.Environment
import org.springframework.stereotype.Service
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

@Service
class ConfigService(val environment: Environment) {

    final val consul: Consul? = consulLet@ let {
        val host = environment.getProperty("mray.consul.host", String::class.java)

        if (host == null) {
            logger.info("mray.consul.host is missing. Using envs")
            return@let null
        }

        return@let Consul.builder()
                .withHostAndPort(HostAndPort.fromParts(host, 8500))
                .build()
    }

    final val logger: Logger = LoggerFactory.getLogger(MailService::class.java)


    var registrationEnabled: Boolean by BooleanConsulProperty("mray.registration", false, Boolean::class, consul, environment)

    class BooleanConsulProperty<T : Any>(val name: String, val defaultValue: T, val type: KClass<T>, val consul: Consul?, val environment: Environment) {
        operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
            val result = environment.getProperty(name, type.java)
            return result ?: defaultValue
        }

        operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {

        }
    }
}