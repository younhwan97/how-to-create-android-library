## 1. 라이브러리 제공을 위한 프로젝트 생성

`Android Studio → New Project → Empty Compose Activity`

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

## 2. 

### 2-1. 프로젝트 계층 구조 변경

<img src="https://github.com/younhwan97/how-to-create-android-library/blob/main/images/how-to-android-library-step-1.png?raw=true" width="550"/>

<br>

### 2-2. 모듈 생성

기존 MainActivity가 포함된 모듈은 이미 많은 다른 모듈들이 포함되어 있다. 

하지만 우리가 라이브러리를 빌드할 때 그 많은 모듈을 포함하고 싶지 않기 때문에 새로운 모듈을 생성한다.

<img src="https://github.com/younhwan97/how-to-create-android-library/blob/main/images/how-to-android-library-step-2.png?raw=true" width="550"/>