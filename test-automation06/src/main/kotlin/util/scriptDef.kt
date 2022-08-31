package util

import kotlinx.coroutines.runBlocking
import kotlin.script.experimental.annotations.KotlinScript
import kotlin.script.experimental.api.*
import kotlin.script.experimental.dependencies.*
import kotlin.script.experimental.dependencies.maven.MavenDependenciesResolver
import kotlin.script.experimental.jvm.JvmDependency
import kotlin.script.experimental.jvm.dependenciesFromCurrentContext
import kotlin.script.experimental.jvm.jvm

@Suppress("unused")
// The KotlinScript annotation marks a class that can serve as a reference to the script definition for
// `createJvmCompilationConfigurationFromTemplate` call as well as for the discovery mechanism
// The marked class also become the base class for defined script type (unless redefined in the configuration)
@KotlinScript(
    // file name extension by which this script type is recognized by mechanisms built into scripting compiler plugin
    // and IDE support, it is recommendend to use double extension with the last one being "kts", so some non-specific
    // scripting support could be used, e.g. in IDE, if the specific support is not installed.
    fileExtension = "test.kts",
    // the class or object that defines script compilation configuration for this type of scripts
    compilationConfiguration = SimpleMainKtsScriptDefinition::class,
    // the class or object that defines script evaluation configuration for this type of scripts
    evaluationConfiguration = SimpleMainKtsEvaluationConfiguration::class
)
// the class is used as the script base class, therefore it should be open or abstract. Also the constructor parameters
// of the base class are copied to the script constructor, so with this definition the script will require `args` to be
// passed to the constructor, and `args` could be used in the script as a defined variable.
abstract class SimpleMainKtsScript()

class SimpleMainKtsScriptDefinition : ScriptCompilationConfiguration(
    {
        // Implicit imports for all scripts of this type
        defaultImports(DependsOn::class, Repository::class)
        jvm {
            // Extract the whole classpath from context classloader and use it as dependencies
            dependenciesFromCurrentContext(wholeClasspath = true)
        }
        // Callbacks
        refineConfiguration {
            // Process specified annotations with the provided handler
            onAnnotations(DependsOn::class, Repository::class, handler = ::configureMavenDepsOnAnnotations)
        }
    }
)

// Handler that reconfigures the compilation on the fly
fun configureMavenDepsOnAnnotations(context: ScriptConfigurationRefinementContext): ResultWithDiagnostics<ScriptCompilationConfiguration> {
    val annotations = context.collectedData?.get(ScriptCollectedData.collectedAnnotations)?.takeIf { it.isNotEmpty() }
        ?: return context.compilationConfiguration.asSuccess()
    return runBlocking {
        resolver.resolveFromScriptSourceAnnotations(annotations)
    }.onSuccess {
        context.compilationConfiguration.with {
            dependencies.append(JvmDependency(it))
        }.asSuccess()
    }
}

private val resolver = CompoundDependenciesResolver(FileSystemDependenciesResolver(), MavenDependenciesResolver())

class SimpleMainKtsEvaluationConfiguration : ScriptEvaluationConfiguration()


