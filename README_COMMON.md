### 1. Retry Function

* **Extension Retry Function support for Result<T> or Flow coroutines**
* **Với Result<T> function:** extension retryWithExponentialBackoff() sẽ check retry trong trường hợp call api trả về lỗi AppException.Remote.ServerException
```kotlin
suspend inline fun <T> Result<T>.retryWithExponentialBackoff(
    times: Int = RETRY_TIMES, // default retry times
    initialDelay: Long = RETRY_INITIAL_DELAY,
    maxDelay: Long = RETRY_MAX_DELAY,
    factor: Double = RETRY_FACTOR,
): Result<T> {
    var currentDelay = initialDelay
    repeat(times) {
        try {
            val result = this
            if (result is Result.Success || (result is Result.Failure
                        && result.exception != AppException.Remote.ServerException)
            ) {
                return result
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        delay(currentDelay)
        currentDelay = (currentDelay * factor).toLong().coerceAtMost(maxDelay)
    }
    return this // last attempt
}
```

* **Cách sử dụng:**
```kotlin
getResultWithType(type = BaseMapperFactory.SuccessResponseMapperType.DATA_JSON_OBJECT) {
                    appApiService.login(username, password)
                }.onSuccess {
                    appPreferences.token = it.token
                    appPreferences.setLoginInfo(username, password)
                }.map {
                    it.toEntity()
                }.retryWithExponentialBackoff(2)
```

* **With flow coroutines** have ext to support retry, see the repo for more: https://github.com/hoc081098/FlowExt/blob/master/src/commonMain/kotlin/com/hoc081098/flowext/retryWhenWithDelayStrategy.kt
```kotlin
flow {... }.flowOn(ioDispatcher).retryWhenWithDelayStrategy(
strategy = DelayStrategy.FixedTimeDelayStrategy(
Duration.parse(
RETRY_MAX_DELAY.toString()))
) { cause, _ ->
cause is IOException
}
```

### 2. Flexible response
* **TemplateResponse:**
  Define các dạng response template, được dùng trong AppApiService, các dạng response đang hỗ trợ
```kotlin
@JsonClass(generateAdapter = true)
data class DataResponse<T>(
    @field:Json(name = "data")
    val data: T,
) : Base<T>()
```

	Hoặc một Object response cụ thể sẽ extends Base<T>, ví dụ:
```kotlin
@JsonClass(generateAdapter = true)
data class MovieData(

    @field:Json(name = "id")
    val id: Int?,

    @field:Json(name = "title")
    val title: String?,

    @field:Json(name = "overview")
    val overview: String?,

    @field:Json(name = "backdrop_path")
    val backdropPath: String?,

    ) : Base<MovieData>()
```

* **BaseResponseMapper:**

  **SuccessResponseMapperType** define các enum type (jsonArray, jsonObject, dataJsonArray, dataJsonObject,…) nếu phát sinh thì define thêm

  **ByTypeResponseFactory** chứa method fromType(SuccessResponseMapperType) để điều hướng đến các Mapper

  **Các internal class Mapper<T>** chứa method map(response:Any):T → ?cast response to DataResponse

* **ResultHandler:** chứa extension xử lí result response trả về, trường hợp success tiến hành mapping Response từ Type đã truyền vào
```kotlin
inline fun <reified S : Base<T>, T> getResultWithType(
    type: BaseMapperFactory.SuccessResponseMapperType? =
        BaseMapperFactory.SuccessResponseMapperType.DATA_JSON_OBJECT,
    action: () -> Response<S>
): Result<T>
```
* type: SuccessResponseMapperType
* action: suspend fun trả về ```Response<S extends Base<T>>``` (vì vậy khi tạo 1 template trong TemplateResponse cần extends từ Base<T> class)

