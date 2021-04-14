package com.rameshkp.openapi.merger.app.models

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.ints.shouldBeExactly
import io.kotest.matchers.shouldBe
import javax.validation.Validation


internal class ServerTest: BehaviorSpec({
    val validator = Validation.buildDefaultValidatorFactory().validator

    given("A server object") {
        val server = Server()
        `when`("url is null") {
            then("constraint validation should fail") {
                val constraints = validator.validate(server)
                constraints.size shouldBeExactly 1
                constraints.first().message shouldBe "server url cannot be blank"
            }
        }
        `when`("description is null and url is not null") {
            then("constraint validation should pass") {
                server.url = "http://localhost:8080"
                val constraints = validator.validate(server)
                constraints.size shouldBeExactly 0
            }
        }
    }
})