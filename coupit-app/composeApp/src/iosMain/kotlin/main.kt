import androidx.compose.ui.window.ComposeUIViewController
import com.webxela.app.coupit.App
import com.webxela.app.coupit.koin.KoinInitializer
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController(
    configure = { KoinInitializer.initKoin() }
) { App() }