package common.api

import org.koin.dsl.module

val apiModule = module {
    single { ApiServer(getAll()) }
}