package com.example.soundinch7.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.soundinch7.ui.LibraryViewModel
import com.example.soundinch7.ui.components.PlaylistCard
import com.example.soundinch7.ui.models.Playlist
import com.example.soundinch7.ui.theme.SoundInch7Theme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen(
    viewModel: LibraryViewModel = viewModel()
) {
    val selectedTab by viewModel.selectedTab.collectAsStateWithLifecycle()
    val playlists by viewModel.filteredPlaylist.collectAsStateWithLifecycle()
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var selectedPlaylist by remember { mutableStateOf<Playlist?>(null) }

    fun closeSheet() {
        scope.launch { sheetState.hide() }
            .invokeOnCompletion { selectedPlaylist = null }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Library",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        TabRow(selectedTabIndex = selectedTab) {
            listOf("All", "Favorites").forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { viewModel.onTabSelected(index) },
                    text = { Text(title) }
                )
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 160.dp),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = playlists,
                key = { playlist -> playlist.id }
            ) { playlist ->
                PlaylistCard(
                    playlist = playlist,
                    onClick = {},
                    onLongClick = { selectedPlaylist = playlist }
                )
            }
        }
    }

    selectedPlaylist?.let { playlist ->
        ModalBottomSheet(
            onDismissRequest = { selectedPlaylist = null },
            sheetState = sheetState
        ) {
            Text(
                text = playlist.name,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
            )
            HorizontalDivider()
            ListItem(
                headlineContent = {
                    Text(if (playlist.isFavorite) "Remove from favorites" else "Add to favorites")
                },
                leadingContent = {
                    Icon(
                        imageVector = if (playlist.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = null
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        viewModel.toggleFavorite(playlist)
                        closeSheet()
                    }
                    .padding(horizontal = 8.dp)
            )
            ListItem(
                headlineContent = {
                    Text(
                        text = "Delete playlist",
                        color = MaterialTheme.colorScheme.error
                    )
                },
                leadingContent = {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        viewModel.deletePlaylist(playlist)
                        closeSheet()
                    }
                    .padding(horizontal = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LibraryScreenPreview() {
    SoundInch7Theme {
        LibraryScreen()
    }
}
