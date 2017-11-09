import groovy.json.JsonSlurper

/*
http://localhost:9000/api/measures/component?componentKey=edge.platform.linux.exenia.appmngr&metricKeys=coverage,ncloc
http://localhost:9000/api/measures/component_tree?baseComponentKey=edge.platform.linux.exenia.appmngr&depth=-1&qualifiers=FIL&metricKeys=coverage
*/
def sonarqube_host="http://localhost:9000"
def project_name="test"

def SONARCUBE_URL = "${sonarqube_host}/api/measures/component?componentKey=${project_name}"
def COVERAGE_URL = "${sonarqube_host}/api/measures/component_tree?baseComponentKey=${project_name}&depth=-1&qualifiers=FIL&metricKeys=coverage"

def sonar_coverage = new URL("${SONARCUBE_URL}&metricKeys=coverage").getText()
def sonar_ncloc = new URL("${SONARCUBE_URL}&metricKeys=ncloc").getText()

URL u = new URL ("${sonarqube_host}/api/components/show?component=${project_name}")
HttpURLConnection huc = (HttpURLConnection) u.openConnection ()
int code = huc.getResponseCode() 
print ${code}

son_coverage = (new JsonSlurper().parseText(sonar_coverage)).component.measures[0].value
son_ncloc = (new JsonSlurper().parseText(sonar_ncloc)).component.measures[0].value

print "${son_coverage} ${son_ncloc}"

coverage_class = (new JsonSlurper().parseText(new URL(COVERAGE_URL).getText()))


coverage_class.components.each()  {  f -> 
        if ( Float.parseFloat(f.measures.value[0]) < 50 ) { 
            print "${f.name} ${f.measures.value[0]}"
    }
 } 


