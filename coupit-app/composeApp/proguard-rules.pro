# Keep your application class
-keep class com.webxela.app.coupit.androidApp.** { *; }

# Keep Kotlin Coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}

# Keep Kotlin Serialization
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt

# Keep Compose
-keep class androidx.compose.** { *; }
-keep class androidx.compose.ui.** { *; }
-keep class androidx.compose.material.** { *; }
-keep class androidx.compose.runtime.** { *; }
-keep class androidx.compose.foundation.** { *; }

# Keep Ktor
-keep class io.ktor.** { *; }
-keep class kotlinx.coroutines.** { *; }

# Keep Koin
-keep class org.koin.** { *; }

# Keep Coil
-keep class coil.** { *; }
-keep class coil.request.** { *; }
-keep class coil.target.** { *; }
-keep class coil.transition.** { *; }

# Keep your data models
-keep class com.webxela.app.coupit.data.** { *; }
-keep class com.webxela.app.coupit.domain.** { *; }
-keep class com.webxela.app.coupit.presentation.** { *; } 