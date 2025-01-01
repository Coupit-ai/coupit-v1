import androidx.compose.ui.window.ComposeUIViewController
import com.webxela.app.coupit.App
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController { App() }
