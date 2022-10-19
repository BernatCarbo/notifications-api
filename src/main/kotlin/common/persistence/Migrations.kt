package common.persistence

import org.flywaydb.core.Flyway
import org.flywaydb.core.api.exception.FlywayValidateException
import javax.sql.DataSource

fun DataSource.migrate() {
    val flyway = Flyway.configure().locations(
        "classpath:migrations"
    ).dataSource(this).load()

    try {
        flyway.migrate()
    } catch (exception: FlywayValidateException) {
        val errorMessage = flyway.validateWithResult().invalidMigrations.joinToString("\n") {
            "Invalid migration: ${it.filepath} ${it.errorDetails.errorMessage}."
        }
        throw RuntimeException(errorMessage)
    }
}