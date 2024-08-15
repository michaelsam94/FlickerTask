package com.example.flickertask.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.data.model.Photo
import com.example.flickertask.ui.viewmodel.SearchPhotosViewModel
import com.example.flickertask.util.Constants

@Composable
fun PhotosScreen(viewModel: SearchPhotosViewModel = hiltViewModel()) {
    val photos by viewModel.photos
    val isLoading by viewModel.isLoading
    val hasMorePages by viewModel.hasMorePages
    val currentPage by viewModel.currentPage // Assuming you have currentPage in your ViewModel

    // Use LaunchedEffect to load initial photos when the screen is opened
    LaunchedEffect(Unit) {
        viewModel.searchPhotos("Color", Constants.API_KEY, currentPage)
    }

    val listState = rememberLazyListState()

    // Detect when the user has scrolled to the end of the list
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo }
            .collect { layoutInfo ->
                // Check if the last item is visible
                if (layoutInfo.visibleItemsInfo.isNotEmpty()) {
                    val lastVisibleItem = layoutInfo.visibleItemsInfo.last()
                    if (lastVisibleItem.index == layoutInfo.totalItemsCount - 1) {
                        // Trigger loading more photos
                        if (hasMorePages && !isLoading) {
                            viewModel.searchPhotos("Color", Constants.API_KEY, currentPage + 1)
                        }
                    }
                }
            }
    }

    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(16.dp)
    ) {
        items(photos) { photo ->
            PhotoItem(photo)
        }

        item {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

// Extension function to check if scrolled to bottom
private fun LazyListState.isScrolledToBottom(): Boolean {
    return layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1
}

@Composable
fun PhotoItem(photo: Photo) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter("https://live.staticflickr.com/${photo.server}/${photo.id}_${photo.secret}_m.jpg"),
            contentDescription = photo.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = photo.title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PhotosScreenPreview() {
    PhotosScreen()
}