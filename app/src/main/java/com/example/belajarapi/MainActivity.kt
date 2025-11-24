package com.example.belajarapi

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

// --- PERHATIKAN: IMPORTNYA BEDA (TIDAK ADA ANGKA 3) ---
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation // Opsional

import com.example.belajarapi.Data.PahlawanResponseItem
import com.example.belajarapi.ViewModel.PahlawanUiState
import com.example.belajarapi.ViewModel.PahlawanViewModel
import com.example.belajarapi.ui.theme.BelajarApiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BelajarApiTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: PahlawanViewModel = viewModel()) {
    val uiState = viewModel.pahlawanUiState

    when (uiState) {
        is PahlawanUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is PahlawanUiState.Success -> {
            LazyColumn {
                items(uiState.data) { pahlawan ->
                    PahlawanItem(pahlawan = pahlawan)
                }
            }
        }
        is PahlawanUiState.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Gagal memuat data. Cek internet kamu!")
            }
        }
    }
}

@Composable
fun PahlawanItem(pahlawan: PahlawanResponseItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val context = LocalContext.current

            AsyncImage(
                // COIL 2 SYNTAX (Lebih Stabil)
                model = ImageRequest.Builder(context)
                    .data(pahlawan.imageAsset)
                    // Di Coil 2, perintahnya 'setHeader' atau 'addHeader' (biasanya addHeader aman)
                    .addHeader("User-Agent", "Mozilla/5.0")
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier.size(80.dp),
                contentScale = ContentScale.Crop,
                // Listener sederhana Coil 2
                onLoading = { Log.d("CekGambar", "Loading: ${pahlawan.name}") },
                onError = { Log.e("CekGambar", "Error: ${pahlawan.name}") }
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = pahlawan.name ?: "Tanpa Nama",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = pahlawan.deskripsi ?: "Tidak ada deskripsi",
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PahlawanItemPreview() {
    BelajarApiTheme {
        PahlawanItem(PahlawanResponseItem(name = "Contoh", deskripsi = "Tes", imageAsset = ""))
    }
}