* Code xử lí mapping data case success
```kotlin
val data = body()?.let {
                ByTypeResponseFactory<T>()
                    .fromType(type = type)
                    .map(it)
            }
            Result.Success(data!!)
```
* **Cách sử dụng:** Để sử dụng flexible response cần lắm việc thống nhất define type SuccessResponseMapperType sẽ ứng với TemplateResponse nào mới work ok.

  **AppApiService:** xác định kiểu Response<Base<T>>, ví dụ, Json trả về kiểu DataResponse
  ```kotlin
  @POST("user/login")
  @FormUrlEncoded
  @Headers(HeaderInterceptor.Header.NO_AUTH)
  suspend fun login(
      @Field("username") username: String,
      @Field("password") password: String,
  ): Response<DataResponse<LoginResponseData>>
  ```

  **RepositoryImpl:**  ứng với Response trả về từ Retrofit chọn SuccessResponseMapperType để mapping response, default **type = DATA_JSON_OBJECT** ứng với **DataResponse**
  ```kotlin
  getResultWithType(type = BaseMapperFactory.SuccessResponseMapperType.DATA_JSON_OBJECT) {
                  appApiService.login(username, password)
              }
  ```

### 3. Unit Test
* Đặt tên theo format: NameClass + Test, ví dụ: ValidationUtilsTest,  LoginViewModelTest
* Test các Class Utils, Validations
* Test các ViewModel: khi test ViewModel cần sử dụng Mockito để mock Repository
```kotlin
private lateinit var repository: Repository
    private lateinit var loginUseCase: LoginUseCase
    private lateinit var viewModel: LoginViewModel
	...

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        repository = mock()
        loginUseCase = LoginUseCase(repository)
        viewModel = LoginViewModel(loginUseCase)
    }
```

### 4. Dependency Injection
[Document](https://developer.android.com/training/dependency-injection/hilt-android)

Có 3 cách để tạo một object bằng DI:

**1. Đối với các thực thể Singleton**

Define trong class Module
```
@Provides
@Singleton
fun provideProductRepository(
    apiService: ApiService,
): ProductRepository = ProductRepositoryImpl(apiService)
```
**2. Đối với các thực thể non-Singleton**

Sử dụng @Inject constructor để inject các param đã define vào
```
class GetProductUseCase @Inject constructor(
    private val productRepository: ProductRepository,
)
```
**3. Đối với các thực thể không thể sử dụng constructor**

Sử dụng anotation @Inject + lateinit var
```
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var getAppLocalizeUseCase: GetAppLocalizeUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
```

### 5. Navigate giữa các fragment
Bình thường thì sẽ có 3 cách để navigate theo như [document](https://developer.android.com/guide/navigation/navigation-navigate)

* Sử dụng [Safe Args](https://developer.android.com/guide/navigation/navigation-navigate#safeargs)
  
* Sử dụng [Action id](https://developer.android.com/guide/navigation/navigation-navigate#id)
  
* Sử dụng [DeepLink](https://developer.android.com/guide/navigation/navigation-navigate#uri)

Tuy nhiên, vì project đi theo multi-module pattern nên ta chỉ có thể sử dụng cách 3 là DeepLink để navigate theo như [document](https://developer.android.com/guide/navigation/navigation-multi-module#across)

Đối với việc pass argument giữa các fragment, hiện tại có 2 cách:

* [Put data vào Uri](https://developer.android.com/guide/navigation/navigation-deep-link#implicit): Đây là cách được suggest tuy nhiên code lại bị rải rác vì Uri và các ConstantArg nằm ở nhiều nơi khác nhau
  
* Sử dụng `currentBackStackEntry`: Ít được nhắc đến nhưng chưa thấy phát sinh vấn gì và trông cũng thanh lịch hơn. Sử dụng extension function `NavController.navigateCustom` để navigate

### 6. Sử dụng các Base component
Vì project chỉ sử dụng một Activity là MainActivity (có thể là 2 nếu có thêm SplashActivity) nên không cần tới BaseActivity

**1. BaseFragment**

Khi sử dụng cần truyền vào 1 TypeGeneric của lớp ViewBinding dùng cho fragment đó và hàm inflate của nó
```
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate)
```
Cần override lại 2 hàm:

* Hàm `createView()`: Xử lý các view
  
* Hàm `observeData()`: Observer các Flow từ ViewModel. Sử dụng hàm `launchAndRepeatWithViewLifecycle` để quản lý các [Restartable Lifecycle-aware coroutines](https://developer.android.com/topic/libraries/architecture/coroutines#restart)

**2. BaseViewModel**

Cần override lại hàm `handleIntent` để xử lý các Intent trong kiến trúc MVI, hàm này sẽ được gọi khi một Fragment gọi một hàm `ViewModel.triggerIntent()`
