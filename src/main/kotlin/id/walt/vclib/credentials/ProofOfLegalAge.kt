package id.walt.vclib.credentials

import com.beust.klaxon.Json
import id.walt.vclib.model.*
import id.walt.vclib.registry.VerifiableCredentialMetadata
import id.walt.vclib.schema.SchemaService

data class ProofOfLegalAge(
    @Json(name = "@context") @field:SchemaService.PropertyName(name = "@context")
    var context: List<String> = listOf("https://www.w3.org/2018/credentials/v1"),
    @Json(serializeNull = false) override var id: String? = null,
    @Json(serializeNull = false) override var issued: String? = null,
    @Json(serializeNull = false) override var validFrom: String? = null,
    @Json(serializeNull = false) override var expirationDate: String? = null,
    @Json(serializeNull = false) override var credentialSubject: ProofOfLegalAgeSubject? = null,
    @Json(serializeNull = false) override var issuer: String? = null,
    @Json(serializeNull = false) override var proof: Proof? = null,
    override var credentialSchema: CredentialSchema? = null,
) : AbstractVerifiableCredential<ProofOfLegalAge.ProofOfLegalAgeSubject>(type) {
    data class ProofOfLegalAgeSubject(
        @Json(serializeNull = false) override var id: String? = null,
        @Json(serializeNull = false) var countryLegalAge: Int? = null,
        @Json(serializeNull = false) var hasLegalAge: Boolean = false,
    ) : CredentialSubject()

    companion object : VerifiableCredentialMetadata(
        type = listOf("VerifiableCredential", "ProofOfLegalAge"),
        template = {
            ProofOfLegalAge(
                id = "proofLegalAge#3add94f4-28ec-42a1-8704-4e4aa51006b4",
                issuer = "did:example:456",
                issued = "2021-08-31T00:00:00Z",
                validFrom = "2021-08-31T00:00:00Z",
                credentialSubject = ProofOfLegalAgeSubject(
                    countryLegalAge = 18,
                    hasLegalAge = true,
                    id = "4f5e4z5f4ef45341gh654th"
                ),
                credentialSchema = CredentialSchema(
                    id = "https://raw.githubusercontent.com/rigwild/waltid-ssikit-vclib/master/src/test/resources/schemas/ProofOfLegalAge.json",
                    type = "JsonSchemaValidator2018"
                ),
            )
        }
    )

    override fun newId(id: String) = "proofLegalAge#${id}"
}
