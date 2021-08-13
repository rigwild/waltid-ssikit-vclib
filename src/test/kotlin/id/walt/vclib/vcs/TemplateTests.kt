package id.walt.vclib.vcs

import id.walt.vclib.templates.VcTemplateManager
import id.walt.vclib.vclist.VerifiableAuthorization
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class TemplateTests : StringSpec({

    val europassTemplateType = listOf("VerifiableCredential", "VerifiableAttestation", "Europass")

    "default definitions registered" {
        println("Loading Europass by type")
        val europass = VcTemplateManager.loadTemplate(europassTemplateType)

        europass.type shouldBe europassTemplateType
    }

    "test aliases loading by type" {
        val vaMetadata = VerifiableAuthorization.Companion
        val primaryType = vaMetadata.type
        val aliasType = vaMetadata.aliases.first()

        val template1 = VcTemplateManager.loadTemplate(primaryType)
        template1.type shouldBe primaryType

        val template2 = VcTemplateManager.loadTemplate(aliasType)
        template2.type shouldBe primaryType
    }

    "loading default definitions by name" {
        println("Loading Europass by name")
        val europass = VcTemplateManager.loadTemplate("Europass")

        europass.type shouldBe europassTemplateType
    }

    "test aliases loading by name" {
        val vaMetadata = VerifiableAuthorization.Companion
        val primaryType = vaMetadata.type
        val aliasType = vaMetadata.aliases.first()

        val template1 = VcTemplateManager.loadTemplate("VerifiableAuthorization")
        template1.type shouldNotBe aliasType
        template1.type shouldBe primaryType
    }
})