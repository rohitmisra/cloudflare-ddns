package de.rohitmisra.cloudflareddns.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.squareup.okhttp.OkHttpClient
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.beans

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
    }
}