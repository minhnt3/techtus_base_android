package jp.android.app.domain.use_case

import jp.android.app.domain.data.AppResult
import jp.android.app.domain.entity.Movie
import jp.android.app.domain.entity.Paging
import jp.android.app.domain.repository.Repository
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val repository: Repository,
): BaseUseCase() {

    suspend operator fun invoke(page: Int, perPage: Int): AppResult<Paging<Movie>> {
        return repository.getMovies(page, perPage)
    }
}