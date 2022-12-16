## 1. 라이브러리 제공을 위한 프로젝트 생성

`Android Studio` → `New Project` → `Empty Compose Activity`

간단한 이미지 미리보기 라이브러리를 위해 다음과 같은 컴포저블을 생성한다.

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

기존 MainActivity가 포함된 모듈은 이미 많은 모듈들이 포함되어 있다. 

하지만 우리가 라이브러리를 빌드할 때, 그 많은 모듈을 포함하고 싶지 않기 때문에 새로운 모듈을 생성한다.

<img src="https://github.com/younhwan97/how-to-create-android-library/blob/main/images/how-to-android-library-2.png?raw=true" width="550" height="350"/>

Android Library Template 선택 및 모듈 생성

<img src="https://github.com/younhwan97/how-to-create-android-library/blob/main/images/how-to-android-library-3.png?raw=true" width="550" height="350"/>

<br>

### 2-3. 결과 확인

다시 포로젝트 계층 구조를 Android로 변경하고, 새로운 모듈과 해당 모듈의 Gradle 파일을 확인한다.

새로운 모듈은 앱 모듈과 독립적이므로 자체 Gradle 파일이 생성되고, 이에따라 자체 dependency을 지정할 수 있다.

<img src="https://github.com/younhwan97/how-to-create-android-library/blob/main/images/how-to-android-library-4.png?raw=true" width="550" height="350"/>

<br>

## 3. Compose dependency 추가

기존 앱 모듈에 있는 ImagePreview.kt 파일을 새롭게 생성한 모듈에 가져온다.

그 결과 다음과 같이 dependency 문제가 발생하는 것을 볼 수 있다.

<img src="https://github.com/younhwan97/how-to-create-android-library/blob/main/images/how-to-android-library-6.png?raw=true" width="550"/>

새로운 모듈의 Gradle 파일에 Compose dependency를 추가한다.

```Gradle
plugins {
    id 'com.android.library'
    id 'org.jetbrains.kotlin.android'
}

android {
    namespace 'kr.co.younhwan.image_preview'
    compileSdk 32

    defaultConfig {
        minSdk 21
        targetSdk 32

        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles "consumer-rules.pro"
    }

    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = '1.8'
    }
    // 추가
    buildFeatures {
        compose true
    }
    // 추가
    composeOptions {
        kotlinCompilerExtensionVersion '1.1.1'
    }
}

dependencies {

    implementation "androidx.compose.ui:ui:$compose_ui_version"
    implementation "androidx.compose.ui:ui-tooling-preview:$compose_ui_version"
    implementation 'androidx.compose.material:material:1.1.1'
}
```

<br>

## 4. 라이브러리 배포

라이브러리를 jitpack.io에 배포하기 위해 Gradle 파일을 수정한다.

### 4-1. plugin 추가 

다음과 같이 maven-publish plugin을 추가한다.

```Gradle
plugins {
    id 'com.android.library'
    id 'org.jetbrains.kotlin.android'
    id 'maven-publish'
}
```

Gradle 파일의 제일 하단에 다음과 같은 구성을 추가한다.

```Gradle
afterEvaluate {
    publishing {
        publications {
            release(MavenPublication){
                from components.release

                groupId = 'com.github.younhwan97' // Input your github name 
                artifactId = 'image-preview-compose'
                version = '1.0'
            }
        }
    }
}
```

<br>

### 4-2. jitpack.yml 파일 생성

다시 프로젝트 계층 구조를 `Project`로 전환하고, jitpack.yml 파일을 생성한다.

<img src="https://github.com/younhwan97/how-to-create-android-library/blob/main/images/how-to-android-library-7.png?raw=true" width="550"/>

다음과 같은 내용을 작성한다.

```yml
jdk:
  - openjdk11
before_install:
  - ./scripts/prepareJitpackEnvironment.sh
```

<br>

### 4-3. 배포

github에 push하여 지금까지의 내용을 저장하고, 배포한다.

<img src="https://github.com/younhwan97/how-to-create-android-library/blob/main/images/how-to-android-library-8.png?raw=true" width="750"/>

<br>

<img src="https://github.com/younhwan97/how-to-create-android-library/blob/main/images/how-to-android-library-9.png?raw=true" width="750"/>

* **Release title:** 배포 제목을 작성
* **Choose a tag:** 배포 버전을 작성

<br>

위 과정을 마치면 [https://jitpack.io/](https://jitpack.io/)에서 깃허브 레파지토리 주소를 검색했을 때 배포된 라이브러리의 상태를 확인할 수 있다.

<img src="https://github.com/younhwan97/how-to-create-android-library/blob/main/images/how-to-android-library-10.png?raw=true" width="750"/>