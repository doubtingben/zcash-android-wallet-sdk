package cash.z.wallet.sdk.sample.address

import cash.z.wallet.sdk.jni.RustBackend
import cash.z.wallet.sdk.jni.RustBackendWelding
import cash.z.wallet.sdk.secure.Wallet
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty

object Injection {
    private val rustBackend: RustBackendWelding = RustBackend()
    private const val dataDbName = "AddressSampleData.db"

    fun provideWallet(
        seedProvider: ReadOnlyProperty<Any?, ByteArray>,
        spendingKeyProvider: ReadWriteProperty<Any?, String>
    ): Wallet {
        // simulate new session for each call
        App.instance.getDatabasePath(dataDbName).absoluteFile.delete()

        return Wallet(
            App.instance,
            provideRustBackend(),
            App.instance.getDatabasePath(dataDbName).absolutePath,
            App.instance.cacheDir.absolutePath,
            arrayOf(0),
            seedProvider,
            spendingKeyProvider
        )
    }

    fun provideRustBackend(): RustBackendWelding {
        return rustBackend
    }
}

