package jp.android.app.common_ui.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import jp.android.app.common_ui.theme.Dimens
import jp.android.app.common_ui.theme.TextStyle14
import jp.android.app.common_ui.theme.TextStyle16
import jp.android.app.common_ui.util.ExceptionHandler
import jp.android.app.common_ui.util.VMargin8
import jp.android.app.domain.data.AppException
import jp.android.app.shared.Constants
import kotlin.math.roundToInt

@Composable
fun rememberCommonLazyListState() = rememberSaveable(saver = CommonLazyListState.Saver) {
    CommonLazyListState()
}

class CommonLazyListState(
    appException: AppException? = null,
    internal var isInFirstPage: Boolean = true,
) {
    private var _error by mutableStateOf<AppException?>(appException)
    internal val error get() = _error

    fun onError(appException: AppException) {
        _error = appException
    }

    fun clearError() {
        _error = null
    }

    companion object {
        val Saver: Saver<CommonLazyListState, *> = Saver(
            save = {
                listOf(
                    it.error,
                    it.isInFirstPage,
                )
            },
            restore = {
                CommonLazyListState(
                    appException = it[0] as AppException?,
                    isInFirstPage = it[1] as Boolean,
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T> CommonLazyColumn(
    modifier: Modifier = Modifier,
    state: CommonLazyListState,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    isMore: Boolean,
    onLoadMore: () -> Unit,
    onRetry: (isFirstPage: Boolean) -> Unit,
    itemPlaceholder: @Composable LazyItemScope.() -> Unit,
    items: List<T>,
    itemContent: @Composable LazyItemScope.(item: T) -> Unit,
) {
    val listState = rememberLazyListState()
    val pullRefreshState = rememberCustomPullRefreshState(isRefreshing, onRefresh = {
        state.clearError()
        state.isInFirstPage = true
        onRefresh()
    })
    val offset = pullRefreshState.position.toInt()

    Box(
        modifier = modifier
            .pullRefresh(
                onPull = pullRefreshState::onPull,
                onRelease = pullRefreshState::onRelease,
            )
    ) {
        if (items.isEmpty() && state.error != null) { // error in first page
            ErrorItem(
                appException = state.error,
                modifier = Modifier.align(Alignment.Center),
            ) {
                state.clearError()
                onRetry(true)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .offset {
                        IntOffset(0, offset)
                    },
                state = listState,
                contentPadding = contentPadding,
                verticalArrangement = verticalArrangement,
            ) {
                items(
                    items = items,
                    itemContent = itemContent,
                )
                if (isMore) {
                    if (state.error != null && !state.isInFirstPage) {  // error when load more
                        item {
                            ErrorItem(
                                appException = state.error,
                            ) {
                                state.clearError()
                                onRetry(items.isEmpty())
                            }
                        }
                    } else {
                        item {
                            if (items.isNotEmpty()) {
                                LaunchedEffect(listState) {
                                    snapshotFlow {
                                        listState.layoutInfo.totalItemsCount
                                    }.collect {
                                        state.clearError()
                                        state.isInFirstPage = false
                                        onLoadMore()
                                    }
                                }
                            }
                            itemPlaceholder()
                        }
                        items(Constants.Paging.PAGE_SIZE - 1) {
                            itemPlaceholder()
                        }
                    }
                }
            }
        }
        if (offset > 0) {
            val indicatorSize = Dimens.pullRefreshIndicatorSize
            SpinningCircleProgressIndicator(
                color = Color.Black,
                indicatorSize = indicatorSize,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset {
                        val indicatorOffset = minOf(
                            a = offset * 1.5f,
                            b = offset + 16.dp.toPx(),
                            c = PullRefreshDefaults.RefreshThreshold.toPx() / 2 + offset * 0.6f,
                        )
                        IntOffset(0, (indicatorOffset - indicatorSize.toPx()).roundToInt())
                    },
            )
        }
    }
}

@Composable
fun ErrorItem(
    appException: AppException?,
    modifier: Modifier = Modifier,
    onRetry: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            imageVector = Icons.Outlined.Warning,
            tint = Color.Red,
            contentDescription = null,
        )
        Text(
            text = ExceptionHandler.getExceptionMessage(appException),
            style = TextStyle14,
            color = Color.Red,
        )
        VMargin8()
        Text(
            modifier = Modifier
                .background(
                    color = Color(0xFFD5D5D5),
                    shape = RoundedCornerShape(16.dp),
                )
                .clip(RoundedCornerShape(16.dp))
                .clickable(onClick = onRetry)
                .padding(horizontal = 16.dp, vertical = 4.dp),
            text = "Tap to retry",
            style = TextStyle16,
            color = Color.White,
        )
    }
}