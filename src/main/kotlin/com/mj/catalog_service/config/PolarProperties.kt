package com.mj.catalog_service.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties(prefix = "polar")
data class PolarProperties @ConstructorBinding constructor(
    /**
     * A message to welcome users.
     */
    val greeting: String
)