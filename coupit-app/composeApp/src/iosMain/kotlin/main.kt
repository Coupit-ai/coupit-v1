import androidx.compose.ui.window.ComposeUIViewController
import com.webxela.app.coupit.App
import com.webxela.app.coupit.AppInitializer
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController(
    configure = { AppInitializer.initKoin() }
) { App() }