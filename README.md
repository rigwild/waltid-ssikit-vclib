# waltid-ssikit-vclib

Typesafe implementation of W3C Verifiable Credentials in order to facilitate interoperability among various applications.

## Custom version

### Added Verifiable Credentials

- [InternationalBankAccountNumber](./src/main/kotlin/id/walt/vclib/credentials/InternationalBankAccountNumber.kt) ([JSON Schema](./src/test/resources/schemas/InternationalBankAccountNumber.json))
- [ProofOfLegalAge](./src/main/kotlin/id/walt/vclib/credentials/ProofOfLegalAge.kt) ([JSON Schema](./src/test/resources/schemas/ProofOfLegalAge.json))

### Compile custom version locally

```sh
./gradlew build publishToMavenLocal
```

When compilation is done, you can import the dependency `id.walt:waltid-ssikit-vclib:99.22.0-SNAPSHOT-with_custom_vc` into your project.

#### Using Docker

If you use Docker, make sure the repository is available in the scope of the Docker build.

1. Edit Dockerfile to copy the repository in the build context

```Dockerfile
FROM openjdk:17-jdk-slim as buildstage
COPY ./.m2/ /root/.m2 # <--- Add this line
COPY ./ /
RUN ./gradlew installDist
[...]
```

2. Build the package and copy the repository to the scope of the Docker build

```sh
cd ../waltid-ssikit-vclib
./gradlew build publishToMavenLocal

cd ../waltid-walletkit
cp -R ~/.m2/* ./.m2/
docker build -t waltid/walletkit:with_custom_vc .
```

### Update from upstream

```sh
git remote add upstream git@github.com:walt-id/waltid-ssikit-vclib.git
git pull upstream master
```

## Setup

Add the dependency using Gradle:

    implementation("id.walt:waltid-ssikit-vclib:1.21.0")

or Maven:

    <dependency>
        <groupId>id.walt</groupId>
        <artifactId>waltid-ssikit-vclib</artifactId>
        <version>1.22.0</version>
    </dependency>

## Create a credential

```kotlin
val verifiableAuthorization: VerifiableCredential = VerifiableAuthorization(
    id = "did:ebsi-eth:00000001/credentials/1872",
    issuer = "did:ebsi:000001234",
    issuanceDate = "2020-08-24T14:13:44Z",
    credentialSubject = VerifiableAuthorization.CredentialSubject1(
        "did:ebsi:00000004321",
        VerifiableAuthorization.CredentialSubject1.NaturalPerson("did:example:00001111")
    ),
    proof = Proof(
        "EcdsaSecp256k1Signature2019",
        "2020-08-24T14:13:44Z",
        "assertionMethod",
        "did:ebsi-eth:000001234#key-1",
        "eyJhbGciOiJSUzI1NiIsImI2NCI6ZmFsc2UsImNyaXQiOlsiYjY0Il19."
    )
)
```

## Encode a credential to JSON

```kotlin
val json: String = verifiableAuthorization.encode()
// {"@context" : ["https://www.w3.org/2018/credentials/v1", "https://ess ...
```

## Decode a JSON credential

```kotlin
val unknownJson: String = fromInput()

val credential: VerifiableCredential = unknownJson.toCredential()

val issuer = when (credential) {
    is PermanentResidentCard -> credential.issuer
    is VerifiablePresentation -> {
        val vcsInVP = credential.verifiableCredential
        val permanentResidentCardInVP = vcsInVP.any { it is PermanentResidentCard }

        if (permanentResidentCardInVP) {
            (vcsInVP.first { it is PermanentResidentCard } as PermanentResidentCard).issuer
        } else throw IllegalArgumentException("This VP does not contain a PermanentResidentCard")
    }
    else -> throw IllegalArgumentException("No vc of type PermanentResidentCard was found!")
}

// extendable to e.g. take the users address from the PermanentResidentCard, or an Europass (if supplied), or a VerifiableUtilityBill etc...
```

## Adding VC Templates

1. Visit https://vc-creator.walt.id to generate a VC template.
2. Add it to package: id.walt.vclib.credentials.
3. Add the new class to id.walt.vclib.registry.VcTypeRegistry.
4. Run all test-cases, which will generate the JsonSchema and the serialized version of the credential template.
5. Open a Pull-Request on GitHub

## License

The VcLib by walt.id is Open Source software released under the [Apache 2.0 license](https://www.apache.org/licenses/LICENSE-2.0.html).
