package id.walt.vclib.credentials

import com.beust.klaxon.Json
import id.walt.vclib.model.*
import id.walt.vclib.registry.VerifiableCredentialMetadata
import id.walt.vclib.schema.SchemaService

data class InternationalBankAccountNumber(
    @Json(name = "@context") @field:SchemaService.PropertyName(name = "@context")
    var context: List<String> = listOf("https://www.w3.org/2018/credentials/v1"),
    @Json(serializeNull = false) override var id: String? = null,
    @Json(serializeNull = false) override var issued: String? = null,
    @Json(serializeNull = false) override var validFrom: String? = null,
    @Json(serializeNull = false) override var expirationDate: String? = null,
    @Json(serializeNull = false) override var credentialSubject: InternationalBankAccountNumberSubject? = null,
    @Json(serializeNull = false) override var issuer: String? = null,
    @Json(serializeNull = false) override var proof: Proof? = null,
    override var credentialSchema: CredentialSchema? = null,
) : AbstractVerifiableCredential<InternationalBankAccountNumber.InternationalBankAccountNumberSubject>(type) {
    data class InternationalBankAccountNumberSubject(
        @Json(serializeNull = false) override var id: String? = null,
        @Json(serializeNull = false) var accountNumber: String? = null,
        @Json(serializeNull = false) var bankIdentifier: String? = null,
        @Json(serializeNull = false) var bban: String? = null,
        @Json(serializeNull = false) var iban: String? = null,
    ) : CredentialSubject()

    companion object : VerifiableCredentialMetadata(
        type = listOf("VerifiableCredential", "InternationalBankAccountNumber"),
        template = {
            InternationalBankAccountNumber(
                id = "iban#3add94f4-28ec-42a1-8704-4e4aa51006b4",
                issuer = "did:example:456",
                issued = "2021-08-31T00:00:00Z",
                validFrom = "2021-08-31T00:00:00Z",
                credentialSubject = InternationalBankAccountNumberSubject(
                    id = "id123",
                    accountNumber = "12345678901",
                    bankIdentifier = "10011",
                    bban = "10011000201234567890188",
                    iban = "FR7610011000201234567890188",
                ),
                credentialSchema = CredentialSchema(
                    id = "https://raw.githubusercontent.com/rigwild/waltid-ssikit-vclib/master/src/test/resources/schemas/InternationalBankAccountNumber.json",
                    type = "JsonSchemaValidator2018"
                ),
            )
        }
    )

    override fun newId(id: String) = "iban#${id}"
}
