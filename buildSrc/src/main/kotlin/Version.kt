object Version {

    val slf4j = Slf4j(
        api = "2.0.9",
        simple = "2.0.9"
    )

    val koin = Koin(
        core = "3.4.3",
    )

    val jmustache = "1.15"
}

data class Slf4j internal constructor(
    val api: String,
    val simple: String,
)

data class Koin internal  constructor(
    val core: String,
)