import androidx.compose.ui.window.ComposeUIViewController
import com.webxela.app.coupit.App
import com.webxela.app.coupit.koin.KoinInitialiser
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController {
    KoinInitialiser.initKoin()
    App()
}