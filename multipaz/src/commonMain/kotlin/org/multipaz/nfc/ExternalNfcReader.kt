package org.multipaz.nfc

import kotlinx.coroutines.flow.Flow
import org.multipaz.cbor.annotation.CborSerializable
import kotlin.time.Instant


@CborSerializable
sealed class ExternalNfcReader(
    open val id: String,
    open val addedAt: Instant,
    open val displayName: String,
) {

    abstract fun observeState(): Flow<ExternalNfcReaderState>

    abstract suspend fun requestPermission(): Boolean

    abstract suspend fun getNfcTagReader(): NfcTagReader

    companion object {
    }
}
