package org.multipaz.nfc

import kotlinx.coroutines.flow.Flow

actual fun ExternalNfcReaderUsb.observeUsbState(): Flow<ExternalNfcReaderState> {
    throw NotImplementedError("Not implemented on this platform")
}

actual suspend fun ExternalNfcReaderUsb.requestUsbPermission(): Boolean {
    throw NotImplementedError("Not implemented on this platform")
}

actual suspend fun ExternalNfcReaderUsb.getUsbNfcTagReader(): NfcTagReader {
    throw NotImplementedError("Not implemented on this platform")
}
