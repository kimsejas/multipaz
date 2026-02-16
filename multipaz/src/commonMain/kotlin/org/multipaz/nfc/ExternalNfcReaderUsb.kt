package org.multipaz.nfc

import kotlinx.coroutines.flow.Flow
import kotlin.time.Instant

data class ExternalNfcReaderUsb(
    override val id: String,
    override val addedAt: Instant,
    override val displayName: String,
    val vendorId: Int,
    val productId: Int
): ExternalNfcReader(id, addedAt, displayName) {

    override fun observeState(): Flow<ExternalNfcReaderState> = observeUsbState()

    override suspend fun requestPermission(): Boolean = requestUsbPermission()

    override suspend fun getNfcTagReader(): NfcTagReader = getUsbNfcTagReader()
}

expect fun ExternalNfcReaderUsb.observeUsbState(): Flow<ExternalNfcReaderState>

expect suspend fun ExternalNfcReaderUsb.requestUsbPermission(): Boolean

expect suspend fun ExternalNfcReaderUsb.getUsbNfcTagReader(): NfcTagReader
