package org.multipaz.testapp.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Usb
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.multipaz.compose.items.Item
import org.multipaz.compose.items.ItemList
import org.multipaz.nfc.ExternalNfcReaderStore

@Composable
private fun FloatingActionButtonMenu(
    externalNfcReaderStore: ExternalNfcReaderStore
) {
    val coroutineScope = rememberCoroutineScope()
    var isMenuExpanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        Column(horizontalAlignment = Alignment.End) {
            AnimatedVisibility(
                visible = isMenuExpanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column(horizontalAlignment = Alignment.End) {
                    ExtendedFloatingActionButton(
                        text = { Text("Add ACR 1252U") },
                        onClick = {
                            isMenuExpanded = false
                            coroutineScope.launch {
                                externalNfcReaderStore.addUsbReader(
                                    "ACR 1252U",
                                    vendorId = 0x072f,
                                    productId = 0x223b
                                )
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = Icons.Outlined.Usb,
                                contentDescription = null
                            )
                        },
                        elevation = FloatingActionButtonDefaults.elevation(8.dp),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    ExtendedFloatingActionButton(
                        text = { Text("Add ACR WalletMate II") },
                        onClick = {
                            isMenuExpanded = false
                            coroutineScope.launch {
                                externalNfcReaderStore.addUsbReader(
                                    "ACR WalletMate II",
                                    vendorId = 0x072f,
                                    productId = 0x2401
                                )
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = Icons.Outlined.Usb,
                                contentDescription = null
                            )
                        },
                        elevation = FloatingActionButtonDefaults.elevation(8.dp),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    ExtendedFloatingActionButton(
                        text = { Text("Add ACR ACR1555 BLE Reader (USB interface)") },
                        onClick = {
                            isMenuExpanded = false
                            coroutineScope.launch {
                                externalNfcReaderStore.addUsbReader(
                                    "ACR 1555 BLE (USB interface)",
                                    vendorId = 0x072f,
                                    productId = 0x230a
                                )
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = Icons.Outlined.Usb,
                                contentDescription = null
                            )
                        },
                        elevation = FloatingActionButtonDefaults.elevation(8.dp),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
            }
            FloatingActionButton(
                onClick = { isMenuExpanded = !isMenuExpanded },
                elevation = FloatingActionButtonDefaults.elevation(8.dp),
                content = {
                    Icon(
                        imageVector = if (isMenuExpanded) Icons.Filled.Menu else Icons.Filled.Add,
                        contentDescription = null,
                    )
                }
            )
        }
    }
}


@Composable
fun NfcReadersScreen(
    externalNfcReaderStore: ExternalNfcReaderStore,
    showToast: (message: String) -> Unit,
    onReaderClicked: (readerId: String) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButtonMenu(externalNfcReaderStore)
        }
    ) {

        val readers = externalNfcReaderStore.readers.collectAsState().value
        val items = mutableListOf<@Composable () -> Unit>()
        if (readers.isEmpty()) {
            items.add {
                Text(text = "No readers found. Press + to add one.")
            }
        } else {
            readers.forEach { reader ->
                val state = reader.observeState().collectAsState(null)
                items.add {
                    Item(
                        modifier = Modifier.clickable {
                            onReaderClicked(reader.id)
                        },
                        key = reader.displayName,
                        valueText = "State: ${state.value}"
                    )
                }
            }
        }

        ItemList(
            items = items,
            title = "External NFC Readers",
        )
    }
}