import UIKit
import Rinku

@main
class AppDelegate: UIResponder, UIApplicationDelegate {
    var window: UIWindow?
    let rinku = RinkuIos(deepLinkFilter: nil, deepLinkMapper: nil) // Initialize Rinku

    func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?
    ) -> Bool {
        window = UIWindow(frame: UIScreen.main.bounds)
        if let window = window {
            window.rootViewController = MainKt.MainViewController()
            window.makeKeyAndVisible()
        }
        return true
    }

    func application(
        _ app: UIApplication,
        open url: URL,
        options: [UIApplication.OpenURLOptionsKey: Any] = [:]
    ) -> Bool {
        rinku.onDeepLinkReceived(url: url.absoluteString)
        return true
    }
}