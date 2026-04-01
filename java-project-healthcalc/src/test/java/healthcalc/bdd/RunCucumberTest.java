package healthcalc.bdd; // Debe coincidir con la carpeta donde está este archivo

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
// 1. Apuntamos a la carpeta de resources donde estarán nuestros ficheros .feature
@SelectPackages("healthcalc")
// 2. Apuntamos al paquete exacto donde estarán nuestras clases con los Steps de Java
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "healthcalc.bdd")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty, html:target/reporte.html")
public class RunCucumberTest { 
}