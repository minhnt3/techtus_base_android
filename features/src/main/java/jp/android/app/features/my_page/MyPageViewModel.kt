package jp.android.app.features.my_page

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.android.app.common_ui.base.BaseEvent
import jp.android.app.common_ui.base.BaseState
import jp.android.app.common_ui.base.BaseViewModel
import jp.android.app.domain.data.AppException
import jp.android.app.domain.entity.Movie
import jp.android.app.domain.use_case.GetMoviesUseCase
import jp.android.app.shared.Constants
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
) : BaseViewModel<MyPageState, MyPageEvent>(MyPageState()) {

    init {
        getMovies(true)
    }

    fun onRefresh() {
        emitState {
            it.copy(
                isRefreshing = true,
            )
        }
        getMovies(true)
    }

    fun onLoadMore() {
        getMovies(false)
    }

    fun getMovies(isFirstPage: Boolean) {
        val page = if (isFirstPage) Constants.Paging.INITIAL_PAGE else state.currentPage + 1
        viewModelScope.launch {
            getMoviesUseCase(page, Constants.Paging.PAGE_SIZE).onSuccess { paging ->
                emitState {
                    it.copy(
                        list = if (isFirstPage) paging.list else it.list.plus(paging.list),
                        isMore = paging.hasNext,
                        currentPage = page,
                        isRefreshing = false,
                        appException = null,
                    )
                }
            }.onFailure { appException ->
                emitEvent(
                    MyPageEvent.GetMoviesFailure(
                        appException = appException
                    )
                )
                emitState {
                    it.copy(
                        isRefreshing = false,
                        appException = appException,
                    )
                }
            }
        }
    }

    fun onClearError() {
        emitState {
            it.copy(
                appException = null,
            )
        }
    }
}

sealed interface MyPageEvent : BaseEvent {
    data class GetMoviesFailure(val appException: AppException) : MyPageEvent
}

data class MyPageState(
    val list: List<Movie> = emptyList(),
    val currentPage: Int = Constants.Paging.INITIAL_PAGE,
    val isRefreshing: Boolean = false,
    val isMore: Boolean = true,
    val appException: AppException? = null,
) : BaseState
