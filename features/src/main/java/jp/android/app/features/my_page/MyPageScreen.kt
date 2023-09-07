package jp.android.app.features.my_page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import jp.android.app.common_ui.theme.Dimens
import jp.android.app.common_ui.theme.TextStyle14
import jp.android.app.common_ui.theme.TextStyle14Bold
import jp.android.app.common_ui.ui.CommonLazyColumn
import jp.android.app.common_ui.ui.ErrorDialog
import jp.android.app.common_ui.ui.NetworkImage
import jp.android.app.common_ui.ui.PlaceholderBox
import jp.android.app.common_ui.ui.rememberCommonLazyListState
import jp.android.app.common_ui.util.HMargin16
import jp.android.app.common_ui.util.VMargin4
import jp.android.app.common_ui.util.VMargin8
import jp.android.app.common_ui.util.collectEvent
import jp.android.app.domain.entity.Movie

@Composable
fun MyPageScreen(
    viewModel: MyPageViewModel = hiltViewModel(),
    onItemClick: (Movie) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberCommonLazyListState()
    viewModel.event.collectEvent {
        when (it) {
            is MyPageEvent.GetMoviesFailure -> {
                listState.onError(it.appException)
            }
        }
    }

    ErrorDialog(uiState.appException, viewModel::onClearError)

    CommonLazyColumn(
        state = listState,
        contentPadding = PaddingValues(
            top = Dimens.myPagePaddingTop,
            bottom = Dimens.padding16,
        ),
        verticalArrangement = Arrangement.spacedBy(Dimens.padding16),
        isRefreshing = uiState.isRefreshing,
        onRefresh = viewModel::onRefresh,
        isMore = uiState.isMore,
        onLoadMore = viewModel::onLoadMore,
        onRetry = viewModel::getMovies,
        itemPlaceholder = {
            LoadingItem()
        },
        items = uiState.list,
    ) {
        MovieItem(
            movie = it,
            onItemClick = onItemClick,
        )
    }
}

@Composable
fun MovieItem(
    movie: Movie,
    onItemClick: (Movie) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onItemClick(movie)
            }
            .padding(
                horizontal = 16.dp,
            ),
    ) {
        NetworkImage(
            url = movie.backDropPath,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(Dimens.movieItemImageSize),
        )
        HMargin16()
        Column {
            Text(
                text = movie.id.toString() + ". " + movie.title,
                style = TextStyle14Bold,
            )
            VMargin4()
            Text(
                text = movie.overview,
                style = TextStyle14,
            )
        }
    }
}

@Composable
fun LoadingItem() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
            ),
    ) {
        PlaceholderBox(
            modifier = Modifier
                .size(Dimens.movieItemImageSize),
        )
        HMargin16()
        Column {
            PlaceholderBox(
                modifier = Modifier
                    .width(50.dp)
                    .height(16.dp),
            )
            VMargin8()
            PlaceholderBox(
                modifier = Modifier
                    .width(240.dp)
                    .height(14.dp),
            )
        }
    }
}