package de.rohitmisra.cloudflareddns.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.squareup.okhttp.OkHttpClient
import de.rohitmisra.cloudflareddns.handler.DnsRecordHandler
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.support.BeanDefinitionDsl
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.beans
import org.springframework.web.servlet.function.router

class BeansConfig : ApplicationContextInitializer<GenericApplicationContext> {
    override fun initialize(context: GenericApplicationContext) =
        beans.initialize(context)
}

val beans = beans {
    bean {
        OkHttpClient()
    }
    bean {
        ObjectMapper().registerModule(KotlinModule())
            .registerModule(JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    }

    bean<DnsRecordHandler>(
        name = "dnsRecordHandler",
        scope = BeanDefinitionDsl.Scope.SINGLETON,
        isLazyInit = true,
        isPrimary = true,
        isAutowireCandidate = true,
        initMethodName = "",
        destroyMethodName = "",
        description = "description",
        role = BeanDefinitionDsl.Role.APPLICATION
    )
}