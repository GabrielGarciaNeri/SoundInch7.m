package com.example.soundinch7.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.example.soundinch7.ui.models.Playlist
import com.example.soundinch7.ui.theme.SoundInch7Theme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlaylistCard(
    playlist: Playlist,
    onClick: () -> Unit,
    onLongClick: () -> Unit = {}
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f) // this make the card square
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // cover with colors from the playlist
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f) // get all the space in the card
                    .background(Color(playlist.colorHex.toColorInt())
                    ),
                contentAlignment = Alignment.Center
            ){
                Icon(
                    Icons.Default.MusicNote, contentDescription = "Playlist cover",
                    tint = Color.White.copy(alpha = 0.7f),
                    modifier = Modifier.size (32.dp) // size of the icon
                )
            } // end of box
            // playlist name and information
            Column (modifier = Modifier.padding(8.dp)) {
                Text(
                    text =playlist.name,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text("${playlist.songCount} songs",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 200)
@Composable
fun PlaylistCardPreview() {
    SoundInch7Theme {
        Box(modifier = Modifier.padding(16.dp)) {
            PlaylistCard(
                playlist = Playlist(
                    id = 1,
                    name = "My Playlist",
                    genre = "Rock",
                    songCount = 10,
                    colorHex = "#E91E63",
                    isFavorite = true
                ),
                onClick = {},
            )
        }
    }
}








