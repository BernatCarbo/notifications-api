package common.persistence

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.koin.core.module.Module
import org.koin.dsl.module
import javax.sql.DataSource

val persistenceModule: Module = module {
    val hikariConfig = HikariConfig().apply {
        driverClassName = "org.sqlite.JDBC"
        jdbcUrl = "jdbc:sqlite:./application.db"
        maximumPoolSize = 1
    }
    val dataSource: DataSource = HikariDataSource(hikariConfig)
    dataSource.migrate()

    val database = Database.connect(dataSource)

    single { dataSource }
    single { database }
}