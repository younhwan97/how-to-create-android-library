## 1. 라이브러리 제공을 위한 프로젝트 생성

`Android Studio` → `New Project` → `Empty Compose Activity`

간단한 이미지 미리보기 라이브러리를 위해 다음과 같이 코드를 작성한다.

```Kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HowToCreateAndroidLibraryTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), 
                    color = MaterialTheme.colors.background
                ) {
                    Box() {
                        ImagePreview(
                            image = painterResource(id = R.drawable.sample),
                            description = "Hello",
                            modifier = Modifier
                                .size(150.dp)
                                .align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}
```

```Kotlin
@Composable
fun ImagePreview(
    image: Painter,
    modifier: Modifier = Modifier,
    description: String = "",
    contentDescription: String = "",
    onImageClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(10.dp))
            .shadow(5.dp, RoundedCornerShape(5.dp))
            .clickable { onImageClick() }
    ) {
        Image(
            painter = image,
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        )
        Text(
            text = description,
            style = MaterialTheme.typography.body1,
            color = Color.White,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color.Transparent,
                            Color.Black
                        )
                    )
                )
                .align(Alignment.BottomStart)
                .padding(8.dp)
        )
    }
}
```

<br>

## 2. 모듈 생성

### 2-1. 프로젝트 계층 구조 변경

<img src="https://github.com/younhwan97/how-to-create-android-library/blob/main/images/how-to-android-library-1.png?raw=true" width="300" height="300"/>

<br>

### 2-2. 모듈 생성

기존 MainActivity가 포함된 모듈은 이미 많은 다른 모듈들이 포함되어 있다. 

하지만 우리가 라이브러리를 빌드할 때 그 많은 모듈을 포함하고 싶지 않기 때문에 새로운 모듈을 생성한다.

<img src="https://github.com/younhwan97/how-to-create-android-library/blob/main/images/how-to-android-library-2.png?raw=true" width="550" height="350"/>

Android Library Template 선택 및 모듈 생성

<img src="https://github.com/younhwan97/how-to-create-android-library/blob/main/images/how-to-android-library-3.png?raw=true" width="550" height="350"/>

<br>

### 2-3. 결과 확인

다시 포로젝트 계층 구조를 Android로 변경하고, 새로운 모듈과 해당 모듈의 Gradle 파일을 확인한다.

새로운 모듈은 앱 모듈과 독립적이므로 자체 Gradle 파일이 생성되고, 이에따라 자체 종속성을 지정할 수 있다.

<img src="https://github.com/younhwan97/how-to-create-android-library/blob/main/images/how-to-android-library-4.png?raw=true" width="550" height="350"/>

<br>

## 

기존 앱 모듈에 있는 ImagePreview.kt 파일을 새롭게 생성한 모듈에 가져온다.

그 결과 다음과 같이 종속성 문제가 발생하는 것을 볼 수 있다.

<img src="https://github.com/younhwan97/how-to-create-android-library/blob/main/images/how-to-android-library-6.png?raw=true" width="550"/>