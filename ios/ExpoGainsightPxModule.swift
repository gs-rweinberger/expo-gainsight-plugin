import ExpoModulesCore

public class ExpoGainsightPxModule: Module {
    public func definition() -> ModuleDefinition {
        Name("ExpoGainsightPx")
        
        Function("hello") {
            return "Hello world! 👋"
        }
        
        Function("startInstance") { (apiKey: String) in
            return false
        }
        
        Function("identify") { (userId: String) in
            return false
        }

        Function("custom") { (eventName: String) in
            return false
        }
    }
}
